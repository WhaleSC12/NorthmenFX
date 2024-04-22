package com.example;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import com.DegreeEZ.*;

import java.io.IOException;

public class StudentHomeController {

    @FXML
    private TextFlow notesField;
    @FXML
    private TextFlow currentlyEnrolledField;
    @FXML
    private ImageView homeButton, searchButton, semesterButton, profileButton;

    // Method to initialize the view with data
    @FXML
    public void initialize() {
        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-home.fxml"));
        searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-search.fxml"));
        semesterButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-semester.fxml"));
        profileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-profile.fxml"));
        
        updateNotesField();
        updateCurrentlyEnrolledField();
    }

    private void updateNotesField() {
        Student currentStudent = (Student) DegreeWorksApplication.getInstance().getUser();
        notesField.getChildren().clear();
        if (currentStudent != null && currentStudent.getAdvisorNotes() != null && !currentStudent.getAdvisorNotes().isEmpty()) {
            for (String note : currentStudent.getAdvisorNotes()) {
                Text textNode = new Text(note + "\n");
                // Adding padding node
                Text paddingNode = new Text("\n");  // Use an empty Text node as a spacer
                notesField.getChildren().addAll(textNode, paddingNode);
            }
        } else {
            notesField.getChildren().add(new Text("No notes from advisor."));
        }
    }
    
    private void updateCurrentlyEnrolledField() {
        Student currentStudent = (Student) DegreeWorksApplication.getInstance().getUser();
        currentlyEnrolledField.getChildren().clear();
        if (currentStudent != null && !currentStudent.getEnrolledCourses().isEmpty()) {
            for (Course course : currentStudent.getEnrolledCourses()) {
                Text courseText = new Text(course.getName() + " - Credits: " + course.getCreditHours() + "\n");
                Text paddingNode = new Text("\n"); // Use an empty Text node as a spacer
                currentlyEnrolledField.getChildren().addAll(courseText, paddingNode);
            }
        } else {
            currentlyEnrolledField.getChildren().add(new Text("No currently enrolled courses."));
        }
    }

    private void navigateTo(String fxmlFile) {
        try {
            App.setRoot(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error navigating to: " + fxmlFile);
        }
    }
}
