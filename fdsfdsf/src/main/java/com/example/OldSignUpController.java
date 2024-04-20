package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import com.DegreeEZ.CourseList;
import com.DegreeEZ.Course;
import com.DegreeEZ.MajorList;
import com.DegreeEZ.Major;
import com.DegreeEZ.Advisor;
import com.DegreeEZ.AdvisorList;
import com.DegreeEZ.CompletedCourse;
import com.DegreeEZ.DegreeWorksApplication;
import com.DegreeEZ.Student;
import com.DegreeEZ.StudentList;

import java.util.ArrayList;
import java.util.UUID;

public class SignUpController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField, userNameField, passwordField, studentsField;
    @FXML
    private CheckBox advisorCheckBox;
    @FXML
    private Text majorText, yearText, completedCourseText, gradeText, semesterText, enrolledText, advisorText;
    @FXML
    private ComboBox<Course> coursesDropdown, currentCoursesDropdown;
    @FXML
    private ComboBox<String> gradeDropdown, yearDropdown;
    @FXML
    private ComboBox<Integer> semesterDropdown;
    @FXML
    private ComboBox<Major> majorDropdown;
    @FXML
    private ComboBox<Advisor> advisorDropdown;
    @FXML
    private Button continueButton, addStudentsBtn, addCompletedCourseBtn;
    @FXML
    private Label errorLabel;

    private ArrayList<CompletedCourse> completedCourses = new ArrayList<>();
    private ArrayList<UUID> studentUUIDs = new ArrayList<>();
    private ArrayList<Course> enrolledCourses = new ArrayList<>();

    @FXML
    public void initialize() {
        populateCoursesDropdown();
        populateGradeDropdown();
        populateSemesterDropdown();
        populateYearDropdown();
        populateMajorDropdown();
        toggleAdvisorFields(false); // Initially set to student mode
    }

    private void populateCoursesDropdown() {
        coursesDropdown.getItems().addAll(CourseList.getCourses());
        currentCoursesDropdown.getItems().addAll(CourseList.getCourses());
    }

    private void populateGradeDropdown() {
        gradeDropdown.getItems().addAll("A", "B", "C", "D", "F");
    }

    private void populateSemesterDropdown() {
        semesterDropdown.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8);
    }

    private void populateYearDropdown() {
        yearDropdown.getItems().addAll("Freshman", "Sophomore", "Junior", "Senior");
    }

    private void populateMajorDropdown() {
        majorDropdown.getItems().addAll(MajorList.getMajors());
    }

    private void populateAdvisorDropdown() {
        advisorDropdown.getItems().addAll(AdvisorList.getAdvisors());
    }

    @FXML
    private void handleAdvisorCheck() {
        toggleAdvisorFields(advisorCheckBox.isSelected());
    }

    private void toggleAdvisorFields(boolean isAdvisor) {
        majorDropdown.setVisible(!isAdvisor);
        yearDropdown.setVisible(!isAdvisor);
        coursesDropdown.setVisible(!isAdvisor);
        gradeDropdown.setVisible(!isAdvisor);
        semesterDropdown.setVisible(!isAdvisor);
        addCompletedCourseBtn.setVisible(!isAdvisor);
        majorText.setVisible(!isAdvisor);
        completedCourseText.setVisible(!isAdvisor);
        gradeText.setVisible(!isAdvisor);
        semesterText.setVisible(!isAdvisor);
        enrolledText.setVisible(!isAdvisor);
        advisorText.setVisible(!isAdvisor);
        
        studentsField.setVisible(isAdvisor);
        addStudentsBtn.setVisible(isAdvisor);
    }

    @FXML
    private void addCompletedCourse() {
        Course selectedCourse = coursesDropdown.getSelectionModel().getSelectedItem();
        String selectedGrade = gradeDropdown.getSelectionModel().getSelectedItem();
        Integer selectedSemester = semesterDropdown.getSelectionModel().getSelectedItem();

        if (selectedCourse != null && selectedGrade != null && selectedSemester != null) {
            CompletedCourse newCourse = new CompletedCourse(selectedCourse.getId(), selectedGrade, selectedSemester);
            completedCourses.add(newCourse);
            coursesDropdown.getSelectionModel().clearSelection();
            gradeDropdown.getSelectionModel().clearSelection();
            semesterDropdown.getSelectionModel().clearSelection();
        } else {
            errorLabel.setText("Please select a course, grade, and semester to add.");
            errorLabel.setVisible(true);
        }
    }

    @FXML
    private void handleSignUp() {
        if (advisorCheckBox.isSelected()) {
            createAdvisorAccount();
        } else {
            createStudentAccount();
        }
    }

    @FXML
    private void addStudent() {
        String fullName = studentsField.getText();
        String[] names = fullName.split(" ");
        if (names.length == 2) {
            String firstName = names[0];
            String lastName = names[1];
            Student foundStudent = StudentList.getStudents().stream()
                .filter(s -> s.getFirstName().equals(firstName) && s.getLastName().equals(lastName))
                .findFirst().orElse(null);
            if (foundStudent != null) {
                studentUUIDs.add(foundStudent.getUUID());
                studentsField.clear();
            } else {
                errorLabel.setText("Student not found.");
                errorLabel.setVisible(true);
            }
        } else {
            errorLabel.setText("Please enter both first and last name.");
            errorLabel.setVisible(true);
        }
    }

    @FXML
    private void addEnrolledCourse() {
        Course selectedCourse = currentCoursesDropdown.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            enrolledCourses.add(selectedCourse);
            currentCoursesDropdown.getSelectionModel().clearSelection();
        } else {
            errorLabel.setText("Please select a course to add.");
            errorLabel.setVisible(true);
        }
    }
    
    @FXML
    private void createStudentAccount() {
        Major selectedMajor = majorDropdown.getSelectionModel().getSelectedItem();
        String selectedYear = yearDropdown.getSelectionModel().getSelectedItem();
        Advisor selectedAdvisor = advisorDropdown.getSelectionModel().getSelectedItem();

        int semestersTaken = 0;
        if (selectedYear.equals("Freshman")) {
            semestersTaken = 0;
        } else if (selectedYear.equals("Sophomore")) {
            semestersTaken = 2;
        } else if (selectedYear.equals("Junior")) {
            semestersTaken = 4;
        } else if (selectedYear.equals("Senior")) {
            semestersTaken = 6;
        }
        DegreeWorksApplication.getInstance().createAccount(false, firstNameField.getText(), lastNameField.getText(), userNameField.getText(), passwordField.getText(), selectedMajor, completedCourses, enrolledCourses, new ArrayList<>(), selectedAdvisor.getUUID(), null, semestersTaken, "",null); 
    }

    private void createAdvisorAccount() {
        if (!studentUUIDs.isEmpty()) {
            DegreeWorksApplication.getInstance().createAccount(true, firstNameField.getText(), lastNameField.getText(), userNameField.getText(), passwordField.getText(), null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>(), 0,"", studentUUIDs);
        } else {
            errorLabel.setText("No students added.");
            errorLabel.setVisible(true);
        }
    }
}
