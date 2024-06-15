package com.example;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App2 extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Screen screen = Screen.getPrimary();

        Rectangle2D bounds = screen.getVisualBounds();


        /* SPACINGS AND SIZING */
        double width = bounds.getWidth() * .4;
        double height =  bounds.getHeight() * .6;

        /* RECTANGLE AND TITLE SIZING */
        double titleBgX = width * .4; 
        double titleBgY = 45;
        double titleBGXpos = width * .30;



        /* SPACINGS AND SIZING END */
        
        /* STACK PANE FOR CENTERING */
        StackPane stackPane = new StackPane();
            stackPane.setPrefSize(width, height *.5); // Set preferred size for StackPane
            stackPane.setStyle("-fx-background-color: #1E1E1E"); // Set scene background color

        Rectangle backgroundRect = new Rectangle(titleBgX, titleBgY);
            backgroundRect.setFill(Color.web("#DC5F00"));

        // Create a text node
        Text text = new Text("WELCOME TO ISTO");
            text.getStyleClass().add("my-text"); // Apply CSS class for styling
            // Add nodes to StackPane
            stackPane.getChildren().addAll(backgroundRect, text);
            stackPane.setAlignment(Pos.CENTER);


        /* MAIN GROUP */
        Group grp = new Group();
        Rectangle border = new Rectangle(width * .97, height * .97);
            border.setTranslateY(height * .015);
            border.setTranslateX(width * .015);
            border.setFill(null);
            border.setStroke(Color.web("EEEEEE", 1.0));
            border.setStrokeWidth(2);


        /* FINISHING TOUCHES */
        grp.getChildren().addAll(stackPane, border);
        scene = new Scene(grp, width, height);
        scene.setFill(Color.web("1E1E1E", 1.0));
        

        

        scene.getStylesheets().add(getClass().getResource("/com/example/styles.css").toExternalForm());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}