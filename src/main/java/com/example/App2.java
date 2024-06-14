package com.example;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App2 extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        double width = 500;
        double height = 500;


        Group grp = new Group();

        Rectangle border = new Rectangle(width * .97, height * .97);
        border.setTranslateY(height * .015);
        border.setTranslateX(width * .015);
        border.setFill(null);
        border.setStroke(Color.web("EEEEEE", 1.0));
        border.setStrokeWidth(2);


        /* TITLE TEXT */
        double titleBgX = width * .5; 
        double titleBgY = 45;
        double titleBGXpos = width * .25;

        Group titleGroup = new Group();
        Rectangle titleBg = new Rectangle(titleBgX, titleBgY);
        titleBg.setFill(Color.web("DC5F00", 1.0));
        titleBg.setTranslateX(titleBGXpos);
        titleBg.setTranslateY(20);

        Text t = new Text(titleBGXpos + (titleBgX *.10) - 10, 50, "WELCOME TO ISTO");
        //t.setFont(new Font(25));
        t.getStyleClass().add("my-text"); // Apply CSS class
        //t.setFont(Font.font("Verdana", 25)); 
        titleGroup.getChildren().addAll( titleBg, t);
        
        

        grp.getChildren().addAll(border, titleGroup);
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