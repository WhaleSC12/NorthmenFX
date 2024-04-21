package com.example;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;
import com.DegreeEZ.Student;
import com.DegreeEZ.*;

import java.io.IOException;
import java.util.ArrayList;

import com.DegreeEZ.DegreeWorksApplication;
import javafx.scene.input.MouseEvent;


public class StudentHomeController {

    @FXML
    private TextArea notesField;

    @FXML
    private TextArea currentlyEnrolledField;

    @FXML
    private ImageView homeButton, searchButton, semesterButton, profileButton;

    // Method to initialize the view with data
    @FXML
    public void initialize() {
        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-home.fxml"));
        searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-search.fxml"));
        semesterButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-semester.fxml"));
        profileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-profile.fxml"));

        notesField.setEditable(false);
        currentlyEnrolledField.setEditable(false);
        
        updateNotesField();
        updateCurrentlyEnrolledField();
    }

    private void updateNotesField() {
        // Get the current user
        Student currentStudent = (Student) DegreeWorksApplication.getInstance().getUser();
        if (currentStudent != null) {
            StringBuilder notes = new StringBuilder();
            for (String note : currentStudent.getAdvisorNotes()) {
                notes.append(note).append("\n");
            }
            // Set the text of notesField to the collected notes
            notesField.setText(notes.toString());
        } else {
            notesField.setText("No notes from advisor.");
        }
    }

    private void updateCurrentlyEnrolledField() {
        Student currentStudent = (Student) DegreeWorksApplication.getInstance().getUser();
        ArrayList<Course> enrolled = currentStudent.getEnrolledCourses();
        if (currentStudent != null && !enrolled.isEmpty()) {
            StringBuilder courses = new StringBuilder();
            for (Course course : currentStudent.getEnrolledCourses()) {
                courses.append(course).append("\n");
            }

            currentlyEnrolledField.setText(courses.toString());
        } else {
            currentlyEnrolledField.setText("No currently enrolled courses");
        }

    }

    private void navigateTo(String fxmlFile) {
        try {
            App.setRoot(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error navigating to: " + fxmlFile);        }
    }
}
