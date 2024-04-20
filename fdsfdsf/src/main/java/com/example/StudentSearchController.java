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
        setupButtons();
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
        return "Course " + course.getName() + "(" + course.getSubject() + " " + course.getNumber() + ")" +
        "\n" + "Credits: " + course.getCreditHours() + "\n" + "Elective: " + course.getIsElective() +
         "\n" + "Prerequisites: " + "\n" + course.printPrerequisites();
    }

    private void setupButtons() {
        searchButton.setOnMouseClicked(e -> performSearch());
        registerButton.setOnAction(e -> registerSelectedCourse());
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
        if (selectedCourse != null) {
            System.out.println("Registering for course: " + selectedCourse.getName());
            // Implement your registration logic here
        }
    }
}
