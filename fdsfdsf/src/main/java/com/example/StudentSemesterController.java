package com.example;

import java.util.ArrayList;

import com.DegreeEZ.*;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

public class StudentSemesterController {

    @FXML
    private ComboBox<String> semesterDropdown;

    @FXML
    private Text class1Name, class1Credits, class1Grade;
    @FXML
    private Text class2Name, class2Credits, class2Grade;
    @FXML
    private Text class3Name, class3Credits, class3Grade;
    @FXML
    private Text class4Name, class4Credits, class4Grade;
    @FXML
    private Text class5Name, class5Credits, class5Grade;
    @FXML
    private VBox courseDetailsVBox;

    @FXML
    private Text currentSemesterField;

    public void initialize() {
        populateSemesterDropdown();
        setDefaultValues();
    }

    private void updateSemesterDetails(String selectedSemester) {
        Student currentStudent = (Student) DegreeWorksApplication.getInstance().getUser();
        Major studentMajor = currentStudent.getMajor();
        int semesterIndex = Integer.parseInt(selectedSemester.substring(9)); 
        
        // Clear existing information
        courseDetailsVBox.getChildren().clear();
    
        // Get all relevant courses
        ArrayList<Course> allCourses = new ArrayList<>(currentStudent.getOutstandingRequirements());
        allCourses.addAll(currentStudent.getEnrolledCourses()); 
        allCourses.addAll(currentStudent.getCompletedCoursesAsCourses()); 
    
        int courseCount = 1;
        for (Course course : allCourses) {
            String displayText = courseCount++ + ". " + course.getName() + " (" + course.getNumber() + ")\nCredits: " + course.getCreditHours();
            String gradeText = "";
    
            // Determine if the course is completed, enrolled, or planned
            CompletedCourse completed = currentStudent.findCompletedCourse(course.getId());
            if (completed != null) {
                gradeText = "Grade Received: " + completed.getGrade();
            } else if (currentStudent.isCurrentlyEnrolled(course)) {
                gradeText = "Enrolled";
            } else {
                String minGrade = studentMajor.getMinGradeForCourse(course.getId());
                gradeText = "Grade Required: " + (minGrade != null ? minGrade : "N/A");
            }
    
            displayText += "\n" + gradeText + "\n";
            courseDetailsVBox.getChildren().add(new Text(displayText));
        }
    }

    private void populateSemesterDropdown() {
        for (int i = 1; i <= 8; i++) {
            semesterDropdown.getItems().add("Semester " + i);
        }
        semesterDropdown.setOnAction(event -> updateSemesterDetails(semesterDropdown.getValue()));
    }


    private void setDefaultValues() {
        semesterDropdown.getSelectionModel().selectFirst();
        updateSemesterDetails(semesterDropdown.getValue());
    }
}
