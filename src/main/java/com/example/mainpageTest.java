package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import py4j.GatewayServer;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.util.HashMap;
import java.util.Map;

public class mainpageTest extends Application {

    public static JavaFXLineGraph lineGraphRef;
    public static StackPane lineGraphStackPane;

    Label priceLabelOutput; 
    Label ctgrLabelOutput;
    Label subTitleTradeDet;


    public Map<String, Object> curAccount;
    public static Profile objProfileInstance;
    public mainpageTest(Profile profileInstance) {
        this.objProfileInstance = profileInstance;
        this.curAccount = profileInstance.getProfileData();
        System.out.println(this.curAccount.get("name"));

    }

    @Override
    public void start(Stage mainStage) {

        createGateWayServer();


        lineGraphRef = new JavaFXLineGraph();
        lineGraphStackPane = lineGraphRef.createStackPane();
        lineGraphRef.currentCustomerProperty().addListener((obs, oldStock, newStock) -> {
            System.out.println("OBS: "+ obs);
            System.out.println("oldStk: "+ oldStock);
            System.out.println("newStk {\n Object: "+ newStock
             + " \n Name: " + newStock.getName() 
             + " \n Data:" +  newStock.getData()
             + " \n Cur Price:" + ((double) Math.round(((double) newStock.getData().get(newStock.getData().size() - 1).getYValue() * 10000)) / 10000)
             ); 

            //Parsing Values from String
            Pattern pattern = Pattern.compile("(.*)\\((.*)\\) // (.*)");
            Matcher matcher = pattern.matcher(newStock.getName());

            if (matcher.matches()) {
                String category = matcher.group(1).trim(); // "Space Rocks"
                String abbreviation = matcher.group(2).trim(); // "SR"
                String type = matcher.group(3).trim(); // "MILITARY"
                
                System.out.println("Category: " + category);
                System.out.println("Abbreviation: " + abbreviation);
                System.out.println("Type: " + type);

                subTitleTradeDet.setText(abbreviation);
                ctgrLabelOutput.setText(type);
                priceLabelOutput.setText(Double.toString((double) Math.round(((double) newStock.getData().get(newStock.getData().size() - 1).getYValue() * 10000)) / 10000));
            }

            
        });
        


        Scene mainScene = createMainScene();
        mainScene.getStylesheets().add(this.getClass().getResource("chart.css").toExternalForm());
        
        mainStage.setTitle("ISTO SYSTEM");
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        mainStage.show();
    }
    
    

    //#region MAIN SCENE FRONTEND
    public Scene createMainScene() {
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
        //#endregion

        //#region Date
        StackPane datePane = new StackPane();
        datePane.getStyleClass().add("mainpage-cellStyle");
        GridPane.setHgrow(datePane, Priority.NEVER);
        GridPane.setVgrow(datePane, Priority.NEVER);
        datePane.setMinSize(25, 25);
        datePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Create the GridPane for the layout
        GridPane dateGrid = new GridPane();


        Label topRect1 = new Label("-{ Month / Day / Year }-");
        topRect1.setStyle("-fx-text-fill: #DC5F00; -fx-font-size: 12px;"); // Orange color
        topRect1.setTranslateY(3);


        VBox topSection = new VBox(1); 
        topSection.getChildren().addAll(topRect1);

        // Create a Month
        Label monthText = new Label("Apr");
        monthText.setStyle("-fx-text-fill: #DC5F00; -fx-font-size: 20;");


        // Create the bottom section with two numbers
        Label dayText = new Label("7");
        dayText.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 20;"); 

        // Create the middle section with a dark gray rectangle
        Region middleRect = new Region();
        middleRect.setMinSize(10, 3);
        middleRect.setMaxSize(10, 3);
        middleRect.setStyle("-fx-background-color: #555555;"); // Dark gray color

        Region middleRect2 = new Region();
        middleRect2.setMinSize(10, 3);
        middleRect2.setMaxSize(10, 3);
        middleRect2.setStyle("-fx-background-color: #555555;"); // Dark gray color

        Label yearText = new Label("4122");
        yearText.setStyle("-fx-text-fill: #DC5F00; -fx-font-size: 20;"); // Orange color

        HBox bottomSection = new HBox(2); // Horizontal box with spacing
        bottomSection.getChildren().addAll(monthText, middleRect,dayText, middleRect2, yearText);
        bottomSection.setAlignment(Pos.CENTER);

        // Add sections to the GridPane
        dateGrid.setAlignment(Pos.CENTER);
        dateGrid.add(topSection, 0, 0);
        dateGrid.add(bottomSection, 0, 1);

        // Center the GridPane in the StackPane
        StackPane.setAlignment(dateGrid, Pos.CENTER);

        // Add the GridPane to the StackPane
        datePane.getChildren().add(dateGrid);

        // Add the datePane to the lowMidGridPane
        lowMidGridPane.add(datePane, 1, 0, 1, 1);
        //#endregion
        
        //#region TimePane
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
        orangeRegion.setStyle("-fx-text-fill: #DC5F00; -fx-background-color: #DC5F00;"); // Set color to orange
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
        Label balanceLabel = new Label("BALANCE: 25.10 K SHK");
        balanceLabel.setTranslateX(25);
        balanceLabel.setTranslateY(5);
        balanceLabel.setTextFill(Color.WHITE);
        balanceLabel.setFont(Font.font("Arial", 20)); // Adjust font and size as needed

        // Create the Cp ID label
        Label cpIdLabel = new Label("Cp ID: 211934-455");
        cpIdLabel.setTranslateX(55);
        cpIdLabel.setTranslateY(0);
        cpIdLabel.setTextFill(Color.BLACK);
        cpIdLabel.setFont(Font.font("Arial", 20)); // Adjust font and size as needed

        Region walDivider = new Region();
        walDivider.setMinSize(20, 5);
        walDivider.setMaxSize(Double.MAX_VALUE, 5);
        walDivider.setStyle("-fx-background-color: #1E1E1E;"); // Dark gray color

        Region walDivider2 = new Region();
        walDivider2.setMinSize(20, 5);
        walDivider2.setMaxSize(Double.MAX_VALUE, 5);
        walDivider2.setStyle("-fx-background-color: #1E1E1E;"); // Dark gray color

        // Create a VBox to hold the labels
        VBox vbox = new VBox(0); // Spacing between labels
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.getChildren().addAll(titleLabel, walDivider, balanceLabel, cpIdLabel, walDivider2);

        // Add the VBox to the StackPane
        walPane.getChildren().add(vbox);
        walPane.setTranslateY(-5);

        GridPane.setHgrow(walPane, Priority.ALWAYS);
        GridPane.setVgrow(walPane, Priority.ALWAYS);
        walPane.setMaxSize(Double.MAX_VALUE, 50);

        grid.add(walPane, 0, 1, 2, 1);
        //#endregion

        //#region Vis Pane 
        StackPane visPaneTop = new StackPane();
        visPaneTop.getStyleClass().add("mainpage-cellStyle-2");

        Image image = new Image(new File("src/main/java/com/example/space.gif").toURI().toString());
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

        //#region Info Pane and Bottom Right Grid
        StackPane infoPane = new StackPane();
        infoPane.setTranslateY(-5);
        grid.add(infoPane, 4, 3, 2, 3);

        GridPane infoGridPane = new GridPane();
        infoGridPane.setVgap(10);
        
        ColumnConstraints infoGridPaneCol0 = new ColumnConstraints();
        infoGridPaneCol0.setPercentWidth(100);
       

        RowConstraints infoGridPaneRow0 = new RowConstraints();
        infoGridPaneRow0.setPercentHeight(21); 
        RowConstraints infoGridPaneRow1 = new RowConstraints();
        infoGridPaneRow1.setPercentHeight(37.66); 
        RowConstraints infoGridPaneRow2 = new RowConstraints();
        infoGridPaneRow2.setPercentHeight(28.33); 
        RowConstraints infoGridPaneRow3 = new RowConstraints();
        infoGridPaneRow3.setPercentHeight(15);


        infoGridPane.getColumnConstraints().addAll(infoGridPaneCol0);
        infoGridPane.getRowConstraints().addAll(infoGridPaneRow0, infoGridPaneRow1, infoGridPaneRow2, infoGridPaneRow3);
        
        /* CONTAINS UPDATES ARROWS UPS AND DOWNS */
        StackPane stockUpdatePane = new StackPane();
        stockUpdatePane.setTranslateY(6);
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

        //#endregion

        //#region Trade Details 
        StackPane tradeDetPane = new StackPane();
        GridPane tradeDetPaneGrid = new GridPane();
        tradeDetPaneGrid.setVgap(0);
        tradeDetPaneGrid.setHgap(0);

        ColumnConstraints tradeDetPaneGridCol0 = new ColumnConstraints();
        tradeDetPaneGridCol0.setPercentWidth(75);
        ColumnConstraints tradeDetPaneGridCol1 = new ColumnConstraints();
        tradeDetPaneGridCol1.setPercentWidth(25);
        tradeDetPaneGrid.getColumnConstraints().addAll(tradeDetPaneGridCol0,tradeDetPaneGridCol1); // each get 50% of width


        Label titleTradeDet = new Label("TRADE DETAILS");
        titleTradeDet.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 25px;");

        // Defined Statically 
        subTitleTradeDet = new Label("Item: << Space Rocks >>");
        subTitleTradeDet.setTranslateY(-5);
        subTitleTradeDet.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 15px;");

        tradeDetPaneGrid.add(titleTradeDet, 0, 0);
        tradeDetPaneGrid.add(subTitleTradeDet, 0, 1);

        /* Separate Inner Grid */

        GridPane tradeDetPaneInnerGrid = new GridPane();
        tradeDetPaneInnerGrid.setHgap(0);
        tradeDetPaneInnerGrid.setVgap(5);



        ColumnConstraints trdDefPnIGCol1 = new ColumnConstraints();
        trdDefPnIGCol1.setPercentWidth(25);
        ColumnConstraints trdDefPnIGCol2 = new ColumnConstraints();
        trdDefPnIGCol2.setPercentWidth(75);
        
        tradeDetPaneInnerGrid.getColumnConstraints().addAll(trdDefPnIGCol1, trdDefPnIGCol2); // each get 50% of width
   

        
        StackPane ctgryStackLabel = new StackPane();
        ctgryStackLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 15px; -fx-border-width: 2; -fx-padding: 5px; -fx-border-color: #DC5F00;");

        Label ctgryLabel = new Label("Ctgry");
        ctgryLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 15px; -fx-border-width: 2; -fx-padding: 5px; ");
        ctgryStackLabel.getChildren().addAll(ctgryLabel);


        StackPane ctgrStackLabelOutput = new StackPane();
        ctgrStackLabelOutput.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 15px; -fx-border-width: 2; -fx-padding: 5px; -fx-border-color: #DC5F00;");

        //Defined Statically
        ctgrLabelOutput = new Label("aaa");
        ctgrLabelOutput.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 15px; -fx-border-width: 2; -fx-padding: 5px; ");
        ctgrStackLabelOutput.getChildren().addAll(ctgrLabelOutput);

        /* */
        StackPane priceStackLabel = new StackPane();
        priceStackLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 15px; -fx-border-width: 2; -fx-padding: 5px; -fx-border-color: #DC5F00;");

        Label priceLabel = new Label("Price");
        priceLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 15px; -fx-border-width: 2; -fx-padding: 5px; ");
        priceStackLabel.getChildren().addAll(priceLabel);

        StackPane priceStackLabelOutput = new StackPane();
        priceStackLabelOutput.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 15px; -fx-border-width: 2; -fx-padding: 5px; -fx-border-color: #DC5F00;");

        //Defined Statically 
        priceLabelOutput = new Label("aaaAaaaaaa");
        priceLabelOutput.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 15px; -fx-border-width: 2; -fx-padding: 5px; ");
        priceStackLabelOutput.getChildren().addAll(priceLabelOutput);
        

        /* Categories and Price */
        tradeDetPaneInnerGrid.add(ctgryStackLabel, 0, 0);
        tradeDetPaneInnerGrid.add(ctgrStackLabelOutput, 1, 0, 1, 1);
        tradeDetPaneInnerGrid.add(priceStackLabel, 0, 1);
        tradeDetPaneInnerGrid.add(priceStackLabelOutput, 1, 1, 1, 1);

        /* Add Inner Grid */
        tradeDetPaneGrid.add(tradeDetPaneInnerGrid, 0, 2, 2, 1);
        tradeDetPaneGrid.setAlignment(Pos.CENTER_LEFT);

        tradeDetPane.getChildren().addAll(tradeDetPaneGrid);

        StackPane buyAndSellPane = new StackPane();
        buyAndSellPane.getStyleClass().add("mainpage-cellStyle");


        //Should Have only one button Buy or sell
        Button buyButton = new Button("Buy");
        Button sellButton = new Button("Sell");
        buyButton.getStyleClass().add("buy-sell-btn");
        sellButton.getStyleClass().add("buy-sell-btn");
        sellButton.setTranslateY(-5);


        tradeDetPaneGrid.add(buyButton, 1, 0, 1, 1);
        tradeDetPaneGrid.add(sellButton, 1, 1, 1, 1);


        infoGridPane.add(tradeDetPane, 0, 1, 2, 1);

    
        //#endregion
        
        //#region  Leader Board
        StackPane topLdrPane = new StackPane();
        //topLdrPane.getStyleClass().add("mainpage-cellStyle");
        

        GridPane leaderGrid = new GridPane();

        ColumnConstraints leaderGridcol0 = new ColumnConstraints();
        leaderGridcol0.setPercentWidth(25);
        ColumnConstraints leaderGridcol1 = new ColumnConstraints();
        leaderGridcol1.setPercentWidth(75);

        leaderGrid.getColumnConstraints().addAll(leaderGridcol0, leaderGridcol1);

        RowConstraints leaderGridRow0 = new RowConstraints();
        leaderGridRow0.setPercentHeight(25);
        RowConstraints leaderGridRow1 = new RowConstraints();
        leaderGridRow1.setPercentHeight(20);
        RowConstraints leaderGridRow2 = new RowConstraints();
        leaderGridRow2.setPercentHeight(20);
        RowConstraints leaderGridRow3 = new RowConstraints();
        leaderGridRow3.setPercentHeight(20);
        RowConstraints leaderGridRow4 = new RowConstraints();
        leaderGridRow4.setPercentHeight(15);

        leaderGrid.getRowConstraints().addAll(leaderGridRow0, leaderGridRow1, leaderGridRow2, leaderGridRow3, leaderGridRow4);

        topLdrPane.getChildren().addAll(leaderGrid);

        StackPane leaderBoardTitle = new StackPane();
        leaderBoardTitle.setStyle("-fx-border-color: #DC5F00 ; -fx-border-width: 2; -fx-padding: 1px; ");

        GridPane.setHgrow(leaderBoardTitle, Priority.NEVER);
        GridPane.setVgrow(leaderBoardTitle, Priority.NEVER);
        leaderBoardTitle.setMinSize(25, 25);
        leaderBoardTitle.setMaxSize(Double.MAX_VALUE, 40);

        Label ldrTitle = new Label("TOP 3 EMPLOYESS BY PROFIT");
        ldrTitle.setStyle("-fx-text-fill: #DC5F00; -fx-font-size: 25px; -fx-border-width: 2; -fx-padding: 5px; ");
        leaderBoardTitle.getChildren().add(ldrTitle);


        StackPane plyr1Name = new StackPane();
        plyr1Name.setStyle("-fx-border-color: #1E1E1E; -fx-background-color:  #DC5F00; ");
        Label p1N = new Label("JOSHUA");
        p1N.setStyle("-fx-text-color: #1E1E1E;");
        StackPane plyr1Status = new StackPane();
        plyr1Status.setStyle("-fx-border-color: #DC5F00;");
        Label p1S = new Label("Active");
        p1S.setStyle("-fx-text-fill: #DC5F00");

        plyr1Name.getChildren().addAll(p1N);
        plyr1Status.getChildren().addAll(p1S);

        StackPane plyr2Name = new StackPane();
        plyr2Name.setStyle("-fx-border-color: #1E1E1E; -fx-background-color:  #DC5F00; ");
        Label p2N = new Label("EMMA");
        p2N.setStyle("-fx-text-color: #1E1E1E;");
        StackPane plyr2Status = new StackPane();
        plyr2Status.setStyle("-fx-border-color: #DC5F00;");
        Label p2S = new Label("Inactive");
        p2S.setStyle("-fx-text-fill: #DC5F00");


        plyr2Name.getChildren().addAll(p2N);
        plyr2Status.getChildren().addAll(p2S);

        StackPane plyr3Name = new StackPane();
        plyr3Name.setStyle("-fx-border-color: #1E1E1E; -fx-background-color:  #DC5F00; ");
        Label p3N = new Label("LIAM");
        p3N.setStyle("-fx-text-color: #1E1E1E;");
        StackPane plyr3Status = new StackPane();
        plyr3Status.setStyle("-fx-border-color: #DC5F00;");
        Label p3S = new Label("Active");
        p3S.setStyle("-fx-text-fill: #DC5F00");


        plyr3Name.getChildren().addAll(p3N);
        plyr3Status.getChildren().addAll(p3S);





        leaderGrid.add(leaderBoardTitle, 0, 0, 2, 1);
        leaderGrid.add(plyr1Name, 0, 1, 1, 1);
        leaderGrid.add(plyr1Status, 1, 1, 1, 1);
        leaderGrid.add(plyr2Name, 0, 2, 1, 1);
        leaderGrid.add(plyr2Status, 1, 2, 1, 1);
        leaderGrid.add(plyr3Name, 0, 3, 1, 1);
        leaderGrid.add(plyr3Status, 1, 3, 1, 1);




        infoGridPane.add(topLdrPane, 0, 2, 1, 2);



        // Robot Design 
        StackPane roboFace = new StackPane();
        Region roboDesign = new Region();
        roboDesign.setStyle("-fx-background-color:  #DC5F00;");

        leaderGrid.add(roboFace,0, 4, 1, 1);
        leaderGrid.add(roboDesign,1, 4, 1, 1);

        

        infoPane.getChildren().addAll(infoGridPane);

        //BOTTOM RIGHT VIS PANE END 
        //#endregion
        


        //#region BUTTON CLICK INTERACTIONS
        // Event handling to toggle button selection
        marketButton.setOnAction(event -> {
            marketButton.getStyleClass().clear(); // Clear existing styles
            marketButton.getStyleClass().addAll( "button-selected");
            marketPane.getStyleClass().add("mainpage-cellStyle");


            // Create colored content for marketButton
            StackPane marketContent = new StackPane();
            marketContent.setStyle("-fx-background-color: #FF5733;"); 
            marketContent.getChildren().add(lineGraphStackPane);
            
            
            
            
            // Set background color
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


        Image blackHoleImage = new Image(new File("src/main/java/com/example/blackhole.gif").toURI().toString());
        ImageView blackHoleImageView = new ImageView(blackHoleImage);
        

        imgContent.setMinSize(100, 100); // Adjust as needed
        imgContent.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Allow StackPane to grow

        blackHoleImageView.fitWidthProperty().bind(homeContent.widthProperty());
        blackHoleImageView.fitHeightProperty().bind(homeContent.heightProperty());
        blackHoleImageView.setPreserveRatio(false); // Maintain aspect ratio
        

        homeContent.getChildren().addAll(blackHoleImageView, planet);
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
            homeContentReset.getChildren().addAll(blackHoleImageView, planet);
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
            profileContent.setStyle("-fx-background-color: #1E1E1E;"); // Set background color
            

            GridPane profileContentGrid = new GridPane();
            profileContentGrid.setVgap(5);

            ColumnConstraints profileContentGridCol0 = new ColumnConstraints();
            profileContentGridCol0.setPercentWidth(50);
            ColumnConstraints profileContentGridCol1 = new ColumnConstraints();
            profileContentGridCol1.setPercentWidth(50);

            profileContentGrid.getColumnConstraints().addAll(profileContentGridCol0, profileContentGridCol1);

            RowConstraints profileContentGridRow0 = new RowConstraints();
            profileContentGridRow0.setPercentHeight(50);
            RowConstraints profileContentGridRow1 = new RowConstraints();
            profileContentGridRow1.setPercentHeight(50);

            profileContentGrid.getRowConstraints().addAll(profileContentGridRow0, profileContentGridRow1);

            
            ImageView profilePicture = new ImageView();
            profilePicture.setFitWidth(150);
            profilePicture.setFitHeight(150);
            profilePicture.setImage(new Image(new File("src/main/java/com/example/profile_picture.png").toURI().toString()));



            StackPane profStackPane = new StackPane(); 
            profStackPane.getChildren().add(profilePicture);
            // profStackPane.setStyle("-fx-background-color: #ffffff;");


            // Username
            Label usernameLabel = new Label((String)this.curAccount.get("name"));
            usernameLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 24;"); // White text, size 24

            // Company ID
            Label companyIdLabel = new Label("Company ID: " + (String)this.curAccount.get("companyID"));
            companyIdLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 18;"); // White text, size 18
            companyIdLabel.setTranslateY(20);


            StackPane profDetStackPane = new StackPane();
            profDetStackPane.getChildren().addAll(companyIdLabel,usernameLabel);

            // profDetStackPane.setStyle("-fx-background-color: yellow;");

            StackPane profListStackPane = new StackPane();
            // profListStackPane.setStyle("-fx-background-color: green;");


            profileContentGrid.add(profStackPane, 0, 0);
            profileContentGrid.add(profDetStackPane, 1, 0);
            profileContentGrid.add(profListStackPane, 0, 1, 2, 1);


            profileContent.getChildren().add(profileContentGrid);
            

            // VBox profileBox = new VBox(10); // Spacing between nodes
            // profileBox.setAlignment(Pos.CENTER);

            
            
            // // Add nodes to the VBox
            // profileBox.getChildren().addAll(profilePicture, usernameLabel, companyIdLabel);

            // // Add the VBox to the profileContent StackPane
            // profileContent.getChildren().add(profileBox);

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

        //#endregion
        

        Scene scene = new Scene(grid, 900, 750);
        scene.setCamera(new PerspectiveCamera());
        scene.getStylesheets().add(getClass().getResource("/com/example/styles.css").toExternalForm());
        
        return scene;
    }
    //#endregion


    //#region Python 
    public static void createGateWayServer() {
        System.out.println("GateWay Server Connected");
        GatewayServer gatewayServer = new GatewayServer(new mainpageTest(objProfileInstance));
        gatewayServer.start();
    }
    //#endregion 


    //#region Stock Values and Simulation Updates
    public void updateStockPane(String JSONdata) {
        System.out.println("Updating Stock Pane");
        lineGraphRef.updateStockValue(JSONdata);
    } 

    //#endregion


    public static void main(String[] args) {
        launch(args);
    }
}