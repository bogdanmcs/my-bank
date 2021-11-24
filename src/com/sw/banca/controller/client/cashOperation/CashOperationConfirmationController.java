package com.sw.banca.controller.client.cashOperation;

import com.sw.banca.misc.enums.BalanceType;
import com.sw.banca.misc.enums.CashOperationType;
import com.sw.banca.misc.enums.ServerResponse;
import com.sw.banca.misc.client.Transaction;
import com.sw.banca.model.Bank;
import com.sw.banca.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CashOperationConfirmationController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private CashOperationType cashOperationType;
    private BalanceType balanceType;
    private double totalAmount;

    public void proceed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../view/client/cashOperation/CashOperationResult.fxml"));
        root = loader.load();
        CashOperationResult cashOperationResult = loader.getController();
        Transaction transaction = new Transaction(cashOperationType, balanceType, totalAmount);
        String transactionResult = handleTransaction(transaction);
        cashOperationResult.setResultLabel(transactionResult);
        setStage(actionEvent);
    }

    private String handleTransaction(Transaction transaction) {
        ServerResponse serverResponse = getServerResponse(transaction);
        return getCorrespondingMessage(serverResponse);
    }

    private ServerResponse getServerResponse(Transaction transaction) {
        ServerResponse serverResponse;
        if (transaction.getCashOperationType() == CashOperationType.WITHDRAW) {
            serverResponse = Bank.getInstance().withdrawCash(UserSession.getInstance(), transaction);
        } else if (transaction.getCashOperationType() == CashOperationType.DEPOSIT) {
            serverResponse = Bank.getInstance().depositCash(UserSession.getInstance(), transaction);
        } else {
            throw new IllegalStateException();
        }
        return serverResponse;
    }

    private String getCorrespondingMessage(ServerResponse serverResponse) {
        String message;
        if (serverResponse == ServerResponse.OPERATION_SUCCESSFUL) {
            message = "Operation was successful.";
        } else if (serverResponse == ServerResponse.INSUFFICIENT_FUNDS) {
            message = "Insufficient funds.";
        } else {
            message = "Operation was unsuccessful. Please try again or ask the bank representatives for help.";
        }
        return message;
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../view/client/cashOperation/CashOperation.fxml"));
        root = loader.load();
        CashOperationController cashOperationController = loader.getController();
        cashOperationController.setCashOperationType(cashOperationType);
        cashOperationController.setBalanceType(balanceType);
        cashOperationController.setTotalAmount(totalAmount);
        setStage(actionEvent);
    }

    public void setCashOperationType(CashOperationType cashOperationType) {
        this.cashOperationType = cashOperationType;
    }

    public void setBalanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    private void setStage(ActionEvent actionEvent) {
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
