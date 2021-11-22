package com.sw.banca;

import com.sw.banca.model.Bank;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        Scene scene = new Scene(root, 700, 700, Color.LIGHTBLUE);
        Image icon = new Image("res/bank.png");

        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Bank");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Bank.getInstance().initialize();
        launch(args);
    }
}
