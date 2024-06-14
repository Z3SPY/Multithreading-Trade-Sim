package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the main page content
        Button mainButton = new Button("Main Page Content");

        // Create the navigation page content
        Button navButton1 = new Button("Nav Button 1");
        Button navButton2 = new Button("Nav Button 2");
        Button navButton3 = new Button("Nav Button 3");

        // Create a VBox to hold the navigation buttons vertically
        VBox navButtons = new VBox(navButton1, navButton2, navButton3);

        // Create a BorderPane to hold both the main content and the navigation content
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(mainButton); // Place the main content in the center
        borderPane.setRight(navButtons); // Place the navigation content on the right

        // Create the scene and set it on the stage
        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.setTitle("JavaFX Main and Nav Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
