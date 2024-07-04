package com.example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FastClock {
    private Text timeText;
    private Text dateText;
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

        dateText = new Text("Aprimay 1, 4122"); // initial date
        dateText.setFill(Color.WHITE);
        dateText.setFont(Font.font("Arial", 24));

        datePane.getChildren().add(dateText);
        return datePane;
    }

    private void startClock() {
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(.1), e -> {
            clockMinutes = clockMinutes+30;
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
            System.out.println(dateText.getText());
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