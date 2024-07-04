package com.example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FastClock {
    private Text timeText;
    private Text dateText;
    private Label monthText = new Label("Apr");
    private Label dayText = new Label("7");
    private Label yearText = new Label("4122");
    private Timeline timeline;
    private int clockSeconds = 0; 
    private int clockMinutes = 0;
    private int clockHours = 0;
    private int day = 1; // added a variable to keep track of day
    private int year = 4122; // added a variable to keep track of year
    private String[] months = {"Aprimay", "Jugust", "Septober", "Decembary"}; // custom months
    private int monthIndex = 0; // current month index

    public FastClock() {
        initClock();
        startClock();
    }

    private void initClock() {
        StackPane clockarea = new StackPane();
        clockarea.setMinSize(60, 25);
        clockarea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        timeText = new Text("00:00");
        timeText.setFill(Color.WHITE);
        timeText.setFont(Font.font("Arial", 24));

        dateText = new Text("Aprimay 1, 4122");
        dateText.setFill(Color.WHITE);
        dateText.setFont(Font.font("Arial", 24));
    }

    public StackPane getTimePane() {
        StackPane timePane = new StackPane();
        timePane.setMinSize(25, 25);
        timePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        timeText = new Text("00:00");
        timeText.setFill(Color.web("#DC5F00"));
        timeText.setFont(Font.font("Arial", 35));
        timePane.setMinSize(25, 25);
        timePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        

        timePane.getChildren().add(timeText);
        return timePane;
    }

    public StackPane getDatePane() {
        StackPane datePane = new StackPane();
        datePane.setMinSize(25, 25);
        datePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Create a Month
        monthText.setText(getCurrentMonth());
        monthText.setStyle("-fx-text-fill: #DC5F00; -fx-font-size: 20;");

        // Create a Day
        dayText.setText(getCurrentDay());
        dayText.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 20;"); 

        // Create the middle section with a dark gray rectangle
        Region middleRect = new Region();
        middleRect.setMinSize(10, 3);
        middleRect.setMaxSize(10, 3);
        middleRect.setStyle("-fx-background-color: #555555;"); // Dark gray color
        // Create the middle section with a dark gray rectangle
        Region middleRect2 = new Region();
        middleRect2.setMinSize(10, 3);
        middleRect2.setMaxSize(10, 3);
        middleRect2.setStyle("-fx-background-color: #555555;"); // Dark gray color

        // Create a Year
        yearText.setText(getCurrentYear());
        yearText.setStyle("-fx-text-fill: #DC5F00; -fx-font-size: 20;"); // Orange color

        HBox bottomSection = new HBox(2); // Horizontal box with spacing
        bottomSection.getChildren().addAll(monthText, middleRect,dayText, middleRect2, yearText);
        bottomSection.setAlignment(Pos.CENTER);

        datePane.getChildren().addAll(bottomSection);
        return datePane;
    }

    private void startClock() {
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(10), e -> {
            clockMinutes = clockMinutes+5;
            if (clockMinutes == 60) {
                clockHours++;
                clockMinutes = 0;
                if (clockHours == 24) {
                    day++;
                    clockHours = 0;
                    if (day == 16) {
                        monthIndex++;
                        day = 1;
                        if (monthIndex == 4) {
                            year++;
                            monthIndex = 0;
                        }
                    }
                }
            }

            timeText.setText(String.format("%02d:%02d:%02d", clockHours, clockMinutes, clockSeconds));
            dateText.setText(months[monthIndex] + " " + day + ", " + year);
            monthText.setText(months[monthIndex]);
            dayText.setText(String.valueOf(day));
            yearText.setText(String.valueOf(year));
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public String getCurrentMonth() {
        return months[monthIndex];
    }

    public String getCurrentDay() {
        return String.valueOf(day);
    }

    public String getCurrentYear() {
        return String.valueOf(year);
    }
}