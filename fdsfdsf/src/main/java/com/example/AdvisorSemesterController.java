package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.io.IOException;

import com.DegreeEZ.*;

public class AdvisorSemesterController {



    @FXML
    private ImageView semesterButton, searchButton, homeButton, profileButton;
    @FXML
    private ComboBox<Student> studentDropdown;
    @FXML
    private ComboBox<String> semesterDropdown;
    @FXML
    private VBox courseDetailsVBox;

    private Advisor currentAdvisor;
    private Student currentStudent; 

    public void initialize() {
        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-home.fxml"));
        searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-student-search.fxml"));
        semesterButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-semester.fxml"));
        profileButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> navigateTo("advisor-profile.fxml"));
        currentAdvisor = (Advisor) DegreeWorksApplication.getInstance().getUser();
        populateStudentDropdown();
        studentDropdown.setOnAction(event -> {
            currentStudent = studentDropdown.getValue();
            populateSemesterDropdown();
            updateSemesterDetails(null);
        });
        semesterDropdown.setOnAction(event -> updateSemesterDetails(semesterDropdown.getValue()));
    }

    private void populateStudentDropdown() {
        for (Student student : currentAdvisor.getStudents()) {
            studentDropdown.getItems().add(student);
        }
        studentDropdown.setConverter(new StringConverter<Student>() {
            @Override
            public String toString(Student student) {
                return student.getFirstName() + " " + student.getLastName();
            }

            @Override
            public Student fromString(String string) {
                return null; // No conversion from string back to Student needed
            }
        });
    }

    private void populateSemesterDropdown() {
        semesterDropdown.getItems().clear();
        if (currentStudent != null) {
            for (int i = 1; i <= 8; i++) {
                semesterDropdown.getItems().add("Semester " + i);
            }
            semesterDropdown.getSelectionModel().select(currentStudent.getSemestersCompleted());
        }
    }

    private void updateSemesterDetails(String selectedSemester) {
        if (currentStudent == null) return;

        if (selectedSemester == null) {
            selectedSemester = "Semester " + (currentStudent.getSemestersCompleted() + 1);
            semesterDropdown.getSelectionModel().select(currentStudent.getSemestersCompleted());
        }

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
        System.out.println("Navigating to " + fxmlFile);
        try {
            App.setRoot(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
