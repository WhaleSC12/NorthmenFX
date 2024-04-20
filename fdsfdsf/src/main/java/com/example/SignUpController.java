package com.example;

import java.io.IOException;

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
    private void handleStudentBtnAction(MouseEvent event) {
        navigateTo("student-register.fxml");
    }

    @FXML
    private void handleAdvisorBtnAction(MouseEvent event) {
        navigateTo("advisor-register.fxml");
    }

    @FXML
    private void handleLoginBtnAction(MouseEvent event) {
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
