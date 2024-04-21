package com.example;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

import com.DegreeEZ.*;

public class StudentSemesterController {

    @FXML
    private ImageView homeButton;

    @FXML
    private ImageView searchButton;

    @FXML
    private ImageView semesterButton;

    @FXML
    private ImageView profileButton;

    @FXML
    private ComboBox<String> semesterDropdown;
    @FXML
    private VBox courseDetailsVBox;
    @FXML
    private Text nameField, currentSemesterField;

    private Student currentStudent;

    public void initialize() {
        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-home.fxml"));
        searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-search.fxml"));
        semesterButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-semester.fxml"));
        profileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("student-profile.fxml"));
        currentStudent = (Student) DegreeWorksApplication.getInstance().getUser(); // Get the logged-in student
        populateSemesterDropdown();
        populateTopInfo();
        currentSemesterField.setText("Current Semester: " + (currentStudent.getSemestersCompleted() + 1));
        semesterDropdown.getSelectionModel().select(currentStudent.getSemestersCompleted());
        updateSemesterDetails(semesterDropdown.getValue());
    }

    private void populateSemesterDropdown() {
        for (int i = 1; i <= 8; i++) {
            semesterDropdown.getItems().add("Semester " + i);
        }
        semesterDropdown.setOnAction(event -> updateSemesterDetails(semesterDropdown.getValue()));
    }

    @FXML
    private void populateTopInfo() {
        nameField.setText(currentStudent.getFirstName() + " " + currentStudent.getLastName());
        currentSemesterField.setText("Current Semester: " + currentStudent.getCompletedCredits()+1);
    }

    private void updateSemesterDetails(String selectedSemester) {
        int semesterIndex = Integer.parseInt(selectedSemester.split(" ")[1]);
        courseDetailsVBox.getChildren().clear(); // Clear previous items

        if (semesterIndex <= currentStudent.getSemestersCompleted()) {
            // Handle past semesters
            for (CompletedCourse completedCourse : currentStudent.getCompletedCourses()) {
                if (completedCourse.getSemesterTaken() == semesterIndex) {
                    Text courseText = new Text(completedCourse.getCourse().getName() + " - Credits: " + completedCourse.getCourse().getCreditHours() + ", Grade: " + completedCourse.getGrade());
                    courseDetailsVBox.getChildren().add(courseText);
                }
            }
        } else if (semesterIndex == currentStudent.getSemestersCompleted() + 1) {
            // Handle current semester
            for (Course course : currentStudent.getEnrolledCourses()) {
                Text courseText = new Text(course.getName() + " - Credits: " + course.getCreditHours());
                courseDetailsVBox.getChildren().add(courseText);
            }
        } else {
            // Handle future semesters
            for (Course course : currentStudent.getOutstandingRequirements()) {
                if (course.getReccomendedSemester() == semesterIndex) {
                    Text courseText = new Text(course.getName() + " - Credits: " + course.getCreditHours());
                    courseDetailsVBox.getChildren().add(courseText);
                }
            }
        }
    }

    private void navigateTo(String fxmlFile) {
        try {
            App.setRoot(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error navigating to: " + fxmlFile);        }
    }
}
