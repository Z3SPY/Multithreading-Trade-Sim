package com.example;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import py4j.GatewayServer;

public class JavaFXLineGraph extends Application {

    private static XYChart.Series<Number, Number> series;
    private static int timeCounter = 0;
    private static NumberAxis xAxis;
    private static ScrollPane scrollPane;

    public static void main(String[] args) {
        GatewayServer server = new GatewayServer(new JavaFXLineGraph());
        server.start();
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Stock Price Line Chart");

        xAxis = new NumberAxis(0, 5, 1);
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Stock Price");

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Stock Monitoring");

        series = new XYChart.Series<>();
        series.setName("Stock Prices");

        lineChart.getData().add(series);
        lineChart.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());

        scrollPane = new ScrollPane();
        scrollPane.setContent(lineChart);
        scrollPane.setPannable(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        StackPane root = new StackPane(scrollPane);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void updateStockValue(String stockName, double stockPrice) {
        Platform.runLater(() -> {
            series.getData().add(new XYChart.Data<>(timeCounter++, stockPrice));
            xAxis.setLowerBound(Math.max(0, timeCounter - 5));
            xAxis.setUpperBound(timeCounter);

        });
    }
}