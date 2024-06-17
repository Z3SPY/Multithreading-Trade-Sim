package com.example;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class page {
    public void show(Stage stage) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #1E1E1E;"); // White background



        BorderPane pane = new BorderPane();    
        Group newGrp = new Group();
        newGrp.getChildren().addAll(pane, root);

        Scene scene = new Scene(newGrp, 750, 750);

        // Slide-in transition
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(1000), pane);
        slideIn.setFromX(0); // Start from the right edge
        slideIn.setToX(750);    // End at the center
        slideIn.play();

        stage.setScene(scene);
        stage.show();
    }
}