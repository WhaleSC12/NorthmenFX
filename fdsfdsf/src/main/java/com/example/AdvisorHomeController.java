package com.example;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

import com.DegreeEZ.*;

public class AdvisorHomeController {

    @FXML
    private TextFlow strugglingStudentsField;

    @FXML
    private ImageView advisorHomeButton, advisorSearchButton, advisorCalendarButton, advisorProfileButton;

    private Advisor currentAdvisor;

    public void initialize() {
        advisorHomeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-home.fxml"));
        advisorSearchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-student-search.fxml"));
        advisorCalendarButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-semester.fxml"));
        advisorProfileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-profile.fxml"));
        currentAdvisor = (Advisor) DegreeWorksApplication.getInstance().getUser(); 
        populateStrugglingStudents();
    }

    private void populateStrugglingStudents() {
        strugglingStudentsField.getChildren().clear();  // Clear previous data if any
        for (Student student : currentAdvisor.getStudents()) {
            if (student.calculateGPA() < 3.0) {  // Check if the student's GPA is below 3.0
                Text studentInfo = new Text(student.getFirstName() + " " + student.getLastName() + " - GPA: " + String.format("%.2f", student.calculateGPA()) + "\n");
                strugglingStudentsField.getChildren().add(studentInfo);
            }
        }
        if (strugglingStudentsField.getChildren().isEmpty()) {
            strugglingStudentsField.getChildren().add(new Text("No struggling students."));
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
