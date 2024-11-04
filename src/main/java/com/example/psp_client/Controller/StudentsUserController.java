package com.example.psp_client.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.psp_client.Model.Client;
import com.example.psp_client.Model.MainModel;
import com.example.psp_client.Model.Request;
import com.example.psp_client.Model.Students;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class StudentsUserController extends SceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button checkMarksButton;

    @FXML
    private Button checkScholarshipButton;

    @FXML
    private TableColumn<Students, String> dateStudents;

    @FXML
    private Button exit;

    @FXML
    private TableColumn<Students, Integer> idStudents;

    @FXML
    private TableColumn<Students, String> nameStudents;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private TableColumn<Students, String> patronymicStudents;

    @FXML
    private TextField search;

    @FXML
    private Label studentsLabel;

    @FXML
    private Label error;

    @FXML
    private TableView<Students> studentsTable;

    @FXML
    private TableColumn<Students, String> surnameStudents;
    ObservableList<Students> observableListStudents;
    ArrayList<Students> students = new ArrayList<>();
    Request request = new Request();
    Client client = new Client();

    @FXML
    void checkMarks(ActionEvent event) throws IOException {
        Students students = studentsTable.getSelectionModel().getSelectedItem();
        if (students == null) {
            error.setText("Выберите студента");
            error.setVisible(true);
            return;
        }else {
            MarksController.students = students;
            MainModel.scene = checkMarksButton.getScene();
            changeScene("marks", event);
        }
    }

    @FXML
    void checkScholarship(ActionEvent event) throws IOException {
        Students students = studentsTable.getSelectionModel().getSelectedItem();
        if (students == null) {
            error.setText("Выберите студента");
            error.setVisible(true);
            return;
        }else {
            ScholarshipController.students = students;
            MainModel.scene = checkScholarshipButton.getScene();
            changeScene("scholarship", event);
        }
    }

    @FXML
    void exit(ActionEvent event) throws IOException {
        changeScene("menuUser", event);
    }

    @FXML
    void initialize() throws SQLException {
        buildTable();
        searchInTable();
    }

    public void searchInTable() {
        FilteredList<Students> filteredList = new FilteredList<>(observableListStudents, b -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(students -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String seachKey = newValue.toLowerCase();

                if (students.getSurname().toLowerCase().indexOf(seachKey) > -1) {
                    return true;
                } else if (students.getDate().toLowerCase().indexOf(seachKey) > -1) {
                    return true;
                } else if (students.getName().toString().indexOf(seachKey) > -1) {
                    return true;
                } else if (students.getPatronymic().toString().indexOf(seachKey) > -1) {
                    return true;
                }else if (students.getId().toString().indexOf(seachKey) > -1) {
                    return true;
                } else return false;
            });
        });

        SortedList<Students> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(studentsTable.comparatorProperty());
        studentsTable.setItems(sortedList);
    }


    public void buildTable() {
        request.setRequest("getStudents");
        students = client.getStudents(request);
        idStudents.setCellValueFactory(new PropertyValueFactory<>("id"));
        surnameStudents.setCellValueFactory(new PropertyValueFactory<>("surname"));
        nameStudents.setCellValueFactory(new PropertyValueFactory<>("name"));
        patronymicStudents.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        dateStudents.setCellValueFactory(new PropertyValueFactory<>("date"));
        observableListStudents = FXCollections.observableArrayList(students);
        studentsTable.setItems(observableListStudents);
    }

}
