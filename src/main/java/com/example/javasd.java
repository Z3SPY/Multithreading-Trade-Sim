package com.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import py4j.GatewayServer;

public class javasd extends Application {

    private Label stockLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stock Price Viewer");

        stockLabel = new Label("Initial Stock Price: $100.00");
        StackPane root = new StackPane();
        root.getChildren().add(stockLabel);
        primaryStage.setScene(new Scene(root, 300, 250));

        // Start Py4J GatewayServer
        GatewayServer gatewayServer = new GatewayServer(this);
        gatewayServer.start();
        System.out.println("Py4J GatewayServer started");

        primaryStage.show();
        
    }

    // Method to update stock price from Python via Py4J
    public void updateStockValue(String stockName, double stockPrice) {
        // This method is called from Python to update the UI
        Platform.runLater(() -> {
            stockLabel.setText(stockName + " current price: $" + String.format("%.2f", stockPrice));
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
