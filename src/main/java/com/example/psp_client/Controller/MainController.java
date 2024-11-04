package com.example.psp_client.Controller;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.psp_client.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class MainController extends SceneController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label enterError;

    @FXML
    private Label authorization;

    @FXML
    private Button enter;

    @FXML
    private TextField login;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private PasswordField password;

    @FXML
    private Button registration;

    Request request = new Request();
    Client client = new Client();
    MainModel mainModel = new MainModel();

    @FXML
    void openRegScene(ActionEvent event) throws IOException {
        changeScene("registration", event);
    }

    @FXML
    void initialize() {
        enter.setOnAction(event -> {
            String log = login.getText().trim();
            String pass = password.getText().trim();
            if (!log.equals("") && !pass.equals("")) {
                try {
                    logUser(log, pass, event);
                } catch (NoSuchAlgorithmException | SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
            if (log.equals("")) {
                enterError.setText("Вы не ввели логин");
                login.setStyle("-fx-border-color: red");
                password.setStyle("-fx-border-color: white");
                enterError.setVisible(true);
            }
            if (pass.equals("") && !log.equals("")) {
                enterError.setText("Вы не ввели пароль");
                password.setStyle("-fx-border-color: red");
                login.setStyle("-fx-border-color: white");
                enterError.setVisible(true);
            }
        });
    }

    private void logUser(String log, String pass, ActionEvent event) throws NoSuchAlgorithmException, SQLException, IOException {
        Account account = new Account();
        String salt = "", password;
        account.setLogin(log);
        request.setRequest("getSalt");
        request.setAccount(account);
        salt = client.getString(request);
        password = mainModel.getSecurePassword(pass, salt);
        account.setPassword(password);
        request.setRequest("getAcc");
        request.setAccount(account);
        account = client.getAcc(request);
        if (account.getLogin() != null) {
            MainModel.currentLogin = log;
            MainModel.currentPassword = password;
            if (account.getRole() == 1 && account.getAccess() == 1 ) {
                changeScene("menuAdmin", event);
            } else if (account.getRole() == 0 && account.getAccess() == 1) {
                changeScene("menuUser", event);
            } else {
                enterError.setText("Аккаунт не подтверждён");
                enterError.setVisible(true);
            }
        } else {
            enterError.setText("Такого аккаунта нет");
            enterError.setVisible(true);
        }

    }

}
