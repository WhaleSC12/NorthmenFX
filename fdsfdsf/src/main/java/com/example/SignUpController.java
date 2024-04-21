package com.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class SignUpController {

    @FXML
    private Button studentBtn;
    @FXML
    private Button advisorBtn;
    @FXML
    private Button loginBtn;
   

    @FXML
    private void initialize() {
        
    }

    @FXML
    private void handleStudentBtnAction(ActionEvent event) {
        navigateTo("student-register.fxml");
    }

    @FXML
    private void handleAdvisorBtnAction(ActionEvent event) {
        navigateTo("advisor-register.fxml");
    }

    @FXML
    private void handleLoginBtnAction(ActionEvent event) {
        navigateTo("primary.fxml");
    }

    private void navigateTo(String fxmlFile) {
        try {
            App.setRoot(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error navigating to: " + fxmlFile);        }
    }
}
