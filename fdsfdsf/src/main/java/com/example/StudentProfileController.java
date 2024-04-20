package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.io.IOException;

import com.DegreeEZ.*;

public class StudentProfileController {

    @FXML private Text firstNameField;
    @FXML private Text lastNameField;
    @FXML private Text majorField;
    @FXML private Text yearField;
    @FXML private Text gpaField;
    @FXML private TextArea bioField;
    @FXML private Button editProfileBtn;
    @FXML private Button signOutBtn;

    @FXML
    private ImageView homeButton;

    @FXML
    private ImageView searchButton;

    @FXML
    private ImageView semesterButton;

    @FXML
    private ImageView profileButton;


    private User currentUser;

    @FXML
    public void initialize() {
        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-home.fxml"));
        searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-search.fxml"));
        semesterButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-calendar.fxml"));
        profileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-profile.fxml"));
        currentUser = DegreeWorksApplication.getInstance().getUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            updateProfile(student);
        } else {
            // Handle the case where the user might not be a student, or handle differently
            System.out.println("Current user is not a student.");
        }
    }

    private void updateProfile(Student student) {
        firstNameField.setText(student.getFirstName());
        lastNameField.setText(student.getLastName());
        majorField.setText(student.getMajor().getMajorName());  
        yearField.setText(student.getStudentYear());     
        gpaField.setText(String.format("%.2f", student.calculateGPA()));
        bioField.setText(student.getBio());
    }

    @FXML
    private void editProfile() {
        // Add logic to enable editing of profile possibly (if we have time)
    }

    @FXML
    private void signOut() {
        DegreeWorksApplication.getInstance().logout();
        navigateTo("primary.fxml");
        // set user to null and go back to initial log in page
    }

    @FXML
    private void navigateTo(String fxmlFile) {
        System.out.println("Navigating to " + fxmlFile);
        try {
            App.setRoot(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to navigate to: " + fxmlFile);
        }
    }
}