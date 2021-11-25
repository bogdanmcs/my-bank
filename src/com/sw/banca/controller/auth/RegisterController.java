package com.sw.banca.controller.auth;

import com.sw.banca.misc.client.Cnp;
import com.sw.banca.misc.client.Pin;
import com.sw.banca.misc.enums.ServerResponse;
import com.sw.banca.model.Bank;
import com.sw.banca.model.client.Client;
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

public class RegisterController {
    @FXML
    private TextField cnpTextField;
    @FXML
    private TextField pinTextField;
    @FXML
    private Label statusLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void register(ActionEvent actionEvent) throws IOException {
        Cnp cnp = new Cnp(cnpTextField.getText());
        Pin pin = new Pin(pinTextField.getText());

        if (areCredentialsValid(cnp, pin)) {
            Client client = new Client(Integer.parseInt(cnp.getCnpCode()), Integer.parseInt(pin.getPinCode()));
            ServerResponse serverResponse = Bank.getInstance().createAccount(client);

            if (serverResponse == ServerResponse.CLIENT_ALREADY_EXISTS) {
                statusLabel.setText("Client is already registered.");
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/auth/Login.fxml"));
                root = loader.load();
                LoginController loginController = loader.getController();
                loginController.setStatusLabel("Registration was successful. You can now log in.", Color.GREEN);
                setStage(actionEvent);
            }
        }
    }

    private boolean areCredentialsValid(Cnp cnp, Pin pin) {
        if (cnp.isValid() && pin.isValid()) {
            return true;
        }
        if (!cnp.isValid()) {
            statusLabel.setText("CNP is not valid. Format is XXX, numbers only.");
        } else {
            statusLabel.setText("PIN is not valid. Format is XXXX, numbers only.");
        }
        return false;
    }

    public void login(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../../view/auth/Login.fxml"));
        setStage(actionEvent);
    }

    private void setStage(ActionEvent actionEvent) {
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
