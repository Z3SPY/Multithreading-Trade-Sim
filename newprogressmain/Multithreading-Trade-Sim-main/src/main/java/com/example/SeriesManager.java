package com.example;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.XYChart;

public class SeriesManager {
    private static SeriesManager instance;
    private List<XYChart.Series<Number, Number>> seriesList;

    private SeriesManager() {
        seriesList = new ArrayList<>();
        // Initialize seriesList here if needed
    }

    public static SeriesManager getInstance() {
        if (instance == null) {
            instance = new SeriesManager();
        }
        return instance;
    }

    public List<XYChart.Series<Number, Number>> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<XYChart.Series<Number, Number>> seriesList) {
        this.seriesList = seriesList;
    }

    // Other methods to manipulate seriesList if needed
}