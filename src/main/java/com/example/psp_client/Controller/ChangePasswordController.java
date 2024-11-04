package com.example.psp_client.Controller;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.psp_client.Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ChangePasswordController extends SceneController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button changePass;

    @FXML
    private Button exit;

    @FXML
    private PasswordField newPass;

    @FXML
    private PasswordField newPass1;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private PasswordField pass;

    @FXML
    private Label changePasswordLabel;

    @FXML
    private Label changePasswordError;

    Request request = new Request();
    Client client = new Client();
    Account account = new Account();
    MainModel mainModel = new MainModel();

    @FXML
    void exit(MouseEvent event) {
        exit.getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.setScene(MainModel.scene);
        stage.show();

    }

    @FXML
    void initialize() {
        changePass.setOnAction(event -> {
            String password = pass.getText().trim();
            String newPassword = newPass.getText().trim();
            String newPassword1 = newPass1.getText().trim();
            boolean isPassGood = isAlpha(newPassword);
            boolean isPassGood1 = isAlpha(newPassword1);
            String salt = "", hashPass;
            request.setRequest("getSalt");
            account.setLogin(MainModel.currentLogin);
            request.setAccount(account);
            salt = client.getString(request);
            hashPass = mainModel.getSecurePassword(password, salt);
            if (hashPass.equals(MainModel.currentPassword) && newPassword.equals(newPassword1) && !newPassword.equals("") && newPassword.length() > 4 && isPassGood && isPassGood1) {
                try {
                    changePassUser(salt);
                    pass.setStyle("-fx-border-color: white");
                    newPass.setStyle("-fx-border-color: white");
                    newPass1.setStyle("-fx-border-color: white");
                    changePasswordError.setVisible(false);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Изменение пароля");
                    alert.setHeaderText("Пароль изменён");
                    alert.showAndWait();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            } else if (!hashPass.equals(MainModel.currentPassword) || password.equals("")) {
                changePasswordError.setText("Неверный пароль");
                pass.setStyle("-fx-border-color: red");
                newPass.setStyle("-fx-border-color: white");
                newPass1.setStyle("-fx-border-color: white");
                changePasswordError.setVisible(true);
            } else if (newPassword.equals("") && newPassword1.equals("")) {
                changePasswordError.setText("Вы не ввели новый пароль");
                newPass.setStyle("-fx-border-color: red");
                newPass1.setStyle("-fx-border-color: red");
                pass.setStyle("-fx-border-color: white");
                changePasswordError.setVisible(true);
            } else if (!isPassGood && !isPassGood1) {
                changePasswordError.setText("Некорректный пароль");
                newPass.setStyle("-fx-border-color: red");
                newPass1.setStyle("-fx-border-color: red");
                pass.setStyle("-fx-border-color: white");
                changePasswordError.setVisible(true);
            } else if (!newPassword.equals(newPassword1)) {
                changePasswordError.setText("Пароли не совпадают");
                newPass.setStyle("-fx-border-color: red");
                newPass1.setStyle("-fx-border-color: red");
                pass.setStyle("-fx-border-color: white");
                changePasswordError.setVisible(true);
            } else if (newPassword.length() < 5 && newPassword1.length() < 5) {
                changePasswordError.setText("Пароль короткий");
                newPass.setStyle("-fx-border-color: red");
                pass.setStyle("-fx-border-color: white");
                changePasswordError.setVisible(true);
            }
        });
    }

    private void changePassUser(String salt) throws NoSuchAlgorithmException {
        String password = mainModel.getSecurePassword(newPass.getText(), salt);
        MainModel.currentPassword = password;
        account.setPassword(password);
        request.setAccount(account);
        request.setRequest("changePass");
        client.changeData(request);
    }

    public boolean isAlpha(String name) {
        return name.matches("^[a-zA-Z0-9]*$");
    }
}
