package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class mainpage extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setHgap(10); // Horizontal gap between columns
        grid.setVgap(10); // Vertical gap between rows
        grid.setStyle("-fx-background-color: #1E1E1E; -fx-padding: 20;");


        // Define column constraints with percentage widths
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20); // 20% width for column 1
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(20); // 20% width for column 2
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20); // 20% width for column 3
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(20); // 20% width for column 4
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(20); // 20% width for column 5

        grid.getColumnConstraints().addAll(col1, col2, col3, col4, col5);

        // Define row constraints with percentage heights
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(8); // 8% height for row 1
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(8); // 8% height for row 2
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(10); // 8% height for row 3
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(8); // 8% height for row 4
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(60); // 60% height for row 5
        RowConstraints row6 = new RowConstraints();
        row6.setPercentHeight(8); // 8% height for row 6

        grid.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6);

        // Adding elements to the grid

        // Top section
        StackPane topLeft = new StackPane();
        topLeft.getStyleClass().add("stackpane-top-left");
        grid.add(topLeft, 0, 0, 2, 2);

        StackPane topRight = new StackPane();
        topRight.getStyleClass().add("stackpane-top-right");
        grid.add(topRight, 2, 0, 3, 2);

        // Middle section
        HBox middleLeft = new HBox();
        middleLeft.getStyleClass().add("hbox-middle-left");
        grid.add(middleLeft, 0, 2, 1, 1);

        Label timeLabel = new Label("23:00:00");
        timeLabel.getStyleClass().add("label-orange");
        StackPane timePane = new StackPane(timeLabel);
        timePane.getStyleClass().add("stackpane-time");
        grid.add(timePane, 1, 2, 1, 1);

        Label titleLabel = new Label("WELCOME TO ISTO");
        titleLabel.getStyleClass().add("stackpane-title");
        StackPane middleRight = new StackPane(titleLabel);
        middleRight.getStyleClass().add("stackpane-title"); // Apply CSS class for styling
        grid.add(middleRight, 2, 2, 2, 1);

        StackPane shkBox = new StackPane();
        Label shkLabel = new Label("25.00 SHK");
        shkLabel.getStyleClass().add("label-orange");
        shkBox.getChildren().add(shkLabel);
        shkBox.getStyleClass().add("hbox-shk-box");
        grid.add(shkBox, 4, 2, 1, 1);

        // Bottom left section
        StackPane bottomLeft = new StackPane();
        bottomLeft.getStyleClass().add("stackpane-bottom-left");
        grid.add(bottomLeft, 0, 3, 4, 6);

        // Bottom right section
        GridPane bottomRightGrid = new GridPane();
        for (int i = 0; i < 5; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(20); // Each row in the bottom right grid gets 20% height
            bottomRightGrid.getRowConstraints().add(rc);
        }
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100); // Only one column that takes 100% width
        bottomRightGrid.getColumnConstraints().add(cc);

        for (int i = 0; i < 4; i++) {
            StackPane sp = new StackPane();
            sp.getStyleClass().add("stackpane-default");
            bottomRightGrid.add(sp, 0, i);
        }

        StackPane smileyPane = new StackPane();
        smileyPane.getStyleClass().add("stackpane-smiley");
        bottomRightGrid.add(smileyPane, 0, 4);

        grid.add(bottomRightGrid, 4, 3, 1, 3);

        // Set up the scene and stage
        Scene scene = new Scene(grid, 750, 750);
        scene.getStylesheets().add(getClass().getResource("/com/example/styles.css").toExternalForm());
        primaryStage.setTitle("ISTO SYSTEM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
