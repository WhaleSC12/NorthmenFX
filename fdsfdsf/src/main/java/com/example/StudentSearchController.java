package com.example;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import com.DegreeEZ.*;

public class StudentSearchController {

    @FXML private ImageView homeButton, searchButton, profileButton, semesterButton;
    @FXML private TextField searchTextField;
    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> nameColumn;
    @FXML private TableColumn<Course, String> subjectColumn;
    @FXML private TableColumn<Course, Integer> numberColumn;
    @FXML private TableColumn<Course, Integer> creditsColumn;
    @FXML private TableColumn<Course, Integer> recommendedSemesterColumn;
    @FXML private TableColumn<Course, Void> registerColumn;

    private Student currentStudent;
    private ArrayList<Course> searchResults;

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
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("creditHours"));
        recommendedSemesterColumn.setCellValueFactory(new PropertyValueFactory<>("reccomendedSemester"));
        
        // Add a registration button to each row
        registerColumn.setCellFactory(param -> new TableCell<Course, Void>() {
            private final Button registerButton = new Button("Register");
            {
                registerButton.setOnAction(e -> {
                    Course course = getTableView().getItems().get(getIndex());
                    registerCourse(course);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : registerButton);
            }
        });
    }

    private void setupButtons() {
        searchButton.setOnMouseClicked(e -> performSearch());
    }

    private void performSearch() {
        String searchText = searchTextField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            loadAllCourses(); // Reloads all courses if search field is empty
            return;
        }
        ArrayList<Course> results = new ArrayList<>();
        for (Course c : CourseList.getCourses()) {
            // Check if course subject or name contains the search text
            if (c.getSubject().name().toLowerCase().contains(searchText) ||
                c.getName().toLowerCase().contains(searchText) ||
                c.courseCode().toLowerCase().contains(searchText)) {
                results.add(c);
            }
        }
        courseTable.setItems(FXCollections.observableArrayList(results));
    }
    

    private void loadAllCourses() {
        ArrayList<Course> allCourses = CourseList.getCourses();
        courseTable.setItems(FXCollections.observableArrayList(allCourses));
    }

    private void registerCourse(Course course) {
        // Registration logic here, possibly involving a dialog to confirm
        System.out.println("Registering for course: " + course.getName());
    }
}
