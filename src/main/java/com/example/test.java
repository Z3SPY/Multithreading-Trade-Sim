package com.example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class test extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a Planet object
        Planet planet = new Planet(100);
        planet.setPos(300, 100);

        // Setup the scene
        Group root = new Group(planet);
        Scene scene = new Scene(root, 600, 400, true);
        scene.setFill(Color.BLACK);
        scene.setCamera(new PerspectiveCamera());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Rotating Pyramid");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
