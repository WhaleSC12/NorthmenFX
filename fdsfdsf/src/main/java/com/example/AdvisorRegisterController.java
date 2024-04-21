package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.UUID;
import java.io.IOException;
import java.util.ArrayList;

import com.DegreeEZ.*;

public class AdvisorRegisterController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<Student> studentDropdown; 
    @FXML
    private TextArea bioField;
    @FXML
    private Text errorMessage;
    @FXML
    private Button addStudentBtn;

    @FXML
    public void initialize() {
        populateStudentDropdown();
    }

    // Populates the ComboBox with students from the StudentList
    private void populateStudentDropdown() {
        studentDropdown.getItems().clear();
        studentDropdown.getItems().addAll(StudentList.getStudents());
    }


    // Event handler for the Add Student Button
    @FXML
    private void addStudent() {
        Student selectedStudent = studentDropdown.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            errorMessage.setText("No student selected.");
            errorMessage.setVisible(true);
        } else {
            // Assuming you have a method to add the student's UUID to the advisor's list
            Advisor currentAdvisor = getCurrentAdvisor();
            currentAdvisor.addStudentUUID(selectedStudent.getUUID());
            errorMessage.setText("Student added successfully to the advisor.");
            errorMessage.setVisible(true);
        }
    }

    @FXML
    private void registerAdvisor(ActionEvent event) {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
            usernameField.getText().isEmpty() || passwordField.getText().isEmpty() ||
            studentDropdown.getSelectionModel().isEmpty()) {
            errorMessage.setVisible(true);
            errorMessage.setText("Error: Please fill all required fields.");
        } else {
            errorMessage.setVisible(false);
            createAdvisorAccount();
        }
    }

    // Placeholder method to get the current logged-in advisor
    private Advisor getCurrentAdvisor() {
        User user = DegreeWorksApplication.getInstance().getUser();
        if (user instanceof Advisor) {
            return (Advisor) user;
        }
        return null;
    }

    private void createAdvisorAccount() {
        UUID advisorUUID = UUID.randomUUID();
        ArrayList<UUID> studentUUIDs = new ArrayList<>(); // Start with an empty list of advised students.

    User newUser = DegreeWorksApplication.getInstance().createAccount(
        true, // true because this is an advisor account
        firstNameField.getText(),
        lastNameField.getText(),
        usernameField.getText(),
        passwordField.getText(),
        null, // No major for advisors typically
        new ArrayList<>(), // No completed courses
        new ArrayList<>(), // No enrolled courses
        new ArrayList<>(), // No outstanding requirements
        advisorUUID,
        new ArrayList<>(), // Initial advisor notes are empty
        0, // Semesters completed does not apply
        bioField.getText(),
        studentUUIDs
    );

    if (newUser == null) {
        errorMessage.setText("Account creation failed. Please check the data provided.");
        errorMessage.setVisible(true);
    } else {
        errorMessage.setText("Advisor account created successfully.");
        errorMessage.setVisible(true);
        navigateTo("advisor-home.fxml"); // Navigate to advisor home page after registration
    }
    }


    @FXML private void backtoSignUp(ActionEvent event) {
        errorMessage.setVisible(false);
        navigateTo("SignUp.fxml");
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

