package com.example;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class register {
    public void show(Stage stage) {
        // Create and style the grid pane
        GridPane regGrid = new GridPane();
        regGrid.setAlignment(Pos.CENTER);
        regGrid.setVgap(10);
        regGrid.setHgap(10);
        regGrid.setStyle("-fx-background-color: #1E1E1E;");
        regGrid.setPrefWidth(300);


        // Create username and password labels and fields
        Label registerLabel = new Label("COMPANY REGISTRATION");
        registerLabel.getStyleClass().add("reglbl-text"); // Apply CSS class for styling
        registerLabel.setTranslateX(15);
        registerLabel.setTranslateY(-120);


        TextField usernameField = new TextField();
        usernameField.setPromptText("username");
        usernameField.getStyleClass().add("userField"); // Apply CSS class for styling

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("password");
        passwordField.getStyleClass().add("passField"); // Apply CSS class for styling

        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("confirm password");
        confirmPassword.getStyleClass().add("passField"); 

        TextField companyIDField = new TextField();
        companyIDField.setPromptText("company ID");
        companyIDField.getStyleClass().add("userField"); // Apply CSS class for styling

        // Create the login button
        Button registerButton = new Button("SIGN UP");
        registerButton.getStyleClass().add("register-btn"); // Apply CSS class for styling
        
        
       

    

        // Add components to the grid pane
        regGrid.add(usernameField, 0, 1, 3, 1); // Span 2 columns
        regGrid.add(passwordField, 0, 2, 3, 1); // Span 2 columns
        regGrid.add(confirmPassword, 0, 3, 3, 1);   // Span 2 columns
        regGrid.add(companyIDField, 0, 4, 1, 1);   // Span 2 columns
        regGrid.add(registerButton, 1, 4,1, 1);   // Span 2 columns



    

       

        // Create the bottom register option
        HBox registerNav = new HBox();
        Button enterButton = new Button("1. Enter Credentials");
        Button logButton = new Button("2. Go Back To Login");
        enterButton.getStyleClass().add("lower-btn");
        logButton.getStyleClass().add("lower-btn");

        registerNav.getChildren().addAll( enterButton, logButton);
        registerNav.getStyleClass().add("bottomNav"); // Apply CSS class for styling

        // Create the welcome label
        Label welcomeLabel = new Label("WELCOME TO ISTO");
        welcomeLabel.getStyleClass().add("my-text"); // Apply CSS class for styling
        welcomeLabel.setTranslateY(-70);

        // Create a VBox to hold everything except the register option
        Group myGroup = new Group();
        myGroup.getChildren().addAll(regGrid, welcomeLabel, registerLabel);


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

        Scene scene = new Scene(borderPane, 750, 750);
        scene.getStylesheets().add(getClass().getResource("/com/example/styles.css").toExternalForm());
        scene.setFill(Color.web("1E1E1E", 1.0));
        stage.setTitle("ISTO SYSTEM");
        stage.setScene(scene);
        stage.show();

      
        
    }
}