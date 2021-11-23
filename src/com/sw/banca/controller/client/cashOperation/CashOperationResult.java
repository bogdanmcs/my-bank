package com.sw.banca.controller.client.cashOperation;

import com.sw.banca.controller.client.ClientMenuController;
import com.sw.banca.misc.enums.BalanceType;
import com.sw.banca.misc.enums.CashOperationType;
import com.sw.banca.model.UserSession;
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

    public void returnToClientMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../view/client/ClientMenu.fxml"));
        root = loader.load();
        ClientMenuController clientMenuController = loader.getController();
        clientMenuController.setHelloLabel(String.valueOf(UserSession.getInstance().getCnp()));
        setStage(actionEvent);
    }

    public void setResultLabel(String message){
        resultLabel.setText(message);
    }

    public void setCashOperationType(CashOperationType cashOperationType) {
        this.cashOperationType = cashOperationType;
    }

    private void setStage(ActionEvent actionEvent){
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setBalanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
