package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import py4j.GatewayServer;

public class mainSystemHandler extends Application {
    public static JavaFXLineGraph lineGraph;

    public static void main(String[] args) {
        GatewayServer gatewayServer = new GatewayServer(new mainSystemHandler());
        gatewayServer.start();       
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        lineGraph = new JavaFXLineGraph();
        StackPane stackPane = lineGraph.createStackPane();

        Scene scene = new Scene(stackPane, 800.0, 600.0);
        scene.getStylesheets().add(this.getClass().getResource("chart.css").toExternalForm());
        stage.setTitle("Stock Price Line Chart");
        stage.setScene(scene);
        stage.show();
    }


    public void updateStockPane(String JSONdata) {
        lineGraph.updateStockValue(JSONdata);
    }
}
