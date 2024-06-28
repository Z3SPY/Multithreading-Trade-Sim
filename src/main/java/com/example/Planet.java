package com.example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import java.util.Random;


public class Planet extends Group {

    private Pyramid pyramid;
    private Rotate rotateY;
    private Rotate constantRotateX;
    private Timeline timeline;
    private double layOutX = 0;
    private double layOutY = 0;

    public Planet(double size, double parentWidth, double parentHeight) {
        // Create the Pyramid
        pyramid = new Pyramid(size);
        pyramid.setLayoutX(layOutX);
        pyramid.setLayoutY(layOutY);

        // Add a constant rotation on the X-axis
        constantRotateX = new Rotate(30, Rotate.Z_AXIS);
        pyramid.getTransforms().add(constantRotateX);

        // Create a rotation animation on the Y-axis
        rotateY = new Rotate(0, pyramid.getBoundsInLocal().getCenterX(), pyramid.getBoundsInLocal().getCenterY(), pyramid.getBoundsInLocal().getCenterZ(), Rotate.Y_AXIS);
        pyramid.getTransforms().add(rotateY);

        timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(rotateY.angleProperty(), 0)),
            new KeyFrame(Duration.seconds(5), new KeyValue(rotateY.angleProperty(), 360))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // Add random stars

        
        // Add the pyramid to this group
        getChildren().add(pyramid);
    }

    

    public void setPos(double x, double y) {
        this.layOutX = x;
        this.layOutY = y;
        pyramid.setLayoutX(layOutX);
        pyramid.setLayoutY(layOutY);
    }

    public void setConstantRotationX(double angle) {
        constantRotateX.setAngle(angle);
    }

    public void setRotationY(double angle) {
        rotateY.setAngle(angle);
    }

    public void startAnimation() {
        timeline.play();
    }

    public void stopAnimation() {
        timeline.stop();
    }
}

class Pyramid extends Group {

    double size;

    public Pyramid(double size) {
        this.size = size;
        
        // Create the four faces of the pyramid
        // Upper
        Polygon face1 = createTriangle(size, Color.WHITE);
        Polygon face2 = createTriangle(size, Color.WHITE);
        Polygon face3 = createTriangle(size, Color.WHITE);
        Polygon face4 = createTriangle(size, Color.WHITE);


        Polygon face5 = createTriangle(size, Color.RED);
        Polygon face6 = createTriangle(size, Color.RED);
        Polygon face7 = createTriangle(size, Color.RED);
        Polygon face8 = createTriangle(size, Color.RED);

        Rectangle bottomFace = new Rectangle(size, size);
        bottomFace.setFill(Color.BLACK);       
        
        // Lower 

        // Position the faces to form a pyramid
        double halfSize = size / 2;

        // Face 1 (front)
        face1.setTranslateX(0);
        face1.setTranslateY(halfSize);
        face1.setTranslateZ(0);
        face1.setRotationAxis(Rotate.X_AXIS);
        face1.setRotate(0);
        Rotate rotateX1 = new Rotate(30, Rotate.X_AXIS);
        Rotate rotateY1 = new Rotate(0, Rotate.Y_AXIS);
        face1.getTransforms().addAll(rotateY1, rotateX1);

        // Face 2 (back)
        face2.setTranslateX(0);
        face2.setTranslateY(halfSize);
        face2.setTranslateZ(-size);
        Rotate rotateX2 = new Rotate(-30, Rotate.X_AXIS);
        Rotate rotateY2 = new Rotate(0, Rotate.Y_AXIS);
        face2.getTransforms().addAll(rotateY2, rotateX2);

        // Face 3 (right)
        face3.setTranslateX(halfSize + halfSize);
        face3.setTranslateY(halfSize);
        face3.setTranslateZ(-size);
        Rotate rotateX3 = new Rotate(-30, Rotate.X_AXIS);
        Rotate rotateY3 = new Rotate(-90, Rotate.Y_AXIS);
        face3.getTransforms().addAll(rotateY3, rotateX3);

        // Face 4 (left)
        face4.setTranslateX(-halfSize + halfSize);
        face4.setTranslateY(halfSize);
        face4.setTranslateZ(0);
        Rotate rotateX4 = new Rotate(-30, Rotate.X_AXIS);
        Rotate rotateY4 = new Rotate(90, Rotate.Y_AXIS);
        face4.getTransforms().addAll(rotateY4, rotateX4);




        // Face 5
        face5.setTranslateX(0);
        face5.setTranslateY(halfSize + 20);
        face5.setTranslateZ(0);
        face5.setRotationAxis(Rotate.X_AXIS);
        face5.setRotate(0);
        Rotate rotateX5 = new Rotate(150, Rotate.X_AXIS);
        Rotate rotateY5 = new Rotate(0, Rotate.Y_AXIS);
        face5.getTransforms().addAll(rotateX5, rotateY5);


        // Face 6
        face6.setTranslateX(0);
        face6.setTranslateY(halfSize + 20);
        face6.setTranslateZ(-size);
        Rotate rotateX6 = new Rotate(210, Rotate.X_AXIS);
        Rotate rotateY6 = new Rotate(0, Rotate.Y_AXIS);
        face6.getTransforms().addAll(rotateX6, rotateY6);

        // Face 7 (right)
        face7.setTranslateX(halfSize + halfSize);
        face7.setTranslateY(halfSize + 20);
        face7.setTranslateZ(-size);
        Rotate rotateX7 = new Rotate(210, Rotate.X_AXIS);
        Rotate rotateY7 = new Rotate(-90, Rotate.Y_AXIS);
        face7.getTransforms().addAll(rotateY7, rotateX7);

        // Face 8 (left)
        face8.setTranslateX(-halfSize + halfSize);
        face8.setTranslateY(halfSize + 20);
        face8.setTranslateZ(0);
        Rotate rotateX8 = new Rotate(210, Rotate.X_AXIS);
        Rotate rotateY8 = new Rotate(90, Rotate.Y_AXIS);
        face8.getTransforms().addAll(rotateY8, rotateX8);



        bottomFace.setTranslateX(0);
        bottomFace.setTranslateY(75);
        bottomFace.setTranslateZ(-150);
        bottomFace.setRotationAxis(Rotate.X_AXIS);
        bottomFace.setRotate(0);
        Rotate rotateX9 = new Rotate(90, Rotate.X_AXIS);
        Rotate rotateY9 = new Rotate(0, Rotate.Y_AXIS);
        bottomFace.getTransforms().addAll(rotateX9, rotateY9);
        // Add the faces to the Pyramid
        getChildren().addAll(face1, face2, face3, face4, bottomFace, face5, face6, face7, face8);
    }

    private Polygon createTriangle(double size, Color color) {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                0.0, 0.0,
                size / 2, -size,
                size, 0.0
        );
        triangle.setFill(color);
        return triangle;
    }

    
}
