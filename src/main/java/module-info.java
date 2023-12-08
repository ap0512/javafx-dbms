module com.example.dbmsse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.dlsc.formsfx;

    opens com.example.dbmsse to javafx.fxml;
    exports com.example.dbmsse;
}