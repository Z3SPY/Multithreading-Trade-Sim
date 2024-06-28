package com.example;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

        // Create the register button
        Button registerButton = new Button("SIGN UP");
        registerButton.getStyleClass().add("register-btn"); // Apply CSS class for styling

        // Handle register button action
        registerButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPasswordText = confirmPassword.getText();
            String companyId = companyIDField.getText();

            if (!password.equals(confirmPasswordText)) {
                showAlert(AlertType.ERROR, "Registration Failed", "Passwords do not match.");
                return;
            }

            if (!isValidPassword(password)) {
                showAlert(AlertType.ERROR, "Registration Failed", "Password must be at least 8 characters long and contain both letters and numbers.");
                return;
            }

            MongoDBUtil mongoDBUtil = new MongoDBUtil();
            boolean userAdded = mongoDBUtil.addUser(username, password, companyId);
            mongoDBUtil.close();

            if (userAdded) {
                showAlert(AlertType.INFORMATION, "Registration Successful", "User registered successfully.");
                login loginPage = new login();
                loginPage.start(stage);
            } else {
                showAlert(AlertType.ERROR, "Registration Failed", "Username or Company ID already exists.");
            }
        });

        // Add components to the grid pane
        regGrid.add(usernameField, 0, 1, 3, 1); // Span 3 columns
        regGrid.add(passwordField, 0, 2, 3, 1); // Span 3 columns
        regGrid.add(confirmPassword, 0, 3, 3, 1);   // Span 3 columns
        regGrid.add(companyIDField, 0, 4, 1, 1);   // Span 1 column
        regGrid.add(registerButton, 1, 4, 1, 1);   // Span 1 column

        // Create the bottom register option
        HBox registerNav = new HBox();
        Button enterButton = new Button("1. Enter Credentials");
        Button logButton = new Button("2. Go Back To Login");
        enterButton.getStyleClass().add("lower-btn");
        logButton.getStyleClass().add("lower-btn");

        logButton.setOnAction(event -> {
            login loginPage = new login();
            loginPage.start(stage);
        });

        registerNav.getChildren().addAll(enterButton, logButton);
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

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
            if (hasLetter && hasDigit) {
                return true;
            }
        }
        return false;
    }
}