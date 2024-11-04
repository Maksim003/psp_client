package com.example.psp_client.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.psp_client.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MarksController extends SceneController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label coursework;

    @FXML
    private Label economy;

    @FXML
    private Label ergaticSystem;

    @FXML
    private Button exit;

    @FXML
    private Label fio;

    @FXML
    private Label networks;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private Label programming;

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
        request.setRequest("getMarks");
        request.setStudents(students);
        Marks marks = client.getMarks(request);
        programming.setText(Integer.toString(marks.getProgramming()));
        networks.setText(Integer.toString(marks.getNetworks()));
        coursework.setText(Integer.toString(marks.getCoursework()));
        ergaticSystem.setText(Integer.toString(marks.getErgaticSystem()));
        economy.setText(Integer.toString(marks.getEconomy()));

    }

}
