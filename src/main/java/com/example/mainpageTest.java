package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;

public class mainpageTest extends Application {
    
    @Override
    public void start(Stage mainStage) {
        GridPane grid = new GridPane();
        grid.setHgap(10); // Horizontal gap between columns
        grid.setVgap(10); // Vertical gap between rows
        grid.setStyle("-fx-background-color: #1E1E1E; -fx-padding: 20;");


        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(10);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(18);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(18);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(18);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(18);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(18);

        RowConstraints row0 = new RowConstraints();
        row0.setPercentHeight(5); 
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(17.5); 
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(8.75); 
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(8.75); 
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(60);

       
        grid.getColumnConstraints().addAll(col0, col1, col2, col3, col4, col5);
        grid.getRowConstraints().addAll(row0, row1, row2, row3, row4);


        //#region Title Pane Top
        StackPane titlePane = new StackPane();
        titlePane.getStyleClass().add("mainpage-cellStyle");
        titlePane.setTranslateX(30);
        grid.add(titlePane, 2, 0, 2, 1);
        //#endregion

        //#region MIDDLE PANE TOP
        StackPane middlePane = new StackPane();
        //middlePane.setStyle("-fx-background-color:BLUE");
        grid.add(middlePane, 0, 2, 6, 1);

        GridPane midGridPane = new GridPane();
        midGridPane.setHgap(10);

        ColumnConstraints midGridCol0 = new ColumnConstraints();
        midGridCol0.setPercentWidth(20);
        ColumnConstraints midGridCol1 = new ColumnConstraints();
        midGridCol1.setPercentWidth(20);
        ColumnConstraints midGridCol2 = new ColumnConstraints();
        midGridCol2.setPercentWidth(60);

        RowConstraints midGridRow0 = new RowConstraints();
        midGridRow0.setPercentHeight(100); 
        
        midGridPane.getRowConstraints().addAll(midGridRow0);
        midGridPane.getColumnConstraints().addAll(midGridCol0, midGridCol1, midGridCol2);
        

        /* =============================================  */
        StackPane marketPane = new StackPane();
        //marketPane.getStyleClass().add("mainpage-cellStyle");
        midGridPane.add(marketPane, 0, 0, 1, 1);

        Button marketButton = new Button("ISTO MARKET");
        marketButton.getStyleClass().addAll("button");
        marketButton.setMaxWidth(Double.MAX_VALUE); // Ensure button stretches horizontally


        marketPane.getChildren().addAll(marketButton);

        /* =============================================  */

        StackPane homePane = new StackPane();
        homePane.getStyleClass().add("mainpage-cellStyle");
        midGridPane.add(homePane, 1, 0, 1, 1);

        Button homeButton = new Button("ISTO HOME");
        homeButton.getStyleClass().addAll("button-selected");
        homeButton.setMaxWidth(Double.MAX_VALUE); // Ensure button stretches horizontally


        homePane.getChildren().addAll(homeButton);

        /* =============================================  */
        StackPane alertPane = new StackPane();

        // Create the HBox
        HBox hBox = new HBox();

        // Create the Label
        Label alertNewsText = new Label("INTERSTELLAR UPDATES");
        alertNewsText.setStyle("-fx-text-fill: #DC5F00;"); // Set text color

        // Create the left orange square
        Region leftSquare = new Region();
        leftSquare.setMinSize(10, 10);
        leftSquare.setMaxSize(20, 20);
        leftSquare.setTranslateX(-20);
        leftSquare.setStyle("-fx-background-color: #DC5F00;"); // Set color to orange

        // Create the right orange square
        Region rightSquare = new Region();
        rightSquare.setMinSize(10, 10);
        rightSquare.setMaxSize(20, 20);
        rightSquare.setTranslateX(20);
        rightSquare.setStyle("-fx-background-color: #DC5F00;"); // Set color to orange

        // Add the squares and the label to the HBox
        hBox.getChildren().addAll(leftSquare, alertNewsText, rightSquare);

        // Set the alignment of the HBox to center the label
        HBox.setHgrow(leftSquare, Priority.ALWAYS);
        HBox.setHgrow(rightSquare, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER);

        // Add the HBox to the alertPane
        alertPane.getChildren().add(hBox);

        GridPane.setHgrow(alertPane, Priority.NEVER);
        GridPane.setVgrow(alertPane, Priority.NEVER);
        alertPane.setMinSize(25, 25);
        alertPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        alertPane.getStyleClass().addAll("mainpage-cellStyle", "alert");
        midGridPane.add(alertPane, 2, 0, 1, 1);









        middlePane.getChildren().addAll(midGridPane);
        //MIDDLE PANE TOP END
        //#endregion
        
        //#region LOWER MIDDLE 

        StackPane lowerMiddlePane = new StackPane();
        grid.add(lowerMiddlePane, 0, 3, 4, 1);


        GridPane lowMidGridPane = new GridPane();
        lowMidGridPane.setHgap(10);

        ColumnConstraints lowMidGridCol0 = new ColumnConstraints();
        lowMidGridCol0.setPercentWidth(32);
        ColumnConstraints lowMidGridCol1 = new ColumnConstraints();
        lowMidGridCol1.setPercentWidth(23.8);
        ColumnConstraints lowMidGridCol2 = new ColumnConstraints();
        lowMidGridCol2.setPercentWidth(44.2);

        RowConstraints lowMidGridRow0 = new RowConstraints();
        lowMidGridRow0.setPercentHeight(100); 

        lowMidGridPane.getColumnConstraints().addAll(lowMidGridCol0, lowMidGridCol1, lowMidGridCol2);
        lowMidGridPane.getRowConstraints().addAll(lowMidGridRow0);

        /* =============================================  */

        StackPane profPane = new StackPane();
        //profPane.getStyleClass().add("mainpage-cellStyle"); Remove when button is not selected
        lowMidGridPane.add(profPane, 0, 0, 1, 1);

        Button profileButton = new Button("ISTO PROFILE");
        profileButton.getStyleClass().addAll("button");
        profileButton.setMaxWidth(Double.MAX_VALUE); // Ensure button stretches horizontally


        profPane.getChildren().addAll(profileButton);
        /* =============================================  */


        StackPane datePane = new StackPane();
        datePane.getStyleClass().add("mainpage-cellStyle");
        GridPane.setHgrow(datePane, Priority.NEVER);
        GridPane.setVgrow(datePane, Priority.NEVER);
        datePane.setMinSize(25, 25);
        datePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Create the GridPane for the layout
        GridPane dateGrid = new GridPane();

        Region topRect1 = new Region();
        topRect1.setMinSize(50, 10);
        topRect1.setStyle("-fx-background-color: #DC5F00;"); // Orange color

        Region topRect2 = new Region();
        topRect2.setMinSize(30, 10);
        topRect2.setStyle("-fx-background-color: #DC5F00;"); // Orange color

        VBox topSection = new VBox(5); 
        topSection.getChildren().addAll(topRect1, topRect2);

        // Create the middle section with a dark gray rectangle
        Region middleRect = new Region();
        middleRect.setMinSize(20, 20);
        middleRect.setStyle("-fx-background-color: #555555;"); // Dark gray color

        // Create the bottom section with two numbers
        Label number1 = new Label("7");
        number1.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 20;"); // White color

        Label number2 = new Label("20");
        number2.setStyle("-fx-text-fill: #DC5F00; -fx-font-size: 20;"); // Orange color

        HBox bottomSection = new HBox(10); // Horizontal box with spacing
        bottomSection.getChildren().addAll(number1, middleRect, number2);
        bottomSection.setAlignment(Pos.CENTER);

        // Add sections to the GridPane
        dateGrid.add(topSection, 0, 0);
        dateGrid.add(middleRect, 0, 1);
        dateGrid.add(bottomSection, 0, 2);

        // Center the GridPane in the StackPane
        StackPane.setAlignment(dateGrid, Pos.CENTER);

        // Add the GridPane to the StackPane
        datePane.getChildren().add(dateGrid);

        // Add the datePane to the lowMidGridPane
        lowMidGridPane.add(datePane, 1, 0, 1, 1);





















        

        // Create the timePane
        StackPane timePane = new StackPane();
        Label timeText = new Label("23:00:00");
        timeText.setStyle("-fx-text-fill: #DC5F00;"); // Set text color

        timePane.getChildren().addAll(timeText);
        timePane.setTranslateX(10);

        GridPane.setHgrow(timePane, Priority.NEVER);
        GridPane.setVgrow(timePane, Priority.NEVER);
        timePane.setMinSize(25, 25);
        timePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        timePane.getStyleClass().addAll("time");

        // Create the orange regiong
        Region orangeRegion = new Region();
        orangeRegion.setMinSize(10, 10);
        orangeRegion.setMaxSize(55, Double.MAX_VALUE);
        orangeRegion.setStyle("-fx-text-fill: #DC5F00; -fx-border-color: #DC5F00;"); // Set color to orange
        orangeRegion.setTranslateX(20);

        // Create an HBox to hold timePane and the orange region
        HBox timeHBox = new HBox();
        timeHBox.getChildren().addAll(timePane, orangeRegion);
        timeHBox.getStyleClass().add("mainpage-cellStyle");

        HBox.setHgrow(timePane, Priority.NEVER);
        HBox.setHgrow(orangeRegion, Priority.ALWAYS);

        // Add the HBox to the lowMidGridPane
        lowMidGridPane.add(timeHBox, 2, 0, 1, 1);

        lowerMiddlePane.getChildren().addAll(lowMidGridPane);

        //LOWER MIDDLE END

        //#endregion


        //#region Wallet Pane
        StackPane walPane = new StackPane();
        walPane.getStyleClass().addAll("mainpage-cellStyle", "wallet");

         // Create the ISTO WALLET label
        Label titleLabel = new Label("ISTO WALLET");
        titleLabel.setTranslateX(20);

        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Arial", 28)); // Adjust font and size as needed
        // Create the Balance label
        Label balanceLabel = new Label("Bal: 25.10 K SHK");
        balanceLabel.setTranslateX(45);
        balanceLabel.setTextFill(Color.BLACK);
        balanceLabel.setFont(Font.font("Arial", 20)); // Adjust font and size as needed

        // Create the Cp ID label
        Label cpIdLabel = new Label("Cp ID: 211934-455");
        cpIdLabel.setTranslateX(30);
        cpIdLabel.setTranslateY(-10);
        cpIdLabel.setTextFill(Color.BLACK);
        cpIdLabel.setFont(Font.font("Arial", 20)); // Adjust font and size as needed

        // Create a VBox to hold the labels
        VBox vbox = new VBox(0); // Spacing between labels
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.getChildren().addAll(titleLabel, balanceLabel, cpIdLabel);

        // Add the VBox to the StackPane
        walPane.getChildren().add(vbox);

        GridPane.setHgrow(walPane, Priority.ALWAYS);
        GridPane.setVgrow(walPane, Priority.ALWAYS);
        walPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        grid.add(walPane, 0, 1, 2, 1);
        //#endregion

        //#region Vis Pane 
        StackPane visPaneTop = new StackPane();
        visPaneTop.getStyleClass().add("mainpage-cellStyle-2");

        Image image = new Image(new File("src\\\\main\\\\java\\\\com\\\\example\\\\space.gif").toURI().toString());
        ImageView imageView = new ImageView(image);
        
        
        StackPane imgContent = new StackPane();
        imgContent.setStyle("-fx-background-color: #1E1E1E;"); // Set background color

        imgContent.setMinSize(100, 100); // Adjust as needed
        imgContent.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Allow StackPane to grow

        imageView.fitWidthProperty().bind(imgContent.widthProperty());
        imageView.fitHeightProperty().bind(imgContent.heightProperty());
        imageView.setPreserveRatio(false); // Maintain aspect ratio

        imgContent.getChildren().addAll(imageView);

        visPaneTop.getChildren().addAll(imgContent);

        grid.add(visPaneTop, 2, 1, 4, 1);
        //#endregion

        //#region Main Vis 
        StackPane visPane = new StackPane();
        GridPane.setHgrow(visPane, Priority.NEVER);
        GridPane.setVgrow(visPane, Priority.NEVER);
        
        visPane.getStyleClass().add("mainpage-cellStyle-2");
        grid.add(visPane, 0, 4, 4, 1);
        //#endregion



        //#region BOTTOM RIGHT VIS PANE START
        StackPane infoPane = new StackPane();
        //infoPane.setStyle("-fx-background-color:BLUE");
        grid.add(infoPane, 4, 3, 2, 3);

        GridPane infoGridPane = new GridPane();
        infoGridPane.setVgap(10);
        
        ColumnConstraints infoGridPaneCol0 = new ColumnConstraints();
        infoGridPaneCol0.setPercentWidth(100);
       

        RowConstraints infoGridPaneRow0 = new RowConstraints();
        infoGridPaneRow0.setPercentHeight(22); 
        RowConstraints infoGridPaneRow1 = new RowConstraints();
        infoGridPaneRow1.setPercentHeight(34.66); 
        RowConstraints infoGridPaneRow2 = new RowConstraints();
        infoGridPaneRow2.setPercentHeight(28.33); 
        RowConstraints infoGridPaneRow3 = new RowConstraints();
        infoGridPaneRow3.setPercentHeight(15);


        infoGridPane.getColumnConstraints().addAll(infoGridPaneCol0);
        infoGridPane.getRowConstraints().addAll(infoGridPaneRow0, infoGridPaneRow1, infoGridPaneRow2, infoGridPaneRow3);
        
        /* CONTAINS UPDATES ARROWS UPS AND DOWNS */
        StackPane stockUpdatePane = new StackPane();
        stockUpdatePane.getStyleClass().add("mainpage-cellStyle");
        GridPane.setHgrow(stockUpdatePane, Priority.NEVER);
        GridPane.setVgrow(stockUpdatePane, Priority.NEVER);
        stockUpdatePane.setMinSize(20, 20);
        stockUpdatePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Create the HBox to hold the three squares
        HBox stockHBox = new HBox(15); // Horizontal box with spacing
        stockHBox.setMinSize(20, 20);
        stockHBox.setMaxSize(Double.MAX_VALUE, 70);
        stockHBox.setTranslateX(20);
        
        Region square1 = new Region();
        square1.setMinSize(75, 50);
        square1.setStyle("-fx-background-color: #1E1E1E; -fx-border-color: #DC5F00; -fx-border-width: 2;");

        Region square2 = new Region();
        square2.setMinSize(75, 50);
        square2.setStyle("-fx-background-color: #1E1E1E; -fx-border-color: #DC5F00; -fx-border-width: 2;");

        Region square3 = new Region();
        square3.setMinSize(75, 50);
        square3.setStyle("-fx-background-color: #1E1E1E; -fx-border-color: #DC5F00; -fx-border-width: 2;");

        stockHBox.getChildren().addAll(square1, square2, square3);
        StackPane.setAlignment(stockHBox, Pos.CENTER);
        stockUpdatePane.getChildren().add(stockHBox);

        infoGridPane.add(stockUpdatePane, 0, 0, 1, 1);
        lowerMiddlePane.getChildren().add(infoGridPane);







        /* TRADE DETAILS */
        StackPane tradeDetPane = new StackPane();
        GridPane tradeDetPaneGrid = new GridPane();

        Label titleTradeDet = new Label("TRADE DETAILS");
        titleTradeDet.setStyle("-fx-text-fill: #DC5F00; -fx-font-size: 25px;");

        Label subTitleTradeDet = new Label("Trade Item: << Space Rocks >>");
        subTitleTradeDet.setStyle("-fx-text-fill: #DC5F00; -fx-font-size: 15px;");



        
        tradeDetPaneGrid.add(titleTradeDet, 0, 0);
        tradeDetPaneGrid.add(subTitleTradeDet, 0, 1);




        tradeDetPane.getChildren().addAll(tradeDetPaneGrid);
        infoGridPane.add(tradeDetPane, 0, 1, 1, 1);


        StackPane topLdrPane = new StackPane();
        topLdrPane.getStyleClass().add("mainpage-cellStyle");
        infoGridPane.add(topLdrPane, 0, 2, 1, 1);

        StackPane botPane = new StackPane();
        botPane.getStyleClass().add("mainpage-cellStyle");
        infoGridPane.add(botPane, 0, 3, 1, 1);

        infoPane.getChildren().addAll(infoGridPane);

        //BOTTOM RIGHT VIS PANE END 
        //#endregion
        


        /* BUTTON CLICK INTERACTIONS */
        // Event handling to toggle button selection
        marketButton.setOnAction(event -> {
            marketButton.getStyleClass().clear(); // Clear existing styles
            marketButton.getStyleClass().addAll( "button-selected");
            marketPane.getStyleClass().add("mainpage-cellStyle");


            // Create colored content for marketButton
            StackPane marketContent = new StackPane();
            marketContent.setStyle("-fx-background-color: #FF5733;"); // Set background color
            // You can add more nodes or content to marketContent if needed

            // Add content to visPane
            visPane.getChildren().addAll(marketContent);


            /* HOME BUTTON BEHAVIOUR */
            homePane.getStyleClass().clear();
            homeButton.getStyleClass().clear(); // Clear existing styles
            homeButton.getStyleClass().add("button");

            /*Profile*/
            profPane.getStyleClass().clear();
            profileButton.getStyleClass().clear();
            profileButton.getStyleClass().add("button");

        });

        StackPane homeContent = new StackPane();
        homeContent.setStyle("-fx-background-color: #1E1E1E;"); // Set background color

        Planet planet = new Planet(150, 300, 300);


        homeContent.setMinSize(300, 300); // Adjust as needed
        homeContent.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Allow StackPane to grow


        Image bHoleImage = new Image(new File("src\\main\\java\\com\\example\\bhole.gif").toURI().toString());
        ImageView bHoleImageView = new ImageView(bHoleImage);
        

        imgContent.setMinSize(100, 100); // Adjust as needed
        imgContent.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Allow StackPane to grow

        bHoleImageView.fitWidthProperty().bind(homeContent.widthProperty());
        bHoleImageView.fitHeightProperty().bind(homeContent.heightProperty());
        bHoleImageView.setPreserveRatio(false); // Maintain aspect ratio
        

        homeContent.getChildren().addAll(bHoleImageView, planet);
        visPane.getChildren().add(homeContent);


        homeButton.setOnAction(event -> {
            homeButton.getStyleClass().clear(); // Clear existing styles
            homeButton.getStyleClass().addAll("button", "button-selected");
            homePane.getStyleClass().add("mainpage-cellStyle");


            // Create colored content for homeButton
            
           
            // You can add more nodes or content to homeContent if needed
            StackPane homeContentReset = new StackPane();
            homeContentReset.setStyle("-fx-background-color: #1E1E1E;"); // Set background color
            homeContentReset.setMinSize(300, 300); // Adjust as needed
            homeContentReset.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Allow StackPane to grow

            // Add content to visPane
            homeContentReset.getChildren().addAll(bHoleImageView, planet);
            visPane.getChildren().add(homeContentReset);


            /* MARKET BUTTON BEHAVIOUR */
            marketPane.getStyleClass().clear();
            marketButton.getStyleClass().clear(); // Clear existing styles
            marketButton.getStyleClass().add("button");

            /*Profile*/
            profPane.getStyleClass().clear();
            profileButton.getStyleClass().clear();
            profileButton.getStyleClass().add("button");
        });
        
        profileButton.setOnAction(event -> {
            profileButton.getStyleClass().clear(); // Clear existing styles
            profileButton.getStyleClass().addAll("button", "button-selected");
            profPane.getStyleClass().add("mainpage-cellStyle");


            // Create colored content for profileButton
            StackPane profileContent = new StackPane();
            profileContent.setStyle("-fx-background-color: #27AE60;"); // Set background color
            // You can add more nodes or content to profileContent if needed

            // Add content to visPane
            visPane.getChildren().add(profileContent);
             

            /* MARKET BUTTON BEHAVIOUR */
            marketPane.getStyleClass().clear();
            marketButton.getStyleClass().clear(); // Clear existing styles
            marketButton.getStyleClass().add("button");

            /* HOME BUTTON BEHAVIOUR */
            homePane.getStyleClass().clear();
            homeButton.getStyleClass().clear(); // Clear existing styles
            homeButton.getStyleClass().add("button");

        });

        /* BUTTON CLICK INTERACTIONS */
        

        Scene scene = new Scene(grid, 900, 750);
        scene.setCamera(new PerspectiveCamera());
        scene.getStylesheets().add(getClass().getResource("/com/example/styles.css").toExternalForm());
        mainStage.setTitle("ISTO SYSTEM");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}