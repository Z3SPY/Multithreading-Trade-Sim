package com.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import py4j.GatewayServer;

public class JavaApp extends Application {

 
    private Label label;

    // Method to update JavaFX UI with data from Python
    public void updateLabel(String data) {
        Platform.runLater(() -> {
            label.setText(data);
        });
    }


    @Override
    public void start(Stage primaryStage) {
        label = new Label("Data from Python will appear here");

        StackPane root = new StackPane();
        root.getChildren().add(label);

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.setTitle("JavaFX Application");
        primaryStage.show();

        // Start Py4J GatewayServer
        GatewayServer gatewayServer = new GatewayServer(this);
        gatewayServer.start();
        System.out.println("Py4J GatewayServer started");
    }

   

    public static void main(String[] args) {
        launch(args);
    }
}
