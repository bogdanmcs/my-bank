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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CashOperationController {
    @FXML
    private TextField saveAmountTextField;
    @FXML
    private Label balanceTypeLabel;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label infoLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private CashOperationType cashOperationType;
    private BalanceType balanceType;
    private double totalAmount = 0;

    public void addAmount1(){
        totalAmount += 1;
        setTotalAmountLabel();
    }

    public void addAmount5(){
        totalAmount += 5;
        setTotalAmountLabel();
    }

    public void addAmount10(){
        totalAmount += 10;
        setTotalAmountLabel();
    }

    public void addAmount50(){
        totalAmount += 50;
        setTotalAmountLabel();
    }

    public void addAmount100(){
        totalAmount += 100;
        setTotalAmountLabel();
    }

    public void addAmountCustom(ActionEvent actionEvent){
        if(isNumerical(saveAmountTextField.getText())){
            double customAmount = Double.parseDouble(saveAmountTextField.getText());
            setTotalAmount(customAmount);
        }
        else
        {
            setErrorLabel("Amount must be numerical.");
        }
    }

    public void proceed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../view/client/cashOperation/CashOperationConfirmation.fxml"));
        root = loader.load();
        CashOperationConfirmationController cashOperationConfirmationController = loader.getController();
        cashOperationConfirmationController.setCashOperationType(cashOperationType);
        cashOperationConfirmationController.setBalanceType(balanceType);
        cashOperationConfirmationController.setTotalAmount(totalAmount);
        setStage(actionEvent);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../view/client/cashOperation/CashOperationBalanceType.fxml"));
        root = loader.load();
        CashOperationBalanceTypeController cashOperationBalanceTypeController = loader.getController();
        cashOperationBalanceTypeController.setCashOperationType(cashOperationType);
        setStage(actionEvent);
    }

    public void setCashOperationType(CashOperationType cashOperationType){
        this.cashOperationType = cashOperationType;
        setInfoLabel();
    }

    private void setInfoLabel(){
        String message = "Please select the amount that you would like to ";
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

    public void setBalanceType(BalanceType balanceType){
        this.balanceType = balanceType;
        setBalanceTypeLabel();
    }

    public void setBalanceTypeLabel(){
        if(balanceType == BalanceType.EURO || balanceType == BalanceType.RON)
        {
            balanceTypeLabel.setText(String.valueOf(balanceType));
        }
        else
        {
            throw new IllegalStateException();
        }
    }

    public void setTotalAmount(double totalAmount){
        this.totalAmount = totalAmount;
        setTotalAmountLabel();
    }

    public void setTotalAmountLabel(){
        totalAmountLabel.setText(String.valueOf(totalAmount));
    }

    public void setErrorLabel(String message){
        errorLabel.setText(message);
    }

    private boolean isNumerical(String text){
        for(int i = 0; i < text.length(); i++){
            if(!Character.isDigit(text.charAt(i))){
                return false;
            }
        }
        return true;
    }

    private void setStage(ActionEvent actionEvent){
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
