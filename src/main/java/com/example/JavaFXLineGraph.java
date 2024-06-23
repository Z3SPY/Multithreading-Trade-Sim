package com.example;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import py4j.GatewayServer;

public class JavaFXLineGraph extends Application {
    //private List<XYChart.Series<Number, Number>> seriesList;
    private SeriesManager seriesManager = SeriesManager.getInstance();


    private int timeCounter = 0;
    private NumberAxis xAxis;
    private ScrollPane scrollPane;
    private LineChart<Number, Number> lineChart;

    public static void main(String[] args) {
        GatewayServer server = new GatewayServer(new JavaFXLineGraph());
        server.start();
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Stock Price");
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Stock Monitoring");

        // Initialize series names and add them to seriesList
        seriesManager.getSeriesList().add(createSeries("Space Rocks"));
        seriesManager.getSeriesList().add(createSeries("Hyper Accelerators"));
        seriesManager.getSeriesList().add(createSeries("Tiki Torches"));
        seriesManager.getSeriesList().add(createSeries("Space Worm Jelly"));
        seriesManager.getSeriesList().add(createSeries("Stone Pick Axe"));

        // Initially display all series on the chart
        lineChart.getData().add(seriesManager.getSeriesList().get(0));
        lineChart.getStylesheets().add(this.getClass().getResource("chart.css").toExternalForm());

        scrollPane = new ScrollPane();
        scrollPane.setContent(lineChart);
        scrollPane.setPannable(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Add buttons for switching stocks
        List<Button> stockButtons = new ArrayList<>();
        for (int i = 0; i < seriesManager.getSeriesList().size(); i++) {
            Button stockButton = new Button(seriesManager.getSeriesList().get(i).getName());
            int index = i; // To capture in lambda
            stockButton.setOnAction(e -> switchStock(index));
            stockButtons.add(stockButton);
        }

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(stockButtons);

        VBox root = new VBox(scrollPane, buttonBox);
        Scene scene = new Scene(root, 800.0, 600.0);
        stage.setTitle("Stock Price Line Chart");
        stage.setScene(scene);
        stage.show();
    }

    public void updateStockValue(String jsonData) {
        Platform.runLater(() -> {

            // Ensure seriesList is not null
            List<XYChart.Series<Number, Number>> seriesList = seriesManager.getSeriesList();

            System.out.print(seriesList);

            Gson gson = new Gson();
            List<DataObject> data = gson.fromJson(jsonData, new TypeToken<List<DataObject>>(){}.getType());
            
        
            


            // Update the corresponding series with new data
            timeCounter++;
            for (int i = 0; i < data.size() && i < seriesList.size(); i++) {
                double stockPrice = data.get(i).getStockPrice();
                XYChart.Series<Number, Number> series = seriesList.get(i);
                series.getData().add(new XYChart.Data<>(timeCounter, stockPrice));
                

                // Style dataPoint based on price fluctuation
                XYChart.Data<Number, Number> dataPoint = series.getData().get(series.getData().size() - 1);
                
                System.out.println(dataPoint);
                if (series.getData().size() > 1) {
                    double lastPrice = series.getData().get(series.getData().size() - 2).getYValue().doubleValue();
                    if (dataPoint.getNode() != null) {
                        if (stockPrice > lastPrice) {
                            dataPoint.getNode().setStyle("-fx-background-color: green;");
                        } else {
                            dataPoint.getNode().setStyle("-fx-background-color: red;");
                        }
                    }
                    
                }

            }

            
        });

    }

    private XYChart.Series<Number, Number> createSeries(String name) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);
        return series;
    }

    private void switchStock(int newIndex) {
            // Clear the currently displayed series
            System.out.print(seriesManager.getSeriesList());

            lineChart.getData().clear();
            //System.out.print(seriesList.get(newIndex)); // Check Series List
            // Add the selected series back to the chart
            lineChart.getData().add(seriesManager.getSeriesList().get(newIndex));



            

            ObservableList<XYChart.Data<Number, Number>> data = seriesManager.getSeriesList().get(newIndex).getData();

            for (int i = 0; i < data.size(); i++) {
                XYChart.Data<Number, Number> dataPoint = data.get(i);
                double curPrice = dataPoint.getYValue().doubleValue();
                
                if (i != 0) {
                    double lastPrice = data.get(i - 1).getYValue().doubleValue();
                    String style = (curPrice < lastPrice) ? "-fx-background-color: red;" : "-fx-background-color: green;";
                    dataPoint.getNode().setStyle(style);
                } else {
                    dataPoint.getNode().setStyle("-fx-background-color: black;");
                }
            }

    }
}
