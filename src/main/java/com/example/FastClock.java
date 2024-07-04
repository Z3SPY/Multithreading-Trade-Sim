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
    private int clockSeconds = 0; // added a variable to keep track of clock seconds
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
        clockarea.setMinSize(25, 25);
        clockarea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        timeText = new Text("00:00");
        timeText.setFill(Color.WHITE);
        timeText.setFont(Font.font("Arial", 24));

        dateText = new Text("Aprimay 1, 4122"); // initial date
        dateText.setFill(Color.WHITE);
        dateText.setFont(Font.font("Arial", 24));

        // add clockarea, timeText, and dateText to the scene graph
        // ...
    }

    public StackPane getTimePane() {
        StackPane timePane = new StackPane();
        timePane.setMinSize(25, 25);
        timePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        timeText = new Text("00:00");
        timeText.setFill(Color.web("#DC5F00"));
        timeText.setFont(Font.font("Arial", 35));
        

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
            // clockSeconds = (clockSeconds + 10) % 300; // increment clock seconds by 10, and reset to 0 after 5 minutes
            clockMinutes = clockMinutes+10;
            if (clockMinutes == 60){
                clockHours =clockHours +1;
                clockMinutes=0;
            }
            if (clockHours==24){
                day= day+1;
                clockHours=0;
            }

            timeText.setText(String.format("%02d:%02d:%02d", clockHours, clockMinutes, clockSeconds));

            // update date
            // day = (day % 15) + 1; // increment day, and reset to 1 after 15 days
            // if (day == 1) {
            //     monthIndex = (monthIndex + 1) % 4; // increment month, and reset to 0 after 4 months
            //     if (monthIndex == 0) {
            //         year++; // increment year after 4 months
            //     }
            // }
            dateText.setText(months[monthIndex] + " " + day + ", " + year);
            System.out.println(dateText);
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