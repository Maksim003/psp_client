module com.example.psp_client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;

    opens com.example.psp_client to javafx.fxml;
    exports com.example.psp_client;
    exports com.example.psp_client.Controller;
    opens com.example.psp_client.Controller to javafx.fxml;
    exports com.example.psp_client.Model;
    opens com.example.psp_client.Model to javafx.fxml, com.fasterxml.jackson.databind;
}