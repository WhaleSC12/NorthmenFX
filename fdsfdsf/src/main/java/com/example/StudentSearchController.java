package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.util.converter.DefaultStringConverter;
import java.util.ArrayList;

import com.DegreeEZ.*;

public class StudentSearchController {

    @FXML private ImageView homeButton, searchButton, profileButton, semesterButton;
    @FXML private TextField searchTextField;
    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> nameColumn;
    @FXML private TableColumn<Course, String> subjectColumn;
    @FXML private TableColumn<Course, Integer> numberColumn;
    @FXML private TableColumn<Course, Boolean> electiveColumn;
    @FXML private TextArea courseInfoField;
    @FXML private Button registerButton;

    private Course selectedCourse;  // To hold the currently selected course

    @FXML
    public void initialize() {
        setupTableColumns();
        loadAllCourses();
    }

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        electiveColumn.setCellValueFactory(new PropertyValueFactory<>("elective"));
        
        // Format elective display
        electiveColumn.setCellFactory(column -> new TableCell<Course, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item ? "Yes" : "No");
                }
            }
        });

        // Making course names clickable
        nameColumn.setCellFactory(col -> {
            TableCell<Course, String> cell = new TableCell<>();
            cell.textProperty().bind(cell.itemProperty());  // Bind text property to the item property
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty()) {
                    Course rowData = cell.getTableRow().getItem();
                    courseInfoField.setText(formatCourseDetails(rowData));
                    selectedCourse = rowData;  // Set the selected course
                }
            });
            return cell;
        });
    }

    private String formatCourseDetails(Course course) {
        StringBuilder details = new StringBuilder();
        details.append("Course: ").append(course.getName())
               .append(" (").append(course.getSubject()).append(" ").append(course.getNumber()).append(")")
               .append("\nCredits: ").append(course.getCreditHours())
               .append("\nElective: ").append(course.getIsElective() ? "Yes" : "No");
    
        // Handling prerequisites
        if (!course.getPrerequisites().isEmpty()) {
            details.append("\nPrerequisites:");
            for (Prerequisite prereq : course.getPrerequisites()) {
                details.append("\n").append(prereq.printPrerequisites());  // Using the enhanced print method
            }
        } else {
            details.append("\nPrerequisites: None");
        }
    
        return details.toString();
    }


    private void performSearch() {
        String searchText = searchTextField.getText().trim().toLowerCase();
        ArrayList<Course> results = new ArrayList<>();
        for (Course c : CourseList.getCourses()) {
            if (c.getSubject().toString().toLowerCase().contains(searchText) ||
                c.getName().toLowerCase().contains(searchText) ||
                c.getNumber().toLowerCase().contains(searchText)) {
                results.add(c);
            }
        }
        courseTable.setItems(FXCollections.observableArrayList(results));
    }

    private void loadAllCourses() {
        ArrayList<Course> allCourses = CourseList.getCourses();
        courseTable.setItems(FXCollections.observableArrayList(allCourses));
    }

    private void registerSelectedCourse() {
        User currentUser = DegreeWorksApplication.getInstance().getUser();
        if (currentUser instanceof Student && selectedCourse != null) {
            Student student = (Student) currentUser;
            student.addEnrolledCourse(selectedCourse);  // Assuming Student class has this method
            System.out.println("Registered for course: " + selectedCourse.getName());
        } else {
            System.out.println("Registration failed: No course selected or user is not a student.");
        }
    }

}
