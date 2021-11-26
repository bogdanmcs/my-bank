package com.sw.banca.controller.fisc;

import com.sw.banca.misc.enums.FiscClientsView;
import com.sw.banca.misc.fisc.MetDecoder;
import com.sw.banca.model.client.Client;
import com.sw.banca.model.fisc.Fisc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FiscMenuController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ListView<String> clientsListView;
    @FXML
    private Button viewAllClientsButton;
    @FXML
    private Button viewTrackedClientsButton;
    @FXML
    private Button viewUntrackedClientsButton;

    private final HashMap<String, Client> clientsListMap = new HashMap<>();
    private List<Client> clientsList;
    private FiscClientsView fiscClientsView;

    public void setClientsList(List<Client> clientList, FiscClientsView fiscClientsView) {
        this.clientsList = clientList;
        setViewClientsButtons(fiscClientsView);
        setClientsListView();
    }

    private void setClientsListView(){
        clientsListView.getItems().setAll(getClientsInfo());
    }

    private List<String> getClientsInfo() {
        List<String> clientsListInfo = new ArrayList<>();
        for (Client c: clientsList) {
            String message = "Client " + c.getInfo();
            clientsListInfo.add(message);
            clientsListMap.put(message, c);
        }
        return clientsListInfo;
    }

    public void setFiscClientsView(FiscClientsView fiscClientsView) {
        if (fiscClientsView == FiscClientsView.ALL) {
            viewAllClients();
        } else if (fiscClientsView == FiscClientsView.TRACKED) {
            viewTrackedClients();
        } else {
            viewUntrackedClients();
        }
    }

    public void viewAllClients() {
        List<Client> clientsList = Fisc.getInstance().getClientsList();
        setClientsList(clientsList, FiscClientsView.ALL);
    }

    public void viewTrackedClients() {
        List<Client> clientsList = Fisc.getInstance().getClientsList();
        List<Client> trackedClientsList = clientsList.stream()
                .filter(Client::isTracked)
                .collect(Collectors.toList());
        setClientsList(trackedClientsList, FiscClientsView.TRACKED);
    }

    public void viewUntrackedClients() {
        List<Client> clientsList = Fisc.getInstance().getClientsList();
        List<Client> untrackedClientsList = clientsList.stream()
                .filter(client -> !client.isTracked())
                .collect(Collectors.toList());
        setClientsList(untrackedClientsList, FiscClientsView.UNTRACKED);
    }

    private void setViewClientsButtons(FiscClientsView fiscClientsView) {
        this.fiscClientsView = fiscClientsView;
        if(fiscClientsView == FiscClientsView.ALL) {
            viewAllClientsButton.setStyle("-fx-background-color: yellowgreen");
            viewTrackedClientsButton.setStyle("-fx-background-color: none");
            viewUntrackedClientsButton.setStyle("-fx-background-color: none");
        } else if (fiscClientsView == FiscClientsView.TRACKED) {
            viewAllClientsButton.setStyle("-fx-background-color: none");
            viewTrackedClientsButton.setStyle("-fx-background-color: yellowgreen");
            viewUntrackedClientsButton.setStyle("-fx-background-color: none");
        } else {
            viewAllClientsButton.setStyle("-fx-background-color: none");
            viewTrackedClientsButton.setStyle("-fx-background-color: none");
            viewUntrackedClientsButton.setStyle("-fx-background-color: yellowgreen");
        }
    }

    public void selectClient(MouseEvent mouseEvent) throws IOException {
        Client client = getSelectedClient(mouseEvent.getTarget().toString());
        if (!mouseEvent.getTarget().toString().endsWith("'null'") && client != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/fisc/FiscClientStatus.fxml"));
            root = loader.load();
            FiscClientStatusController fiscClientStatusController = loader.getController();
            fiscClientStatusController.setClientInfo(client, fiscClientsView);
            setStage(mouseEvent);
        }
    }

    private Client getSelectedClient(String mouseEventTarget) {
        MetDecoder metDecoder = new MetDecoder(mouseEventTarget);
        String clientKey = metDecoder.getTextAttribute();
        if (clientKey.equals("UNEXPECTED_ERROR")) {
            return null;
        }
        return clientsListMap.get(clientKey);
    }

    public void terminateSession(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../../view/auth/Login.fxml"));
        setStage(actionEvent);
    }

    private void setStage(ActionEvent actionEvent) {
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setStage(MouseEvent mouseEvent) {
        stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
