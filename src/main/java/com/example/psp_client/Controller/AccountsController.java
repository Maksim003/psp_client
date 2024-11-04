package com.example.psp_client.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


import com.example.psp_client.Model.Account;
import com.example.psp_client.Model.Client;
import com.example.psp_client.Model.MainModel;
import com.example.psp_client.Model.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class AccountsController extends SceneController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exit;

    @FXML
    private Button request;

    @FXML
    private Button changeAccount;

    @FXML
    private Button delAccount;

    @FXML
    private Label error;

    @FXML
    private TableColumn<Account, String> logAccounts;

    @FXML
    private TableView<Account> accountsTable;

    @FXML
    private TableColumn<Account, Integer> idAccounts;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private TableColumn<Account, Integer> roleAccounts;

    ObservableList<Account> observableListAccounts;
    ArrayList<Account> accounts = new ArrayList<>();
    Request req = new Request();
    Client client = new Client();

    @FXML
    void changeRoleButton(ActionEvent event) {
        Account account = accountsTable.getSelectionModel().getSelectedItem();
        if (account == null) {
            error.setText("Выберите аккаунт");
            error.setVisible(true);
            return;
        }
        if(account.getLogin().equals(MainModel.currentLogin)) {
            error.setText("Нельзя изменить себе");
            error.setVisible(true);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение изменения");
        alert.setHeaderText("Изменение роли");
        alert.setContentText("Вы действительно хотите изменить роль?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            req.setRequest("changeRole");
            req.setAccount(account);
            client.changeData(req);
            updateTable();
        }
    }

    @FXML
    void deleteAccountButton(ActionEvent event) {
        Account account = accountsTable.getSelectionModel().getSelectedItem();
        if (account == null) {
            error.setText("Выберите аккаунт");
            error.setVisible(true);
            return;
        }
        if(account.getLogin().equals(MainModel.currentLogin)) {
            error.setText("Нельзя удалить себя");
            error.setVisible(true);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение удаление");
        alert.setHeaderText("Удаление аккаунта");
        alert.setContentText("Вы действительно хотите удалить аккаунт?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            req.setRequest("deleteAcc");
            req.setAccount(account);
            client.changeData(req);
            updateTable();
        }
    }


    @FXML
    void exit(ActionEvent event) throws IOException {
        changeScene("menuAdmin",event);
    }

    @FXML
    void request(ActionEvent event) throws IOException {
        changeScene("request", event);
    }

    @FXML
    void initialize() throws SQLException {
        buildTable();

        delAccount.setOnAction(this::deleteAccountButton);
        changeAccount.setOnAction(this::changeRoleButton);
    }

    public void buildTable() {
        req.setRequest("getAccountsWithAccess");
        accounts = client.getAccounts(req);
        idAccounts.setCellValueFactory(new PropertyValueFactory<>("id"));
        logAccounts.setCellValueFactory(new PropertyValueFactory<>("login"));
        roleAccounts.setCellValueFactory(new PropertyValueFactory<>("role"));
        observableListAccounts = FXCollections.observableArrayList(accounts);
        accountsTable.setItems(observableListAccounts);
    }

    public void updateTable() {
        observableListAccounts.clear();
        buildTable();
    }

}
