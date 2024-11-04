package com.example.psp_client.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.psp_client.Model.Client;
import com.example.psp_client.Model.MainModel;
import com.example.psp_client.Model.Request;
import com.example.psp_client.Model.Students;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScholarshipController extends SceneController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label changeBasicSLabel;

    @FXML
    private Button exit;

    @FXML
    private Label fio;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private Label scholarship;

    static Students students;
    Request request = new Request();
    Client client = new Client();


    @FXML
    void exit(ActionEvent event) throws IOException {
        exit.getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.setScene(MainModel.scene);
        stage.show();
    }

    @FXML
    void initialize() {
        fio.setText(students.getSurname() + " " + students.getName() + " " + students.getPatronymic());
        request.setRequest("getScholarship");
        request.setStudents(students);
        double sp = client.getDouble(request);
        scholarship.setText(Double.toString(sp));
    }

}
