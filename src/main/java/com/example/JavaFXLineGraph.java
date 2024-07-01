package com.example;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
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
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        StackPane root = createStackPane();
        Scene scene = new Scene(root, 800.0, 600.0);
        scene.getStylesheets().add(this.getClass().getResource("chart.css").toExternalForm());
        stage.setTitle("Stock Price Line Chart");
        stage.setScene(scene);
        stage.show();
    }

    public StackPane createStackPane() {
        /* DEFINED GRID VIEW  */
        GridPane stockGridPane = new GridPane();

        RowConstraints row0 = new RowConstraints();
        row0.setPercentHeight(30);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(70);

        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(100);

        stockGridPane.getColumnConstraints().add(col0);
        stockGridPane.getRowConstraints().addAll(row0, row1);

        /* DEFINED GRID VIEW  */

        /* DEFINE LINE CHART LAYOUTS */

        xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Stock Price");
        lineChart = new LineChart<>(xAxis, yAxis);
        //lineChart.setTitle("Stock Monitoring");

        // Initialize series names and add them to seriesList
        // Make this adapt to given python
        seriesManager.getSeriesList().add(createSeries("Space Rocks(SR) // INFRASTRUCUTURE"));
        seriesManager.getSeriesList().add(createSeries("Hyper Accelerators(HA) // MILITARY"));
        seriesManager.getSeriesList().add(createSeries("Tiki Torches(TkT) // COMMERCE"));
        seriesManager.getSeriesList().add(createSeries("Space Worm Jelly(SWJ) // COMMERCE"));
        seriesManager.getSeriesList().add(createSeries("Stone Pick Axe(SPA) // INFRASTRUCUTURE"));

        // Initially display all series on the chart
        lineChart.getData().add(seriesManager.getSeriesList().get(0));

        lineChart.setAnimated(false);

        scrollPane = new ScrollPane();
        scrollPane.setContent(lineChart);
        scrollPane.setPannable(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        /* DEFINE LINE CHART LAYOUTS */

        // Add buttons for switching stocks

        ListView<String> stockListView = new ListView<>();
        for (int i = 0; i < seriesManager.getSeriesList().size(); i++) {
            String stockName = seriesManager.getSeriesList().get(i).getName();
            stockListView.getItems().add(stockName);
        }

        stockListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = stockListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                switchStock(selectedIndex);
            }
        });

        stockGridPane.add(stockListView, 0, 0);
        stockGridPane.add(scrollPane, 0, 1);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(stockGridPane);

        return stackPane;
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


    //Handle Switch Stock
    private final ReadOnlyObjectWrapper<XYChart.Series<Number, Number>> stockCallBack = new ReadOnlyObjectWrapper<>();
    public ReadOnlyObjectProperty<XYChart.Series<Number, Number>> currentCustomerProperty() {
        return stockCallBack.getReadOnlyProperty();
    }

    public XYChart.Series<Number, Number> getCurrentCustomer() {

        return stockCallBack.get();
    }



    private void switchStock(int newIndex) {

        stockCallBack.set(seriesManager.getSeriesList().get(newIndex));



        // Clear the currently displayed series
        System.out.print(seriesManager.getSeriesList());

        lineChart.getData().clear();
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
