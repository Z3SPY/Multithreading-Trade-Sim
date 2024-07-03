package com.example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FastClock extends StackPane {
    private Circle clockFace;
    private Text timeText;
    private Timeline timeline;
    private int clockSeconds = 0; // added a variable to keep track of clock seconds

    public FastClock() {
        initClock();
        startClock();
    }

    private void initClock() {
        StackPane clockarea = new StackPane();
        clockarea.setMinSize(25, 25);
        clockarea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        timeText = new Text("00:00");
        timeText.setStyle("-fx-text-fill: #FFFFFF;");
        // timeText.setFill(Color.WHITE);
        timeText.setFont(Font.font("Arial", 24)); // added closing parenthesis

        getChildren().addAll(clockarea, timeText);
    }

    private void startClock() {
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(10), e -> { // changed duration to 10 seconds
            clockSeconds = (clockSeconds + 10) % 300; // increment clock seconds by 10, and reset to 0 after 5 minutes
            int minutes = clockSeconds / 60;
            int seconds = clockSeconds % 60;

            timeText.setText(String.format("%02d:%02d", minutes, seconds));
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
}