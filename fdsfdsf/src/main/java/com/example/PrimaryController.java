package com.example;

import java.io.IOException;

import com.DegreeEZ.DegreeWorksApplication;
import com.DegreeEZ.User;
import com.DegreeEZ.StudentList;
import com.DegreeEZ.AdvisorList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class PrimaryController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Hyperlink signUpLink;

    @FXML
    private Text loginMessage;

    @FXML
    private void handleSignIn(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = DegreeWorksApplication.getInstance().login(username, password);
        boolean isStudent;
        StudentList studentList = StudentList.getInstance();
        AdvisorList advisorList = AdvisorList.getInstance();
        if (studentList.contains(user.getUUID())) {
            isStudent = true;
        } else if (advisorList.contains(user.getUUID())) {
            isStudent = false;
        } else {
            System.out.println("Error validating user");
            isStudent = false;
        }
        
        
        if (user != null && isStudent) {
            loginMessage.setText("Login successful!");
            navigateTo("student-home");
        } else if (user != null && !isStudent) {
            loginMessage.setText("Login successful!");
            navigateTo("advisor-home");
        }
        
        else {
            loginMessage.setText("Login failed. Please check your username and password.");
            loginMessage.setFill(Color.RED); 
            loginMessage.setVisible(true);
        }
    }

    
    @FXML
    void handleSignUp(ActionEvent event) {
        navigateTo("SignUp.fxml");
    }
    

    
    private void navigateTo(String fxmlFile) {
        System.out.println("Navigating to " + fxmlFile);
        try {
            App.setRoot(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            loginMessage.setText("Failed to load the view: " + fxmlFile);
        }
    }
}
