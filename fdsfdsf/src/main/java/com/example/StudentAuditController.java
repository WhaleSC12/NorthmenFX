package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import com.DegreeEZ.*;

import java.io.IOException;
import java.util.stream.IntStream;

public class StudentAuditController {

    @FXML private ComboBox<Student> studentDropdown;
    @FXML private ComboBox<Course> completedCourseDropdown;
    @FXML private ComboBox<Integer> semesterDropdown;
    @FXML private ComboBox<String> gradeDropdown;
    @FXML private ComboBox<Course> registerCourseDropdown;
    @FXML private ComboBox<Course> outstandingRequirementsDropdown;
    @FXML private ComboBox<Major> majorDropdown;
    @FXML private Button addCompletedCourseButton, addEnrolledCourseButton, addOutstandingRequirementButton, changeMajorButton, backButton;
    @FXML private Text nameField, majorField, gpaField, completedCourseConfirmation, outstandingRequirementConfirmation, registerCourseConfirmation, changeMajorConfirmation;

    private Advisor currentAdvisor;

    @FXML
    public void initialize() {
        currentAdvisor = (Advisor) DegreeWorksApplication.getInstance().getUser();
        studentDropdown.getItems().setAll(currentAdvisor.getStudents());
        gradeDropdown.getItems().setAll("A", "B", "C", "D", "F");
        semesterDropdown.getItems().setAll(IntStream.rangeClosed(1, 8).boxed().toArray(Integer[]::new));

        studentDropdown.setOnAction(this::loadStudentInfo);
        addCompletedCourseButton.setOnAction(this::addCompletedCourse);
        addEnrolledCourseButton.setOnAction(this::registerCourse);
        addOutstandingRequirementButton.setOnAction(this::addOutstandingRequirement);
        changeMajorButton.setOnAction(this::changeStudentMajor);
        backButton.setOnAction(e -> navigateBack());
    }

    private void loadStudentInfo(ActionEvent event) {
        Student selectedStudent = studentDropdown.getValue();
        if (selectedStudent != null) {
            nameField.setText(selectedStudent.getFirstName() + " " + selectedStudent.getLastName());
            majorField.setText(selectedStudent.getMajor().getMajorName());
            gpaField.setText(String.format("%.2f", selectedStudent.calculateGPA()));

            populateCourseDropdowns(selectedStudent);
        }
    }

    private void populateCourseDropdowns(Student student) {
        completedCourseDropdown.getItems().setAll(CourseList.getCourses());
        registerCourseDropdown.getItems().setAll(CourseList.getCourses().stream().filter(c -> !student.getCompletedCoursesAsCourses().contains(c)).toArray(Course[]::new));
        outstandingRequirementsDropdown.getItems().setAll(registerCourseDropdown.getItems());

        majorDropdown.getItems().setAll(MajorList.getMajors().stream().filter(m -> !m.getMajorID().equals(student.getMajor().getMajorID())).toArray(Major[]::new));
    }

    private void addCompletedCourse(ActionEvent event) {
        Student student = studentDropdown.getValue();
        Course course = completedCourseDropdown.getValue();
        Integer semester = semesterDropdown.getValue();
        String grade = gradeDropdown.getValue();

        if (student != null && course != null && semester != null && grade != null) {
            student.addCompletedCourse(new CompletedCourse(course.getId(), grade, semester));
            // Clear selections and confirm operation
            completedCourseDropdown.getSelectionModel().clearSelection();
            semesterDropdown.getSelectionModel().clearSelection();
            gradeDropdown.getSelectionModel().clearSelection();
            completedCourseConfirmation.setVisible(true);
        }
    }

    private void registerCourse(ActionEvent event) {
        Student student = studentDropdown.getValue();
        Course course = registerCourseDropdown.getValue();

        if (student != null && course != null) {
            student.addEnrolledCourse(course);
            // Update the dropdown and confirm operation
            registerCourseDropdown.getItems().remove(course);
            registerCourseConfirmation.setVisible(true);
        }
    }

    private void addOutstandingRequirement(ActionEvent event) {
        Student student = studentDropdown.getValue();
        Course course = outstandingRequirementsDropdown.getValue();

        if (student != null && course != null) {
            student.addOutstandingRequirement(course);
            // Update the dropdown and confirm operation
            outstandingRequirementsDropdown.getItems().remove(course);
            outstandingRequirementConfirmation.setVisible(true);
        }
    }

    private void changeStudentMajor(ActionEvent event) {
        Student student = studentDropdown.getValue();
        Major newMajor = majorDropdown.getValue();

        if (student != null && newMajor != null) {
            student.setMajor(newMajor);
            // Update fields and confirm operation
            majorField.setText(newMajor.getMajorName());
            changeMajorConfirmation.setText("Major changed to: " + newMajor.getMajorName());
            changeMajorConfirmation.setVisible(true);
        }
    }
    
    @FXML
    private void navigateBack() {
        navigateTo("advisor-student-search.fxml");
    }

    @FXML
    private void navigateTo(String fxmlFile) {
        System.out.println("Navigating to " + fxmlFile);
        try {
            App.setRoot(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
