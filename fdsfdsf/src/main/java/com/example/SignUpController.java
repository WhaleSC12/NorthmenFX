
package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {
    @FXML
    private Button secondaryButton;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField userameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void switchToPrimary() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/primary.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) secondaryButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void signUp() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String username = userameField.getText();
        String password = passwordField.getText();

        // Add sign-up logic here

        // Clear fields and show error message if necessary
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Sign-up failed. Please check your input.");
            errorLabel.setVisible(true);
        } else {
            firstNameField.clear();
            lastNameField.clear();
            emailField.clear();
            userameField.clear();
            passwordField.clear();
            errorLabel.setVisible(false);
        }
    }
}