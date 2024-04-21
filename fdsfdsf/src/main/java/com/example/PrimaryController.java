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

public class PrimaryController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink signUpLink;

    @FXML
    private Text signinErrorMessage;

    @FXML
    private void handleSignIn(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = DegreeWorksApplication.getInstance().login(username, password);
    
        if (user != null) {
            signinErrorMessage.setVisible(false);
            if (StudentList.getInstance().contains(user.getUUID())) {
                navigateTo("student-home.fxml");
            } else if (AdvisorList.getInstance().contains(user.getUUID())) {
                navigateTo("advisor-semester.fxml");
            } else {
                // In case the user is neither in student nor advisor lists
                signinErrorMessage.setText("User role undetermined.");
                signinErrorMessage.setVisible(true);
            }
        } else {
            signinErrorMessage.setVisible(true);
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
        }
    }
}
