package com.sw.banca.controller.fisc;

import com.sw.banca.misc.enums.ServerResponse;
import com.sw.banca.model.Bank;
import com.sw.banca.model.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class FiscClientStatusController {
    @FXML
    private Label clientIdLabel;
    @FXML
    private Label clientEuroBalanceLabel;
    @FXML
    private Label clientRonBalanceLabel;
    @FXML
    private Label clientStatusLabel;
    @FXML
    private Button clientStatusButton;
    @FXML
    private TextArea clientOperationsLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Client currentClient;

    public void setClientInfo(Client client){
        currentClient = client;
        setClientIdLabel(String.valueOf(client.getCnp()));
        setClientEuroBalanceLabel(String.valueOf(client.getEuroBalance()));
        setClientRonBalanceLabel(String.valueOf(client.getRonBalance()));
        ServerResponse serverResponse = Bank.getInstance().isClientTracked(client);
        if(serverResponse == ServerResponse.CLIENT_ALREADY_TRACKED) {
            setClientStatusLabel("Client is currently tracked.", Color.GREEN);
            setClientStatusButton("Untrack");
        } else {
            setClientStatusLabel("Client is currently not tracked.", Color.RED);
            setClientStatusButton("Track");
        }
    }

    private void setClientIdLabel(String clientId) {
        clientIdLabel.setText(clientId);
    }

    private void setClientEuroBalanceLabel(String clientEuroBalance) {
        clientEuroBalanceLabel.setText(clientEuroBalance);
    }

    private void setClientRonBalanceLabel(String clientRonBalance) {
        clientRonBalanceLabel.setText(clientRonBalance);
    }

    public void setClientStatus(ActionEvent actionEvent) {
        ServerResponse serverResponse = Bank.getInstance().isClientTracked(currentClient);
        if(serverResponse == ServerResponse.CLIENT_NOT_TRACKED) {
            Bank.getInstance().startClientTracking(currentClient);
            setClientStatusLabel("Client is currently tracked.", Color.GREEN);
            setClientStatusButton("Untrack");
        } else {
            Bank.getInstance().stopClientTracking(currentClient);
            setClientStatusLabel("Client is currently not tracked.", Color.RED);
            setClientStatusButton("Track");
        }
    }

    private void setClientStatusLabel(String clientStatus, Color color) {
        clientStatusLabel.setText(clientStatus);
        clientStatusLabel.setTextFill(color);
    }

    private void setClientStatusButton(String clientStatus) {
        clientStatusButton.setText(clientStatus);
    }


    public void setClientOperationsLabel(String clientOperation) {

    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("../../view/fisc/FiscMenu.fxml"));
        root = loader.load();
        FiscMenuController fiscMenuController = loader.getController();
        List<Client> clientsList = Bank.getInstance().getClientsList();
        fiscMenuController.setClientsList(clientsList);
        setStage(actionEvent);
    }

    private void setStage(ActionEvent actionEvent){
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
