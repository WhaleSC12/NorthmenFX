package com.example;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class AdvisorCalendarController {

    @FXML
    private ImageView homeButton;

    @FXML
    private ImageView searchButton;

    @FXML
    private ImageView semesterButton;

    @FXML
    private ImageView profileButton;

    @FXML
    public void initialize() {
        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-home.fxml"));
        searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-search.fxml"));
        semesterButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-calendar.fxml"));
        profileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-profile.fxml"));
    }

    private void navigateTo(String fxmlFile) {
        try {
            App.setRoot(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error navigating to: " + fxmlFile);
        }
    }
}
