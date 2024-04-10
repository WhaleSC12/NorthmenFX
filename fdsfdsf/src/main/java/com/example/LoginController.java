package com.example;

import java.io.IOException;

import com.DegreeEZ.DegreeWorksApplication;

import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import DegreeEZ.*;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("Login");
    }

    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter a username and password.");
            errorLabel.setVisible(true);
            return;
        }

        //need to put login logic here
        DegreeWorksApplication app = DegreeWorksApplication.getInstance();

        if(app.login(username, password) == null){
            System.out.println("Error loggin in");
        }
        else {
            System.out.println("YAY!");
        }


        usernameField.clear();
        passwordField.clear();
        errorLabel.setVisible(false);
    }

}
