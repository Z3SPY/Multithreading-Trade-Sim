package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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
        passwordField.setPrefWidth(265);

        // Create the login button
        Button loginButton = new Button("LOGIN");
        loginButton.getStyleClass().add("login-btn"); // Apply CSS class for styling
        loginButton.setPrefWidth(265);

        // Create the additional welcome message
        Label additionalMessage = new Label("Welcome To the Interdimensional Space Trade Organization Terminal. Lets Make Some Space! MONEY $Y$");
        additionalMessage.setPrefWidth(265);
        additionalMessage.setTranslateY(25);
        additionalMessage.setWrapText(true);
        additionalMessage.setTextAlignment(TextAlignment.CENTER);
        additionalMessage.setTextFill(Color.web("#FF6600"));

        additionalMessage.getStyleClass().add("add-text"); // Apply CSS class for styling


        // Add components to the grid pane
        grid.add(usernameField, 0, 1, 2, 1); // Span 2 columns
        grid.add(passwordField, 0, 2, 2, 1); // Span 2 columns
        grid.add(loginButton, 0, 3, 2, 1);   // Span 2 columns
        grid.add(additionalMessage, 0, 4, 2, 1); // Span 2 columns

    

       

        // Create the bottom register option
        HBox registerNav = new HBox();
        Button enterButton = new Button("1. Enter");
        Button regButton = new Button("1. Register");
        enterButton.getStyleClass().add("lower-btn");
        regButton.getStyleClass().add("lower-btn");

        registerNav.getChildren().addAll(enterButton, regButton);
        registerNav.getStyleClass().add("bottomNav"); // Apply CSS class for styling

        // Create the welcome label
        Label welcomeLabel = new Label("WELCOME TO ISTO");
        welcomeLabel.getStyleClass().add("my-text"); // Apply CSS class for styling
        welcomeLabel.setTranslateY(-70);

        // Create a VBox to hold everything except the register option
        Group myGroup = new Group();
        myGroup.getChildren().addAll(grid, welcomeLabel);

        // Create the BorderPane and apply the border
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #1E1E1E; -fx-padding: 10;"); // Increased padding
        borderPane.setCenter(myGroup);
        borderPane.setBottom(registerNav);
        // Apply the border to the BorderPane
        borderPane.setBorder(new Border(new BorderStroke(
            Color.WHITE,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            new BorderWidths(2) // You can adjust the border width if needed
        )));

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
