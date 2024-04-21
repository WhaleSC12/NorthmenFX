package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import java.util.ArrayList;
import com.DegreeEZ.*;

public class StudentSearchController {

    @FXML private ImageView homeButton, searchButton, profileButton, semesterButton;
    @FXML private TextField searchTextField, indexField;
    @FXML private TextArea searchResults;
    @FXML private Button registerButton;
    @FXML private Text message;

    private ArrayList<Course> lastSearchResults = new ArrayList<>();

    @FXML
    public void initialize() {
    }

    @FXML
    private void performSearch(ActionEvent event) {
        String searchText = searchTextField.getText().trim().toLowerCase();
        lastSearchResults.clear();  // Clear previous results
        StringBuilder displayResults = new StringBuilder();
        int index = 0;

        for (Course c : CourseList.getCourses()) {
            if (c.getSubject().toString().toLowerCase().contains(searchText) ||
                c.getName().toLowerCase().contains(searchText) ||
                c.getNumber().toLowerCase().contains(searchText)) {
                lastSearchResults.add(c);
                displayResults.append(index++).append(": ").append(c.getName())
                               .append(" - ").append(c.getSubject()).append(" ")
                               .append(c.getNumber()).append(", Credits: ")
                               .append(c.getCreditHours()).append("\n")
                               .append(formatPrerequisites(c.getPrerequisites()))
                               .append("\n\n");
            }
        }
        searchResults.setText(displayResults.toString());
    }

    @FXML
    private String formatPrerequisites(ArrayList<Prerequisite> prerequisites) {
        if (prerequisites.isEmpty()) {
            return "No prerequisites.";
        }
        StringBuilder sb = new StringBuilder("Prerequisites:\n");
        for (Prerequisite prereq : prerequisites) {
            sb.append(prereq.printPrerequisites()).append("\n");
        }
        return sb.toString();
    }

    @FXML
    private void registerSelectedCourse(ActionEvent event) {
        try {
            int index = Integer.parseInt(indexField.getText().trim());
            if (index >= 0 && index < lastSearchResults.size()) {
                Course selectedCourse = lastSearchResults.get(index);
                User currentUser = DegreeWorksApplication.getInstance().getUser();
                if (currentUser instanceof Student) {
                    Student student = (Student) currentUser;
                    student.addEnrolledCourse(selectedCourse);  
                    System.out.println("Registered for course: " + selectedCourse.getName());
                    message.setText("Registration Successful");
                } else {
                    System.out.println("Registration failed: User is not a student.");
                }
            } else {
                throw new IllegalArgumentException("Invalid course index.");
            }
        } catch (NumberFormatException e) {
            message.setText("Error");
            
        } catch (IllegalArgumentException e) {
            message.setText("Error");
        
        }
    }
}
