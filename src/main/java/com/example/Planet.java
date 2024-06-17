package com.example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Planet extends Group {

    private Pyramid pyramid;
    private Rotate rotateY;
    private Rotate constantRotateX;
    private Timeline timeline;
    private double layOutX = 0;
    private double layOutY = 0;

    public Planet(double size) {
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

    public Pyramid(double size) {
        // Create the four faces of the pyramid
        // Upper
        Polygon face1 = createTriangle(size, Color.WHITE);
        Polygon face2 = createTriangle(size, Color.WHITE);
        Polygon face3 = createTriangle(size, Color.WHITE);
        Polygon face4 = createTriangle(size, Color.WHITE);

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

        // Add the faces to the Pyramid
        getChildren().addAll(face1, face2, face3, face4);
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
