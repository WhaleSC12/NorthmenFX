package com.example;

import com.DegreeEZ.DegreeWorksApplication;
import com.DegreeEZ.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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

    // Method to handle the sign-in button click
    @FXML
    private void handleSignIn(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = DegreeWorksApplication.getInstance().login(username, password);
        
        if (user != null) {
            loginMessage.setText("Login successful!");
            // Navigate to another page or update UI accordingly
        } else {
            loginMessage.setText("Login failed. Please check your username and password.");
        }
    }

    // Method to handle the sign-up hyperlink click
    @FXML
    private void handleSignUp(ActionEvent event) {
        try {
            // Assuming you are using a navigation method to change views
            navigateTo("signup.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Dummy method for navigation, replace with your actual navigation logic
    private void navigateTo(String fxmlFile) {
        System.out.println("Navigating to " + fxmlFile);
    }
}
