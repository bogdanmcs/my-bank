package com.sw.banca.controller.client.balanceChecker;

import com.sw.banca.controller.client.ClientMenuController;
import com.sw.banca.misc.client.AccountBalance;
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

public class BalanceCheckerController {
    @FXML
    private Label balanceEuroLabel;
    @FXML
    private Label balanceRonLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setBalanceLabels(AccountBalance accountBalance){
        balanceEuroLabel.setText(String.valueOf(accountBalance.getBalanceEuro()));
        balanceRonLabel.setText(String.valueOf(accountBalance.getBalanceRon()));
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../view/client/ClientMenu.fxml"));
        root = loader.load();
        ClientMenuController clientMenuController = loader.getController();
        clientMenuController.setHelloLabel(String.valueOf(UserSession.getInstance().getCnp()));
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
