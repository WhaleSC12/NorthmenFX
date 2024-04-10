module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example to javafx.fxml;
    exports com.example;

    opens com.DegreeEZ to javafx.fxml;
    exports com.DegreeEZ;
}
