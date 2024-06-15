package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class test extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create and style the grid pane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-background-color: #1E1E1E; -fx-padding: 20;");

        // Create username and password labels and fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("username");
        usernameField.getStyleClass().add("userField"); // Apply CSS class for styling

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("password");
        passwordField.getStyleClass().add("passField"); // Apply CSS class for styling

        // Create the login button
        Button loginButton = new Button("login");
        loginButton.setStyle("-fx-background-color: #FF6600; -fx-text-fill: #1E1E1E; -fx-font-weight: bold;");

        // Create the additional welcome message
        Label additionalMessage = new Label("Welcome To the Interdimensional Space Trade Organization Terminal. Lets Make Some Space! MONEY $$");
        additionalMessage.setFont(Font.font("Arial", 10));
        additionalMessage.setTextFill(Color.web("#FF6600"));

        // Create the bottom register option
        Label registerOption = new Label("1. enter  2. register");
        registerOption.setFont(Font.font("Arial", 12));
        registerOption.setTextFill(Color.web("#FF6600"));
        registerOption.setAlignment(Pos.CENTER);

        // Create the welcome label
        Label welcomeLabel = new Label("WELCOME TO ISTO");
        welcomeLabel.getStyleClass().add("my-text"); // Apply CSS class for styling

        // Add components to the grid pane
        grid.add(usernameField, 0, 1, 2, 1);
        grid.add(passwordField, 0, 2, 2, 1);
        grid.add(loginButton, 0, 3, 2, 1);

        // Create a VBox to hold everything except the register option
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #1E1E1E; -fx-padding: 20;");
        borderPane.setCenter(grid);
        borderPane.setTop(welcomeLabel);
        borderPane.setBottom(registerOption);

        // Set up the scene and stage
        Scene scene = new Scene(borderPane, 750, 750);
        scene.getStylesheets().add(getClass().getResource("/com/example/styles.css").toExternalForm());
        scene.setFill(Color.web("1E1E1E", 1.0));
        primaryStage.setTitle("ISTO SYSTEM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
