package com.example.psp_client.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.psp_client.Model.Client;
import com.example.psp_client.Model.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class BasicScholarshipController extends SceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField basicS;

    @FXML
    private Button change;

    @FXML
    private Label changeBasicSLabel;

    @FXML
    private Label error;

    @FXML
    private Button exit;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    Request request = new Request();
    Client client = new Client();

    @FXML
    void exit(ActionEvent event) throws IOException {
        changeScene("students", event);
    }

    @FXML
    void initialize() {
        change.setOnAction(event -> {
            String basicScholarship = basicS.getText().trim();
            boolean isGood = isBasicS(basicScholarship);
            double bS = 0;
            if (!basicScholarship.equals("") && isGood) {
                bS = Double.parseDouble(basicScholarship);
                request.setRequest("changeBasicS");
                request.setBasicS(bS);
                client.changeData(request);
            }
            if (basicScholarship.equals("")) {
                error.setText("Введите данные");
                error.setVisible(true);
                basicS.setStyle("-fx-border-color: red");
            } else if (bS <= 0 || !isGood) {
                error.setText("Введите данные корректно");
                error.setVisible(true);
                basicS.setStyle("-fx-border-color: red");
            } else {
                error.setVisible(false);
                basicS.setStyle("-fx-border-color: white");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Базовая стипендия изменена");
                alert.showAndWait();
                request.setRequest("updateScholarship");
                client.changeData(request);
            }
        });

    }

    public boolean isBasicS(String name) {
        return name.matches("^[0-9]*\\.?[0-9]+$");
    }
}
