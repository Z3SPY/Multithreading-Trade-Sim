package com.example;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class OrbitCont extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the Pyramid
        Orbit orbit = new Orbit(200, 200, 100);

        orbit.rotatePlanet(0, 40); 

        RotateTransition rotate2 = new RotateTransition(Duration.seconds(5), orbit);
        rotate2.setAxis(Rotate.Y_AXIS);
        rotate2.setByAngle(360); 
        rotate2.setInterpolator(Interpolator.LINEAR);
        rotate2.setCycleCount(Animation.INDEFINITE); 
        rotate2.play();
        


        // Setup the scene
        Group root = new Group(orbit);
        Scene scene = new Scene(root, 600, 400, true);
        scene.setFill(Color.WHITE);

        PerspectiveCamera camera = new PerspectiveCamera();
        camera.fieldOfViewProperty();

        scene.setCamera(camera);

        double cameraX = camera.getTranslateX();
        double cameraY = camera.getTranslateY();
        double cameraZ = camera.getTranslateZ();

        System.out.println("Camera Position: (" + cameraX + ", " + cameraY + ", " + cameraZ + ")");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Rotating Pyramid");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Orbit extends Group {
    private Circle planet;
    Rotate rotateX;
    Rotate rotateY;
    Rotate rotateZ;
    private Rotate planetRotationX;
    private Rotate planetRotationY;

    public Orbit(double centerX, double centerY, double radius) {

        rotateX = new Rotate(70, Rotate.X_AXIS);
        rotateY = new Rotate(0, Rotate.Y_AXIS); 
        rotateZ = new Rotate(0, Rotate.Z_AXIS); 

        Circle orbitCircle = new Circle(centerX, centerY, radius);
        orbitCircle.setFill(null); 
        orbitCircle.setStroke(Color.BLACK);
        orbitCircle.setStrokeWidth(2); 
        orbitCircle.getTransforms().addAll(rotateY, rotateX);
        

        planet = new Circle(centerX + radius, centerY, 10);
        planet.setFill(Color.BLUE); 
        planet.setStroke(null);
        planet.setTranslateZ(-10);
        planetRotationX = new Rotate(70, Rotate.X_AXIS);
        planetRotationY = new Rotate(0, Rotate.Y_AXIS);
        planet.getTransforms().addAll(planetRotationX, planetRotationY);

 

        // Animation to rotate the planet around the orbit circle
        /*Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), new KeyValue(planet.translateXProperty(), 2 * radius, Interpolator.LINEAR))
        );
        timeline.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely
        timeline.play(); // Start animation*/

        getChildren().addAll(orbitCircle, planet); // Add both circles to the group
    }

    public void rotatePlanet(double angleX, double angleY) {
        planetRotationX.setAngle(angleX);
        planetRotationY.setAngle(angleY);
    }

    
}