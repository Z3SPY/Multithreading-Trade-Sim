package com.example;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class test extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load the image
        Image image = new Image(new File("src\\main\\java\\com\\example\\space.gif").toURI().toString());
        ImageView imageView = new ImageView(image);

        // Create a Pane and add the ImageView to it
        Pane visPaneTop = new Pane();
        visPaneTop.getChildren().addAll(imageView);

        // Create a Scene with the Pane
        Scene scene = new Scene(visPaneTop, 800, 600);

        // Set the Scene to the Stage
        primaryStage.setTitle("Image Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
