package com.sw.banca.controller.client;

import com.sw.banca.controller.client.balanceChecker.BalanceCheckerController;
import com.sw.banca.controller.client.cashOperation.CashOperationBalanceTypeController;
import com.sw.banca.misc.client.AccountBalance;
import com.sw.banca.misc.enums.CashOperationType;
import com.sw.banca.misc.enums.ServerResponse;
import com.sw.banca.model.Bank;
import com.sw.banca.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ClientMenuController {
    @FXML
    private Label helloLabel;
    @FXML
    private Label statusLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void withdrawCash(ActionEvent actionEvent) throws IOException {
        setCashOperationType(CashOperationType.WITHDRAW);
        setStage(actionEvent);
    }

    public void depositCash(ActionEvent actionEvent) throws IOException {
        setCashOperationType(CashOperationType.DEPOSIT);
        setStage(actionEvent);
    }

    public void setCashOperationType(CashOperationType cashOperationType) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/client/cashOperation/CashOperationBalanceType.fxml"));
        root = loader.load();
        CashOperationBalanceTypeController cashOperationBalanceTypeController = loader.getController();
        cashOperationBalanceTypeController.setCashOperationType(cashOperationType);
    }

    public void checkBalance(ActionEvent actionEvent) throws IOException {
        AccountBalance accountBalance = Bank.getInstance().getBalanceQuery(UserSession.getInstance());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/client/balanceChecker/BalanceChecker.fxml"));
        root = loader.load();
        BalanceCheckerController balanceCheckerController = loader.getController();
        balanceCheckerController.setBalanceLabels(accountBalance);
        setStage(actionEvent);
    }

    public void deleteAccount(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bank");
        alert.setHeaderText("Clearance of accounts");
        alert.setContentText("Are you sure you want to delete your EURO and RON accounts?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ServerResponse serverResponse = Bank.getInstance().deleteAccount(UserSession.getInstance());
            if (serverResponse == ServerResponse.ACCOUNT_NOT_EMPTY) {
                setStatusLabel("Please withdraw all cash before deleting your account.", Color.RED);
            } else {
                terminateSession(actionEvent);
            }
        }
    }

    public void terminateSession(ActionEvent actionEvent) throws IOException {
        UserSession.getInstance().clearSession();
        root = FXMLLoader.load(getClass().getResource("../../view/auth/Login.fxml"));
        setStage(actionEvent);
    }

    public void setHelloLabel(String id){
        helloLabel.setText("Hello, " + id);
    }

    public void setStatusLabel(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setTextFill(color);
    }

    private void setStage(ActionEvent actionEvent) {
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
