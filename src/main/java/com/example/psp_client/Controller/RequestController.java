package com.example.psp_client.Controller;

import com.example.psp_client.Model.Account;
import com.example.psp_client.Model.Client;
import com.example.psp_client.Model.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RequestController extends SceneController{

    @FXML
    private TableView<Account> accountsTable;

    @FXML
    private Button changeAccess;

    @FXML
    private Button delRequest;

    @FXML
    private Label error;

    @FXML
    private Button exit;

    @FXML
    private TableColumn<Account, Integer> idAccounts;

    @FXML
    private TableColumn<Account, String> logAccounts;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private TableColumn<Account, String> roleAccounts;

    @FXML
    void exit(ActionEvent event) throws IOException {
        changeScene("accounts", event);

    }

    ObservableList<Account> observableListAccounts;
    ArrayList<Account> accounts = new ArrayList<>();
    Request req = new Request();
    Client client = new Client();

    @FXML
    void deleteRequestButton(ActionEvent event) {
        Account account = accountsTable.getSelectionModel().getSelectedItem();
        if (account == null) {
            error.setText("Выберите заявку");
            error.setVisible(true);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение изменения");
        alert.setHeaderText("Удаление заявки");
        alert.setContentText("Вы действительно хотите удалить заявку?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            req.setRequest("deleteAcc");
            req.setAccount(account);
            client.changeData(req);
            updateTable();
        }
    }

    @FXML
    void changeAccessButton(ActionEvent event) throws SQLException {
        Account account = accountsTable.getSelectionModel().getSelectedItem();
        if (account == null) {
            error.setText("Выберите заявку");
            error.setVisible(true);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение изменения");
        alert.setHeaderText("Подтверждение заявки");
        alert.setContentText("Вы действительно хотите подтвердить?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            req.setRequest("changeAccess");
            req.setAccount(account);
            client.changeData(req);
            updateTable();
        }

    }

    @FXML
    void initialize() throws SQLException {
        buildTable();

        changeAccess.setOnAction(event -> {
            try {
                changeAccessButton(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public void buildTable() {
        req.setRequest("getAccountsWithoutAccess");
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