package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import com.DegreeEZ.*;

public class StudentRegisterController {

    @FXML
    private TextField studentsField;
    @FXML
    private Button addStudentsBtn;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<String> majorDropdown; 
    @FXML
    private ComboBox<String> advisorDropdown; 
    @FXML
    private TextArea bioField;
    @FXML
    private Button registerButton;
    @FXML
    private Text errorMessage;

    @FXML
    private void initialize() {
        populateMajorDropdown();
        populateAdvisorDropdown();
    }
    
    private void populateMajorDropdown() {
        majorDropdown.getItems().clear();
        for (Major major : MajorList.getMajors()) {
            majorDropdown.getItems().add(major.getMajorName());
        }
    }

    private void populateAdvisorDropdown() {
        advisorDropdown.getItems().clear();  // Clear existing items first to avoid duplicates
        for (Advisor advisor : AdvisorList.getAdvisors()) {
            advisorDropdown.getItems().add(advisor.getName());  
        }
    }



    @FXML
    private void registerStudent(ActionEvent event) {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
            usernameField.getText().isEmpty() || passwordField.getText().isEmpty() ||
            majorDropdown.getSelectionModel().isEmpty() ||
            advisorDropdown.getSelectionModel().isEmpty() || bioField.getText().isEmpty()) {
            errorMessage.setVisible(true);
            errorMessage.setText("Error: Please fill all required fields.");
        } else {
            errorMessage.setVisible(false);
            createStudentAccount();
        }
    }


    @FXML
    private void createStudentAccount() {
        String selectedMajorName = majorDropdown.getSelectionModel().getSelectedItem();
        Major selectedMajor = MajorList.getMajorByName(selectedMajorName);
        if (selectedMajor == null) {
            errorMessage.setText("Selected major is not valid.");
            errorMessage.setVisible(true);
            return;
        }
    
        String selectedAdvisorName = advisorDropdown.getSelectionModel().getSelectedItem();
        Advisor selectedAdvisor = AdvisorList.getAdvisorByName(selectedAdvisorName);
        if (selectedAdvisor == null) {
            errorMessage.setText("No advisor selected.");
            errorMessage.setVisible(true);
            return;
        }
    
        // Assuming every new sign-up is a freshman, semesters taken would be 0
        int semestersTaken = 0;
        ArrayList<CompletedCourse> completedCourses = new ArrayList<>();
        ArrayList<Course> outstandingRequirements = new ArrayList<>(selectedMajor.getRequiredCourseList());
    
        // Create the student account with the computed lists of courses.
        User newUser = DegreeWorksApplication.getInstance().createAccount(
            false, // false because this is a student account
            firstNameField.getText(),
            lastNameField.getText(),
            usernameField.getText(),
            passwordField.getText(),
            selectedMajor,
            completedCourses, // Empty since they are freshmen
            new ArrayList<>(), // Enrolled courses are initially empty
            outstandingRequirements, // All required courses are outstanding
            selectedAdvisor.getUUID(),
            new ArrayList<>(), // Initial advisor notes are empty
            semestersTaken,
            bioField.getText(),
            new ArrayList<UUID>() // Empty list for student UUIDs since it's a student account
        );
    
        if (newUser == null) {
            errorMessage.setText("Account creation failed. Please check the data provided.");
            errorMessage.setVisible(true);
        } else {
            DegreeWorksApplication.getInstance().setUser(newUser);
            navigateTo("student-home.fxml");
            
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
            System.err.println("Error navigating to: " + fxmlFile);        }
    }
}
