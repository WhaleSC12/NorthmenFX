package com.example;
import java.io.IOException;
import java.util.ArrayList;

import com.DegreeEZ.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class AdvisorSearchController {

    @FXML ImageView homeButton, searchButton, semesterButton, profileButton;
    @FXML private TextField searchStudent, indexField;
    @FXML private TextFlow studentInfo;
    @FXML private Button searchStudentButton, semPlanButton, auditButton, messageButton, sendMessageButton;
    @FXML private TextArea messageField;
    @FXML private Text indexErrorMessage, messageText, messageConfirmationText;

    private ArrayList<Student> searchResults;  // List to keep track of search results for use in other methods.
    private Student currentStudent; 

    @FXML
    private void initialize() {
        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-home.fxml"));
        searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-search.fxml"));
        semesterButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-semester.fxml"));
        profileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-profile.fxml"));
    }

    @FXML
    private void searchStudents(ActionEvent event) {
        String query = searchStudent.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            studentInfo.getChildren().clear();
            studentInfo.getChildren().add(new Text("Please enter a name to search."));
            return;
        }

        searchResults = new ArrayList<>();
        for (Student student : StudentList.getStudents()) {
            String fullName = (student.getFirstName() + " " + student.getLastName()).toLowerCase();
            if (fullName.contains(query)) {
                searchResults.add(student);
            }
        }

        displayResults();
    }

    private void displayResults() {
        studentInfo.getChildren().clear();
        int index = 1;
        for (Student student : searchResults) {
            Text studentDetails = new Text(index + ". " + student.getFirstName() + " " + student.getLastName() + "\n" +
                                           "Year: " + student.getStudentYear() + "\n" +
                                           "Major: " + student.getMajor().getMajorName() + "\n" +
                                           "GPA: " + String.format("%.2f", student.calculateGPA()) + "\n" +
                                           "Advisor: " + (student.getAdvisorUuid().equals(DegreeWorksApplication.getInstance().getUser().getUUID()) ? "Yes" : "No") + "\n\n");
            studentInfo.getChildren().add(studentDetails);
            index++;
        }
    }

    @FXML
    private void showMessageMenu(ActionEvent event) {
        try {
            int index = Integer.parseInt(indexField.getText().trim()) - 1;
            if (index >= 0 && index < searchResults.size()) {
                currentStudent = searchResults.get(index);
                messageText.setText("Send Message to: " + currentStudent.getFirstName() + " " + currentStudent.getLastName());
                messageField.setVisible(true);
                messageText.setVisible(true);
                sendMessageButton.setVisible(true);
                indexErrorMessage.setVisible(false);
            } else {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            indexErrorMessage.setText("Invalid index entered.");
            indexErrorMessage.setVisible(true);
            messageField.setVisible(false);
            messageText.setVisible(false);
            sendMessageButton.setVisible(false);
        }
    }

    @FXML
    private void sendMessageToStudent(ActionEvent event) {
        if (currentStudent != null) {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                currentStudent.addAdvisorNote(message);
                messageField.setText("");
                messageConfirmationText.setText("Message sent to: " + currentStudent.getFirstName() + " " + currentStudent.getLastName() + "!");
                messageConfirmationText.setVisible(true);
                messageField.setVisible(false);
                messageText.setVisible(false);
                sendMessageButton.setVisible(false);
            }
        }
    }

    private void navigateTo(String fxmlFile) {
        System.out.println("Navigating to " + fxmlFile);
        try {
            App.setRoot(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
