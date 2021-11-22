package com.sw.banca.controller.client.cashOperation;

import com.sw.banca.misc.BalanceType;
import com.sw.banca.misc.CashOperationType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CashOperationResult {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label resultLabel;

    private CashOperationType cashOperationType;
    private BalanceType balanceType;
    private double totalAmount;

    public void returnToUserMenu(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../../../view/client/ClientMenu.fxml"));
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setResultLabel(String message){
        resultLabel.setText(message);
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
}
