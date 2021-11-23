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

public class CashOperationBalanceTypeController {
    @FXML
    private Label infoLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private CashOperationType cashOperationType;

    public void selectEuroBalance(ActionEvent actionEvent) throws IOException {
        selectBalance(actionEvent, BalanceType.EURO);
    }

    public void selectRonBalance(ActionEvent actionEvent) throws IOException {
        selectBalance(actionEvent, BalanceType.RON);
    }

    private void selectBalance(ActionEvent actionEvent, BalanceType balanceType) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../view/client/cashOperation/CashOperation.fxml"));
        root = loader.load();
        CashOperationController cashOperationController = loader.getController();
        cashOperationController.setCashOperationType(cashOperationType);
        cashOperationController.setBalanceType(balanceType);
        setStage(actionEvent);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../view/client/ClientMenu.fxml"));
        root = loader.load();
        ClientMenuController clientMenuController = loader.getController();
        clientMenuController.setHelloLabel(String.valueOf(UserSession.getInstance().getCnp()));
        setStage(actionEvent);
    }

    public void setCashOperationType(CashOperationType cashOperationType){
        this.cashOperationType = cashOperationType;
        setInfoLabel();
    }

    private void setInfoLabel(){
        String message = "Please select the balance account from which you would like to ";
        if(cashOperationType == CashOperationType.WITHDRAW){
            message += "withdraw.";
        }
        else if(cashOperationType == CashOperationType.DEPOSIT){
            message += "deposit.";
        }
        else
        {
            throw new IllegalStateException();
        }
        infoLabel.setText(message);
    }

    private void setStage(ActionEvent actionEvent){
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
