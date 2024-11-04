package com.example.psp_client.Controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.psp_client.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class StudentsController extends SceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label labelE;

    @FXML
    private Label labelN;

    @FXML
    private Label labelC;

    @FXML
    private Label labelErg;

    @FXML
    private Label labelP;

    @FXML
    private URL location;

    @FXML
    private Button addStudent;

    @FXML
    private Tab addStudentsTab;

    @FXML
    private Button changeBasicS;

    @FXML
    private Tab changeDataStudentsTab;

    @FXML
    private Button changeStudent;

    @FXML
    private Button check;

    @FXML
    private ComboBox<Integer> coursework;

    @FXML
    private ComboBox<Integer> coursework1;

    @FXML
    private DatePicker date;

    @FXML
    private DatePicker date1;

    @FXML
    private TableColumn<Students, String> dateStudents;

    @FXML
    private TableColumn<Students, String> dateStudents1;

    @FXML
    private Button deleteStudent;

    @FXML
    private ComboBox<Integer> economy;

    @FXML
    private ComboBox<Integer> economy1;

    @FXML
    private ComboBox<Integer> ergaticSystem;

    @FXML
    private ComboBox<Integer> ergaticSystem1;

    @FXML
    private Button checkScholarship;

    @FXML
    private Label error;

    @FXML
    private Label error1;

    @FXML
    private Label error2;

    @FXML
    private Button exit;

    @FXML
    private TableColumn<Students, Integer> idStudents;

    @FXML
    private TableColumn<Students, Integer> idStudents1;

    @FXML
    private TextField name;

    @FXML
    private TextField name1;

    @FXML
    private TableColumn<Students, String> nameStudents;

    @FXML
    private TableColumn<Students, String> nameStudents1;

    @FXML
    private ComboBox<Integer> networks;

    @FXML
    private ComboBox<Integer> networks1;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private TextField patronymic;

    @FXML
    private TextField patronymic1;

    @FXML
    private TableColumn<Students, String> patronymicStudents;

    @FXML
    private TableColumn<Students, String> patronymicStudents1;

    @FXML
    private ComboBox<Integer> programming;

    @FXML
    private ComboBox<Integer> programming1;

    @FXML
    private TextField search;

    @FXML
    private Label studentsLabel;

    @FXML
    private TabPane studentsPane;

    @FXML
    private Tab studentsTab;

    @FXML
    private TableView<Students> studentsTable;

    @FXML
    private TableView<Students> studentsTable1;

    @FXML
    private TextField surname;

    @FXML
    private TextField surname1;

    @FXML
    private TableColumn<Students, String> surnameStudents;

    @FXML
    private TableColumn<Students, String> surnameStudents1;

    ObservableList<Students> observableListStudents;
    ObservableList<Integer> observableList = FXCollections.observableArrayList(4, 5, 6, 7, 8, 9, 10);
    ArrayList<Students> students = new ArrayList<>();
    Request request = new Request();
    Client client = new Client();
    Marks marks = new Marks();


    @FXML
    void checkStudentsButton(ActionEvent event) throws IOException {
        Students students = studentsTable.getSelectionModel().getSelectedItem();
        if (students == null) {
            error.setText("Выберите студента");
            error.setVisible(true);
            return;
        }else {
            MainModel.scene = check.getScene();
            MarksController.students = students;
            changeScene("marks", event);
        }

    }

    @FXML
    void exit(ActionEvent event) throws IOException {
        changeScene("menuAdmin", event);
    }

    @FXML
    void checkScholarshipButton(ActionEvent event) throws IOException {
        Students students = studentsTable.getSelectionModel().getSelectedItem();
        if (students == null) {
            error.setText("Выберите студента");
            error.setVisible(true);
            return;
        }else {
            MainModel.scene = checkScholarship.getScene();
            ScholarshipController.students = students;
            changeScene("scholarship", event);
        }
    }

    @FXML
    void changeBasicS(ActionEvent event) throws IOException {
        changeScene("basicScholarship", event);
    }

    @FXML
    void deleteStudentsButton(ActionEvent event) {
        Students students = studentsTable.getSelectionModel().getSelectedItem();
        if (students == null) {
            error.setText("Выберите студента");
            error.setVisible(true);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение удаление");
        alert.setHeaderText("Удаление студента");
        alert.setContentText("Вы действительно хотите удалить студента?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            request.setRequest("delStudent");
            request.setStudents(students);
            client.changeData(request);
            updateTable(studentsTable);
            updateTable(studentsTable1);
        }
    }

    @FXML
    void initialize() {
        buildTable(idStudents, surnameStudents, nameStudents, patronymicStudents, dateStudents, studentsTable);
        buildTable(idStudents1, surnameStudents1, nameStudents1, patronymicStudents1, dateStudents1, studentsTable1);
        addStudentsButton();
        deleteStudent.setOnAction(this::deleteStudentsButton);
        studentsTable1.setOnMouseClicked(event -> {
            Students students = studentsTable1.getSelectionModel().getSelectedItem();
            request.setRequest("getMarks");
            request.setStudents(students);
            Marks marks = client.getMarks(request);
            int id = 0;
            if (students != null) {
                surname1.setText(students.getSurname());
                surname1.setVisible(true);
                name1.setText(students.getName());
                name1.setVisible(true);
                patronymic1.setText(students.getPatronymic());
                patronymic1.setVisible(true);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(students.getDate(), formatter);
                date1.setValue(localDate);
                date1.setVisible(true);
                economy1.setValue(marks.getEconomy());
                economy1.setVisible(true);
                programming1.setValue(marks.getProgramming());
                programming1.setVisible(true);
                coursework1.setValue(marks.getCoursework());
                coursework1.setVisible(true);
                networks1.setValue(marks.getNetworks());
                networks1.setVisible(true);
                ergaticSystem1.setValue(marks.getErgaticSystem());
                ergaticSystem1.setVisible(true);
                changeStudent.setVisible(true);
                labelVisible(true);
                id = students.getId();
                changeDataStudents(id);
            }
        });
        searchInTable();
    }

    public void labelVisible(boolean flag) {
        labelP.setVisible(flag);
        labelN.setVisible(flag);
        labelC.setVisible(flag);
        labelE.setVisible(flag);
        labelErg.setVisible(flag);
    }

    public void changeDataStudents(int id) {
        coursework1.setItems(observableList);
        programming1.setItems(observableList);
        ergaticSystem1.setItems(observableList);
        networks1.setItems(observableList);
        economy1.setItems(observableList);
        changeStudent.setOnAction(event -> {
            String s = surname1.getText().trim();
            String n = name1.getText().trim();
            String p = patronymic1.getText().trim();
            boolean isSurname = isAlpha(s);
            boolean isName = isAlpha(n);
            boolean isPatronymic = isAlpha(p);
            Integer comboBoxCoursework = coursework1.getSelectionModel().getSelectedItem();
            Integer comboBoxEconomy = economy1.getSelectionModel().getSelectedItem();
            Integer comboBoxNetworks = networks1.getSelectionModel().getSelectedItem();
            Integer comboBoxES = ergaticSystem1.getSelectionModel().getSelectedItem();
            Integer comboBoxProgramming = programming1.getSelectionModel().getSelectedItem();
            Date currentDate = new Date();
            Date startDate = getDate(date1, 10);
            Date startDate1 = getDate(date1, 70);
            if (s.equals("")) {
                error2.setText("Введите Фамилию");
                error2.setVisible(true);
            } else if (n.equals("")) {
                error2.setText("Введите Имя");
                error2.setVisible(true);
            } else if (p.equals("")) {
                error2.setText("Введите Отчество");
                error2.setVisible(true);
            } else if (!isSurname) {
                error2.setText("Некорректно введена фамилия");
                error2.setVisible(true);
            } else if (!isName) {
                error2.setText("Некорректно введено имя");
                error2.setVisible(true);
            } else if (!isPatronymic) {
                error2.setText("Некорректно введено отчество");
                error2.setVisible(true);
            } else if (currentDate.getTime() < startDate.getTime() || currentDate.getTime() > startDate1.getTime()) {
                error2.setText("Некорректно введена дата");
                error2.setVisible(true);
            } else if (date1.getValue() == null) {
                error2.setText("Выберите дату");
                error2.setVisible(true);
            } else if (comboBoxCoursework == null || comboBoxProgramming == null || comboBoxEconomy == null || comboBoxES == null || comboBoxNetworks == null) {
                error2.setText("Выберите оценку за экзамен");
                error2.setVisible(true);
            } else {
                error2.setVisible(false);
                request.setRequest("changeStudent");
                Students student = new Students(id, surname1.getText(), name1.getText(), patronymic1.getText(), date1.getValue().toString().replace("-", ""));
                Marks marks1 = new Marks(coursework1.getValue(), economy1.getValue(), networks1.getValue(), programming1.getValue(), ergaticSystem1.getValue(), id);
                request.setStudents(student);
                request.setMarks(marks1);
                client.changeData(request);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Изменение");
                alert.setHeaderText("Данные изменены");
                alert.showAndWait();
                request.setRequest("calculate");
                client.changeData(request);
                updateTable(studentsTable);
                updateTable(studentsTable1);
                visible();
                labelVisible(false);
            }
        });
    }

    public void visible() {
        surname1.setVisible(false);
        name1.setVisible(false);
        patronymic1.setVisible(false);
        date1.setVisible(false);
        coursework1.setVisible(false);
        programming1.setVisible(false);
        networks1.setVisible(false);
        ergaticSystem1.setVisible(false);
        economy1.setVisible(false);
        changeStudent.setVisible(false);
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
                } else if (students.getId().toString().indexOf(seachKey) > -1) {
                    return true;
                } else return false;
            });
        });
        SortedList<Students> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(studentsTable.comparatorProperty());
        studentsTable.setItems(sortedList);
    }

    public void addStudentsButton() {
        coursework.setItems(observableList);
        programming.setItems(observableList);
        ergaticSystem.setItems(observableList);
        networks.setItems(observableList);
        economy.setItems(observableList);
        addStudent.setOnAction(event -> {
            String s = surname.getText().trim();
            String n = name.getText().trim();
            String p = patronymic.getText().trim();
            boolean isSurname = isAlpha(s);
            boolean isName = isAlpha(n);
            boolean isPatronymic = isAlpha(p);
            Integer comboBoxCoursework = coursework.getSelectionModel().getSelectedItem();
            Integer comboBoxEconomy = economy.getSelectionModel().getSelectedItem();
            Integer comboBoxNetworks = networks.getSelectionModel().getSelectedItem();
            Integer comboBoxES = ergaticSystem.getSelectionModel().getSelectedItem();
            Integer comboBoxProgramming = programming.getSelectionModel().getSelectedItem();
            Date currentDate = new Date();
            Date startDate = getDate(date, 10);
            Date startDate1 = getDate(date, 70);
            if (s.equals("")) {
                error1.setText("Введите Фамилию");
                error1.setVisible(true);
            } else if (n.equals("")) {
                error1.setText("Введите Имя");
                error1.setVisible(true);
            } else if (p.equals("")) {
                error1.setText("Введите Отчество");
                error1.setVisible(true);
            } else if (!isSurname) {
                error1.setText("Некорректно введена фамилия");
                error1.setVisible(true);
            } else if (!isName) {
                error1.setText("Некорректно введено имя");
                error1.setVisible(true);
            } else if (!isPatronymic) {
                error1.setText("Некорректно введено отчество");
                error1.setVisible(true);
            } else if (currentDate.getTime() < startDate.getTime() || currentDate.getTime() > startDate1.getTime()) {
                error1.setText("Некорректно введена дата");
                error1.setVisible(true);
            } else if (date.getValue() == null) {
                error1.setText("Выберите дату");
                error1.setVisible(true);
            } else if (comboBoxCoursework == null || comboBoxProgramming == null || comboBoxEconomy == null || comboBoxES == null || comboBoxNetworks == null) {
                error1.setText("Выберите оценку за экзамен");
                error1.setVisible(true);
            }  else {
                addStudentsToTable();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Добавление");
                alert.setHeaderText("Студент добавлен");
                alert.showAndWait();
                request.setRequest("getBasicS");
                double basicS = client.getDouble(request);
                request.setRequest("changeBasicS");
                request.setBasicS(basicS);
                client.changeData(request);
                request.setRequest("calculate");
                request.setMarks(marks);
                client.changeData(request);
                updateTable(studentsTable);
                updateTable(studentsTable1);
                clearFields();
            }
        });
    }

    public void clearFields() {
        error1.setVisible(false);
        surname.clear();
        name.clear();
        patronymic.clear();
        coursework.setValue(null);
        date.setValue(null);
        programming.setValue(null);
        economy.setValue(null);
        networks.setValue(null);
        ergaticSystem.setValue(null);
    }

    public Date getDate(DatePicker date, int date1) {
        Date startDate = new Date();
        if (date.getValue() != null) {
            LocalDate d = date.getValue().plusYears(date1);
            String str = d.toString();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                startDate = df.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return startDate;
    }

    public void buildTable
            (TableColumn<Students, Integer> id, TableColumn<Students, String> surname, TableColumn<Students, String> name,
             TableColumn<Students, String> patronymic, TableColumn<Students, String> date, TableView<Students> tableView) {
        request.setRequest("getStudents");
        students = client.getStudents(request);
        observableListStudents = FXCollections.observableArrayList(students);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        patronymic.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableView.setItems(observableListStudents);
    }

    public void updateTable(TableView<Students> tableView) {
        request.setRequest("getStudents");
        students = client.getStudents(request);
        observableListStudents = FXCollections.observableArrayList(students);
        tableView.setItems(observableListStudents);
        searchInTable();

    }


    public void addStudentsToTable() {
        Students students = new Students(surname.getText(), name.getText(), patronymic.getText(), date.getValue().toString().replace("-", ""));
        marks = new Marks(coursework.getValue(), economy.getValue(), networks.getValue(), programming.getValue(), ergaticSystem.getValue());
        request.setRequest("addStudent");
        request.setStudents(students);
        client.changeData(request);
        request.setRequest("addMarks");
        request.setMarks(marks);
        client.changeData(request);

    }
    public boolean isAlpha(String name) {
        return name.matches("^[ A-Za-z]+$") || name.matches("^[ А-ЯЁа-ё]+$");
    }
}
