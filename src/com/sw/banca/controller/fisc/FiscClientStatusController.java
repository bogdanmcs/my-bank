package com.sw.banca.controller.fisc;

import com.sw.banca.misc.enums.FiscClientsView;
import com.sw.banca.model.Bank;
import com.sw.banca.model.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    private FiscClientsView fiscClientsView;

    public void setClientInfo(Client client, FiscClientsView fiscClientsView) {
        currentClient = client;
        this.fiscClientsView = fiscClientsView;
        setLabels(client);
    }

    private void setLabels(Client client) {
        setClientIdLabel(String.valueOf(client.getCnp()));
        setClientEuroBalanceLabel(String.valueOf(client.getBalance("EURO")));
        setClientRonBalanceLabel(String.valueOf(client.getBalance("RON")));
        setClientStatusButtonAndLabel();
        setClientOperationsLabel(client.getOperationsList());
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

    private void setClientStatusButtonAndLabel() {
        if (currentClient.isTracked()) {
            setClientStatusAsTracked();
        } else {
            setClientStatusAsUntracked();
        }
    }

    private void setClientStatusAsTracked() {
        setClientStatusLabel("Client is currently tracked.", Color.GREEN);
        setClientStatusButton("Untrack");
    }

    private void  setClientStatusAsUntracked() {
        setClientStatusLabel("Client is currently not tracked.", Color.RED);
        setClientStatusButton("Track");
    }

    public void setClientStatus() {
        if (!currentClient.isTracked()) {
            Bank.getInstance().startClientTracking(currentClient);
            setClientStatusAsTracked();
        } else {
            confirmUntracking();
        }
    }

    private void confirmUntracking() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bank");
        alert.setHeaderText("Untrack client " + currentClient.getCnp());
        alert.setContentText("Are you sure you want to untrack this client? All history will be deleted.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Bank.getInstance().stopClientTracking(currentClient);
            setClientStatusAsUntracked();
            clientOperationsLabel.setText("");
        }
    }

    private void setClientStatusLabel(String clientStatus, Color color) {
        clientStatusLabel.setText(clientStatus);
        clientStatusLabel.setTextFill(color);
    }

    private void setClientStatusButton(String clientStatus) {
        clientStatusButton.setText(clientStatus);
    }


    public void setClientOperationsLabel(List<String> clientOperations) {
        if (!clientOperations.isEmpty()) {
            clientOperationsLabel.setText(clientOperations.toString());
        }
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/fisc/FiscMenu.fxml"));
        root = loader.load();
        FiscMenuController fiscMenuController = loader.getController();
        fiscMenuController.setFiscClientsView(fiscClientsView);
        setStage(actionEvent);
    }

    private void setStage(ActionEvent actionEvent) {
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}