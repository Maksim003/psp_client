package com.example.psp_client.Controller;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.psp_client.Model.Account;
import com.example.psp_client.Model.Client;
import com.example.psp_client.Model.MainModel;
import com.example.psp_client.Model.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class RegController extends SceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label logInError;

    @FXML
    private Button authorization;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private Button regEnter;

    @FXML
    private TextField regLogin;

    @FXML
    private PasswordField regPassword;

    @FXML
    private PasswordField regPassword1;

    @FXML
    private Label registration;

    Client client = new Client();
    Request request = new Request();
    MainModel mainModel = new MainModel();

    @FXML
    void initialize() {
        regEnter.setOnAction(event -> {
            String log = regLogin.getText().trim();
            String pass = regPassword.getText().trim();
            String pass1 = regPassword1.getText().trim();
            boolean isGoodLog = isAlpha(log);
            boolean isGoodPass = isAlpha(pass);
            boolean isGoodPass1 = isAlpha(pass1);
            Account account = new Account();
            account.setLogin(log);
            account.setPassword(pass);
            request.setRequest("getSalt");
            request.setAccount(account);
            String date = client.getString(request);
            try {
                if (!date.equalsIgnoreCase("")) {
                    logInError.setText("Такой логин уже есть");
                    regLogin.setStyle("-fx-border-color: red");
                    logInError.setVisible(true);
                } else if (log.equals("")) {
                    logInError.setText("Вы не ввели логин");
                    regLogin.setStyle("-fx-border-color: red");
                    regPassword.setStyle("-fx-border-color: white");
                    regPassword1.setStyle("-fx-border-color: white");
                    logInError.setVisible(true);
                } else if (!isGoodLog) {
                    logInError.setText("Некорректный логин");
                    regLogin.setStyle("-fx-border-color: red");
                    regPassword.setStyle("-fx-border-color: white");
                    regPassword1.setStyle("-fx-border-color: white");
                    logInError.setVisible(true);
                } else if (!isGoodPass && !isGoodPass1) {
                    logInError.setText("Некорректный пароль");
                    regLogin.setStyle("-fx-border-color: white");
                    regPassword.setStyle("-fx-border-color: red");
                    regPassword1.setStyle("-fx-border-color: red");
                    logInError.setVisible(true);
                } else if (!(pass.equals(pass1))) {
                    logInError.setText("Пароли не совпадают");
                    regLogin.setStyle("-fx-border-color: white;");
                    regPassword.setStyle("-fx-border-color: red");
                    regPassword1.setStyle("-fx-border-color: red");
                    logInError.setVisible(true);
                } else if (pass.equals("") && pass1.equals("")) {
                    logInError.setText("Вы не ввели пароль");
                    regLogin.setStyle("-fx-border-color: white;");
                    regPassword.setStyle("-fx-border-color: red");
                    regPassword1.setStyle("-fx-border-color: red");
                    logInError.setVisible(true);
                } else if (pass.length() < 5 && pass1.length() < 5) {
                    logInError.setText("Пароль короткий");
                    regLogin.setStyle("-fx-border-color: white");
                    regPassword.setStyle("-fx-border-color: red");
                    logInError.setVisible(true);
                } else {
                    signUpUser();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Регистрация");
                    alert.setHeaderText("Вы успешно зарегистрировались");
                    alert.showAndWait();
                    logInError.setVisible(false);
                    regLogin.setStyle("-fx-border-color: white;");
                    regLogin.setText("");
                    regPassword.setStyle("-fx-border-color: white");
                    regPassword.setText("");
                    regPassword1.setStyle("-fx-border-color: white");
                    regPassword1.setText("");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void enter(ActionEvent event) throws IOException {
        changeScene("authorization", event);
    }

    private void signUpUser() throws NoSuchAlgorithmException {
        String salt = mainModel.getSalt();
        String password = mainModel.getSecurePassword(regPassword.getText(), salt);
        Account account = new Account(regLogin.getText(), password, 0, 0, salt);
        request.setRequest("addAcc");
        request.setAccount(account);
        client.changeData(request);
    }

    public boolean isAlpha(String name) {
        return name.matches("^[a-zA-Z0-9]*$");
    }
}