package com.sw.banca.controller;

import com.sw.banca.misc.Cnp;
import com.sw.banca.misc.Pin;
import com.sw.banca.model.Bank;
import com.sw.banca.model.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField cnpTextField;
    @FXML
    private TextField pinTextField;
    @FXML
    private Label statusLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void login(ActionEvent actionEvent) throws IOException {
        Cnp cnp = new Cnp(cnpTextField.getText());
        Pin pin = new Pin(pinTextField.getText());

        if(areValid(cnp, pin))
        {
            Client client = new Client(Integer.parseInt(cnp.getCnpCode()), Integer.parseInt(pin.getPinCode()));

            if(areCredentialsCorrect(client))
            {
                System.out.println("Log: client " + client.getCnp() + " has successfully logged in");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UserMenu.fxml"));
                root = loader.load();
                //

                //
                stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    private boolean areValid(Cnp cnp, Pin pin){
        if(cnp.isValid() && pin.isValid()){
            return true;
        }
        if(!cnp.isValid()){
            setStatusLabel("CNP is not valid. Format is XXX, numbers only.", Color.RED);
        }
        else
        {
            setStatusLabel("PIN is not valid. Format is XXXX, numbers only.", Color.RED);
        }
        return false;
    }

    private boolean areCredentialsCorrect(Client client){
        if(Bank.isRegistered(client)){
            return true;
        }
        else
        {
            setStatusLabel("Invalid credentials. Please try again.", Color.RED);
            return false;
        }
    }

    public void register(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Register.fxml"));
        root = loader.load();
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setStatusLabel(String message, Color color){
        statusLabel.setText(message);
        statusLabel.setTextFill(color);
    }
}
