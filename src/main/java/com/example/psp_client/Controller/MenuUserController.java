package com.example.psp_client.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.psp_client.Model.MainModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MenuUserController extends SceneController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button changePass;

    @FXML
    private Button exit;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private Label menu;

    @FXML
    private Button studentsButton;

    @FXML
    void changePass(ActionEvent event) throws IOException {
        MainModel.scene = changePass.getScene();
        changeScene("changePassword", event);
    }

    @FXML
    void exit(ActionEvent event) throws IOException {
        changeScene("authorization", event);
    }

    @FXML
    void students(ActionEvent event) throws IOException {
        changeScene("studentsUser", event);
    }

    @FXML
    void initialize() {

    }

}
