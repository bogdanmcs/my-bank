package com.sw.banca.controller;

import com.sw.banca.misc.AccountBalance;
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

public class CheckBalanceController {
    @FXML
    private Label balanceEuroLabel;
    @FXML
    private Label balanceRonLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setBalanceLabels(AccountBalance accountBalance){
        balanceEuroLabel.setText(String.valueOf(accountBalance.getSoldContEuro()));
        balanceRonLabel.setText(String.valueOf(accountBalance.getSoldcontRon()));
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UserMenu.fxml"));
        root = loader.load();
        UserMenuController userMenuController = loader.getController();
        userMenuController.setHelloLabel(String.valueOf(UserSession.getInstance().getCnp()));
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
