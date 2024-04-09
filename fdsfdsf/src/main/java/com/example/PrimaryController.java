package com.example;

import java.io.IOException;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class PrimaryController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("primary");
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

        usernameField.clear();
        passwordField.clear();
        errorLabel.setVisible(false);
    }

}
