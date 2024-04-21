// Module info
module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.json;
    requires javafx.base;

    opens com.example to javafx.fxml;
    exports com.example;

    opens com.DegreeEZ to javafx.fxml;
    exports com.DegreeEZ;
}
