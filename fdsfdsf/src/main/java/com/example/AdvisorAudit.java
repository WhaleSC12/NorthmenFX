package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import com.DegreeEZ.*;

public class AdvisorAudit  {

    @FXML
    private ImageView advisorHomeButton;

    @FXML
    private ImageView advisorSearchButton;

    @FXML
    private ImageView advisorCalendarButton;

    @FXML
    private ImageView advisorProfileButton;

    @FXML
    private TextField studentName;

    @FXML
    private TextField majorField;

    @FXML
    private TextField gpaField;

    @FXML
    private TextFlow enrolledCourses;

    @FXML
    private TextArea outstandingCourses;

    @FXML
    private Button changeMajorButton;

    @FXML
    private Button flagStudentButton;

    private User currentUser;


    public void initialize() {
        advisorHomeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-home.fxml"));
        advisorSearchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-student-search.fxml"));
        advisorCalendarButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-semester.fxml"));
        advisorProfileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-profile.fxml"));
        currentUser = DegreeWorksApplication.getInstance().getUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            populateStudentInfo(student);
            updateCurrentEnrolledField();
        } else {
            System.out.println("Current Search is not a student");
        }
        
    }
@FXML
    private void populateStudentInfo(Student student) {
        studentName.setText(student.toString());
        majorField.setText(student.getMajor().getMajorName());
        gpaField.setText(String.format("%.2f", student.calculateGPA()));
        


    }

    private void updateCurrentlyEnrolledField() {
        Student currentStudent = (Student) DegreeWorksApplication.getInstance().getUser();
        enrolledCourses.getChildren().clear();
        if (currentStudent != null && !currentStudent.getEnrolledCourses().isEmpty()) {
            for (Course course : currentStudent.getEnrolledCourses()) {
                Text courseText = new Text(course.getName() + " - Credits: " + course.getCreditHours() + "\n");
                Text paddingNode = new Text("\n"); // Use an empty Text node as a spacer
                enrolledCourses.getChildren().addAll(courseText, paddingNode);
            }
        } else {
            enrolledCourses.getChildren().add(new Text("No currently enrolled courses."));
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


    
    