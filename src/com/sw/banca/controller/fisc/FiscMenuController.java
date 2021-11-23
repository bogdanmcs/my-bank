package com.sw.banca.controller.fisc;

import com.sw.banca.misc.fisc.MetDecoder;
import com.sw.banca.model.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FiscMenuController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ListView<String> clientsListView;

    private final HashMap<String, Client> clientsListMap = new HashMap<>();
    private List<Client> clientList;

    public void setClientsList(List<Client> clientList) {
        this.clientList = clientList;
        setClientsListView();
    }

    private List<String> getClientsInfo(){
        List<String> clientsListInfo = new ArrayList<>();
        for(Client c: clientList) {
            String message = "Client " + c.getInfo();
            clientsListInfo.add(message);
            clientsListMap.put(message, c);
        }
        return clientsListInfo;
    }

    private void setClientsListView(){
        clientsListView.getItems().addAll(getClientsInfo());
    }

    public void selectClient(MouseEvent mouseEvent) throws IOException {
        Client client = getSelectedClient(mouseEvent.getTarget().toString());
        if(!mouseEvent.getTarget().toString().endsWith("'null'") && client != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/fisc/FiscClientStatus.fxml"));
            root = loader.load();
            FiscClientStatusController fiscClientStatusController = loader.getController();
            fiscClientStatusController.setClientInfo(client);
            setStage(mouseEvent);
        }
    }

    private Client getSelectedClient(String mouseEventTarget) {
        MetDecoder metDecoder = new MetDecoder(mouseEventTarget);
        String clientKey = metDecoder.getTextAttribute();
        if(clientKey.equals("UNEXPECTED_ERROR")){
            return null;
        }
        return clientsListMap.get(clientKey);
    }

    public void terminateSession(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../../view/auth/Login.fxml"));
        setStage(actionEvent);
    }

    private void setStage(ActionEvent actionEvent){
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setStage(MouseEvent mouseEvent){
        stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
