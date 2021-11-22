package com.sw.banca.controller;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class UserMenuController {
    @FXML
    private Label helloLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void withdrawCash(ActionEvent actionEvent){

    }

    public void depositCash(ActionEvent actionEvent){

    }

    public void inquireBalance(ActionEvent actionEvent){

    }

    public void clearAccounts(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bank");
        alert.setHeaderText("Clearance of accounts");
        alert.setContentText("Are you sure you want to delete your EURO and RON accounts?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Bank.deleteClient(UserSession.getInstance().getCnp(), UserSession.getInstance().getPin());
            terminateSession(actionEvent);
        }
    }

    public void terminateSession(ActionEvent actionEvent) throws IOException {
        UserSession.getInstance().clearSession();
        root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setHelloLabel(String id){
        helloLabel.setText("Hello, " + id);
    }
}
