module rau.inv_project {
    requires javafx.controls;
    requires javafx.fxml;

    opens model to javafx.base;
    opens rau.inv_project to javafx.fxml;
    exports rau.inv_project;
    exports model;
}