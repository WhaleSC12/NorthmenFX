package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import com.DegreeEZ.*;

public class AdvisorProfile {

    @FXML
    private ImageView advisorHomeButton;

    @FXML
    private ImageView advisorSearchButton;

    @FXML
    private ImageView advisorCalendarButton;

    @FXML
    private ImageView advisorProfileButton;

    @FXML
    private TextField advisorName;

    private User currentUser;

    @FXML
    public void initialize() {
        advisorHomeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-home.fxml"));
        advisorSearchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-audit.fxml"));
        advisorCalendarButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-register.fxml"));
        advisorProfileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-profile.fxml"));
        currentUser = DegreeWorksApplication.getInstance().getUser();
        if(currentUser instanceof Advisor) {
            Advisor advisor = (Advisor) currentUser;
            updateProfile(advisor);
        } else {
            System.out.println("Current user is not an Advisor");
        }
    }

    private void updateProfile(Advisor advisor) {
        advisorName.setText(advisor.toString());
    }

    @FXML
    private void signOut() {
        DegreeWorksApplication.getInstance().logout();
        navigateTo("primary.fxml");
    }

    @FXML
    private void navigateTo(String fxmlFile) {
        System.out.println("Navigating to " + fxmlFile);
        try {
            App.setRoot(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to navigate to: " + fxmlFile);
        }
    }

}


