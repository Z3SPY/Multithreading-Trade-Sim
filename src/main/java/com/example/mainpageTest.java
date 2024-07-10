package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.Random;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
import javafx.util.Duration;
import py4j.GatewayServer;

public class mainpageTest extends Application {
    private static GatewayServer gatewayServer = null;
    public static JavaFXLineGraph lineGraphRef;
    public static StackPane lineGraphStackPane;
    public static Label balanceLabel;
    private static Label alertNewsText;
    public static String curStockName;
    public static Float balance;

    //Updates 

    public static StackPane square1;
    public static StackPane square2;
    public static StackPane square3;

    public static Label r1Abbrv;
    public static Label r1Prc;
    public static Label r1Fluc;
    public static Label r2Abbrv;
    public static Label r2Prc;
    public static Label r2Fluc;
    public static Label r3Abbrv;
    public static Label r3Prc;
    public static Label r3Fluc;

    // Leaderboard
    public static Label p1N;
    public static Label p2N;
    public static Label p3N;
    public static Label p1S;
    public static Label p2S;
    public static Label p3S;

    public static Label priceLabelOutput; 
    Label ctgrLabelOutput;
    Label subTitleTradeDet;

    //private Py4JGatewayServer py4jServer;

    private TextField inputField;
    public static FastClock fastClock;


    public static ListView<String> stocksListView;


    public Map<String, Object> curAccount;
    public static Profile objProfileInstance;
    public mainpageTest(Profile profileInstance) {
        mainpageTest.objProfileInstance = profileInstance;
        this.curAccount = profileInstance.getProfileData();
        System.out.println(this.curAccount.get("name"));   
        System.out.println(this.curAccount.get("stocks"));   

    }
    

    

    // JAVA TO PYTHON 
    static List<ProfileInterface> listeners = new ArrayList<>();

    // Registers all available Interfaces
    public void registerListener(ProfileInterface listener) {
        listeners.add(listener);
    }


    public void leaderBoardListener() {
        for (ProfileInterface listener : listeners) {
            
            listener.updateLeader();

        }
    }

    public void notifyAllListeners(String qtyInput, Boolean btnState, String stockName) {

        for (ProfileInterface listener : listeners) {
            System.out.println(this.curAccount);
            System.out.println("Notify ");

        
            //Create a Json to send
            Gson gson = new Gson(); 
            String json = gson.toJson(this.curAccount); 
        

            // BUYING AND SELLING HAPPENS IN THIS ONE LINE
            Object returnValue = listener.notify(this, json, btnState, Integer.parseInt(qtyInput), stockName);
            System.out.println(returnValue);

            System.out.println(returnValue);


            if (returnValue != null) {

                try {
                    // Parse the JSON string into the ReturnData object
                    String jsonString = (String) returnValue;
                    ReturnData returnData = gson.fromJson(jsonString, ReturnData.class);
                    
                    // Access the parsed data
                    Map<String, Float> portfolio = (Map<String, Float>) returnData.getPortfolio();
                    double balance = returnData.getBalance();

                    // Print the values
                    System.out.println("Portfolio: " + portfolio);
                    System.out.println("Balance: " + balance);

                    // Example usage: updating curAccount with new data if necessary
                    mainpageTest.objProfileInstance.updateProfileData((float) balance, portfolio);
                    updateWalletBalance((float) balance);
                    this.curAccount = mainpageTest.objProfileInstance.getProfileData();

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
             
          
                
            
                
            }
            



        }
    }

    public void updateWalletBalance(Number newBal) {
        balance = newBal.floatValue();
        balanceLabel.setText(String.format("BALANCE: %.2f $HK", balance));
    }



    private String[] formatStocks(Map<String, Float> stocks) {
        return stocks.entrySet().stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue() + " units")
                .toArray(String[]::new);
    }

    

   


    @Override
    public void start(Stage mainStage) {
        createGateWayServer(); // Creates a gateway for python to send data
        
        balance = (Float) this.curAccount.get("balance");

        
        //Could be a problem?
        if (lineGraphRef == null) {
            lineGraphRef = new JavaFXLineGraph();
            lineGraphStackPane = lineGraphRef.createStackPane();

        }
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

                subTitleTradeDet.setText("ITEM: " + abbreviation + " | " + category);
                curStockName = category;
                ctgrLabelOutput.setText(type);
                priceLabelOutput.setText(Double.toString((double) Math.round(((double) newStock.getData().get(newStock.getData().size() - 1).getYValue() * 10000)) / 10000));
            }

            System.out.println(curStockName);

            
        });
        


        Scene mainScene = createMainScene(mainStage);
        

        mainScene.getStylesheets().add(this.getClass().getResource("chart.css").toExternalForm());
        mainStage.setTitle("ISTO SYSTEM");
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        mainStage.show();

        new Thread(() -> runPythonScript("src\\main\\java\\com\\example\\simTest.py")).start();
    }

    private void runPythonScript(String scriptPath) {
        try {
            // Create a process builder for running the Python script
            ProcessBuilder pb = new ProcessBuilder("python", scriptPath);
            pb.redirectErrorStream(true);

            // Start the process
            Process process = pb.start();

            // Capture and print the output of the script
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Wait for the process to complete and check the exit code
            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    

    //#region MAIN SCENE FRONTEND
    public Scene createMainScene(Stage mainStage) {
        fastClock = new FastClock(this);
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
        titlePane.setStyle("-fx-background-color: #DC5F00;"); 
        titlePane.setTranslateX(30);

        Label titleLabelMain = new Label("ISTO SYSTEMS");
        titleLabelMain.setStyle("-fx-font-size: 20px;"); // Set the font size and text color
        titlePane.getChildren().add(titleLabelMain);

        grid.add(titlePane, 2, 0, 2, 1);

        //#endregion0

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
        alertNewsText = new Label("INTERSTELLAR UPDATES");
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
        
        //#region LOWER MIDDLE BEST STOCK LIST

        StackPane lowerMiddlePane = new StackPane();
        grid.add(lowerMiddlePane, 0, 3, 4, 1);


        GridPane lowMidGridPane = new GridPane();
        lowMidGridPane.setHgap(10);

        ColumnConstraints lowMidGridCol0 = new ColumnConstraints();
        lowMidGridCol0.setPercentWidth(32);
        ColumnConstraints lowMidGridCol1 = new ColumnConstraints();
        lowMidGridCol1.setPercentWidth(30);
        ColumnConstraints lowMidGridCol2 = new ColumnConstraints();
        lowMidGridCol2.setPercentWidth(40);

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


        Label topRect1 = new Label("   -{  Month   /      Day   /   Year }-");
        topRect1.setStyle("-fx-text-fill: #DC5F00; -fx-font-size: 12px;"); // Orange color
        topRect1.setTranslateY(3);


        VBox topSection = new VBox(1); 
        topSection.getChildren().addAll(topRect1);

        // Add sections to the GridPane
        dateGrid.setAlignment(Pos.CENTER);
        dateGrid.add(topSection, 0, 0);
        dateGrid.add(fastClock.getDatePane(), 0, 1);

        // Center the GridPane in the StackPane
        StackPane.setAlignment(dateGrid, Pos.CENTER);

        // Add the GridPane to the StackPane
        datePane.getChildren().add(dateGrid);

        // Add the datePane to the lowMidGridPane
        lowMidGridPane.add(datePane, 1, 0, 1, 1);
        //#endregion
        
        //#region TimePane
        StackPane timePane = new StackPane();

        timePane.getChildren().add(fastClock.getTimePane());
        timePane.setTranslateX(10);

        GridPane.setHgrow(timePane, Priority.NEVER);
        GridPane.setVgrow(timePane, Priority.NEVER);
        timePane.setMinSize(126, 25);
        timePane.setMaxSize(126, Double.MAX_VALUE);

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
        balanceLabel = new Label(String.format("BALANCE: %.2f $HK", balance));
        balanceLabel.setTranslateX(25);
        balanceLabel.setTranslateY(5);
        balanceLabel.setTextFill(Color.WHITE);
        balanceLabel.setFont(Font.font("Arial", 20)); // Adjust font and size as needed

        // Create the Cp ID label
        Label cpIdLabel = new Label("Cp ID: " + this.curAccount.get("companyID")); 
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
        //#endregion

        //#region UPDATING PANE */
        
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
        stockHBox.setTranslateX(7.5);
        
         // Square 1
         square1 = new StackPane();
         square1.setMinSize(75, 50);
         square1.setStyle("-fx-background-color: #DC5F00; -fx-border-color: #DC5F00; -fx-border-width: 2;");
 
         Label r1Abbrv = new Label("[SR]");
         r1Abbrv.setStyle("-fx-font-size: 20px;"); // Increase font size
 
         double flucValue1 = 0.02;
         Label r1Fluc = new Label("FLC: " + String.format("%.4f", flucValue1)); // 4 decimal places
         
         double prcValue1 = 23323.00;
         Label r1Prc = new Label("PRC: " + String.format("%.2f", prcValue1)); // 2 decimal places
 
         VBox square1Vbox = new VBox();
         square1Vbox.setTranslateX(10);
         square1Vbox.getChildren().addAll(r1Abbrv, r1Fluc, r1Prc);
 
         square1Vbox.prefWidthProperty().bind(square1.widthProperty());
         square1Vbox.prefHeightProperty().bind(square1.heightProperty());
 
         square1.getChildren().add(square1Vbox);
 
         // Square 2
         square2 = new StackPane();
         square2.setMinSize(75, 50);
         square2.setStyle("-fx-background-color: #DC5F00; -fx-border-color: #DC5F00; -fx-border-width: 2;");
 
         Label r2Abbrv = new Label("[SQ]");
         r2Abbrv.setStyle("-fx-font-size: 20px;"); // Increase font size
 
         double flucValue2 = 0.035;
         Label r2Fluc = new Label("FLC: " + String.format("%.4f", flucValue2)); // 4 decimal places
         
         double prcValue2 = 19876.50;
         Label r2Prc = new Label("PRC: " + String.format("%.2f", prcValue2)); // 2 decimal places
 
         VBox square2Vbox = new VBox();
         square2Vbox.setTranslateX(10);
         square2Vbox.getChildren().addAll(r2Abbrv, r2Fluc, r2Prc);
 
         square2Vbox.prefWidthProperty().bind(square2.widthProperty());
         square2Vbox.prefHeightProperty().bind(square2.heightProperty());
 
         square2.getChildren().add(square2Vbox);
 
         // Square 3
         square3 = new StackPane();
         square3.setMinSize(75, 50);
         square3.setStyle("-fx-background-color: #DC5F00; -fx-border-color: #DC5F00; -fx-border-width: 2;");
 
         Label r3Abbrv = new Label("[RT]");
         r3Abbrv.setStyle("-fx-font-size: 20px;"); // Increase font size
 
         double flucValue3 = 0.025;
         Label r3Fluc = new Label("FLC: " + String.format("%.4f", flucValue3)); // 4 decimal places
         
         double prcValue3 = 25500.75;
         Label r3Prc = new Label("PRC: " + String.format("%.2f", prcValue3)); // 2 decimal places
 
         VBox square3Vbox = new VBox();
         square3Vbox.setTranslateX(10);
         square3Vbox.getChildren().addAll(r3Abbrv, r3Fluc, r3Prc);
 
         square3Vbox.prefWidthProperty().bind(square3.widthProperty());
         square3Vbox.prefHeightProperty().bind(square3.heightProperty());
 
         square3.getChildren().add(square3Vbox);


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
        subTitleTradeDet = new Label("Item:");
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
        ctgrLabelOutput = new Label("");
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
        priceLabelOutput = new Label("");
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


        inputField = new TextField();
        inputField.setPromptText("Qty");
        inputField.setAlignment(Pos.CENTER);
        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                inputField.setText(newValue.replaceAll("[^\\d]", ""));
            }

            if (inputField.getText().length() > 3) {
                String limitedText = inputField.getText().substring(0, 5);
                inputField.setText(limitedText);
            }
        });

        //Should Have only one button Buy or sell
        Button buySellButton = new Button("Buy");
        buySellButton.getStyleClass().add("buy-sell-btn");
        inputField.getStyleClass().add("input-field");
        inputField.setTranslateY(-5);
        

        // Make it call Python

        buySellButton.setOnAction(event -> {

            System.out.println("PROCESSING " + curStockName);
            System.out.println(ctgrLabelOutput.isVisible());

            if (ctgrStackLabelOutput.isVisible() == true) {
                if (subTitleTradeDet.getText().isEmpty() || inputField.getText().isEmpty()) {
                    showAlert(AlertType.ERROR, "Transaction", "Missing Stock Detail or Quantity");
                } else {
                    if (marketButton.getStyleClass().contains("button-selected")) {
                        System.out.println("Buy button clicked");
                        notifyAllListeners(inputField.getText(), false, curStockName);
                        // Add your buy functionality here
                    }
                }
            } else {
                if (profileButton.getStyleClass().contains("button-selected") && !inputField.getText().isEmpty()) {
                    System.out.println("Sell button clicked");
                    stocksListView.getItems().clear();

                    notifyAllListeners(inputField.getText(), true, curStockName);

                    // Update the ListView
                    ObservableList<String> items = FXCollections.observableArrayList(formatStocks(mainpageTest.objProfileInstance.getStocks()));
                    stocksListView.setItems(items);

                    // Add your sell functionality here
                } else {
                    showAlert(AlertType.ERROR, "Transaction", "Missing Stock Amount");
                }
            }
             
            
            if (subTitleTradeDet.getText() == "" || inputField.getText() == "") {
                showAlert(AlertType.ERROR, "Transaction", "Missing Quantity or Havent Selected Stock.");
            }
        });



        tradeDetPaneGrid.add(buySellButton, 1, 0, 1, 1);
        tradeDetPaneGrid.add(inputField, 1, 1, 1, 1);


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
        leaderGridRow1.setPercentHeight(23.5);
        RowConstraints leaderGridRow2 = new RowConstraints();
        leaderGridRow2.setPercentHeight(23.3);
        RowConstraints leaderGridRow3 = new RowConstraints();
        leaderGridRow3.setPercentHeight(23.3);
        RowConstraints leaderGridRow4 = new RowConstraints();
        leaderGridRow4.setPercentHeight(5);

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
        p1N = new Label("JOSHUA");
        p1N.setStyle("-fx-text-color: #1E1E1E;");
        StackPane plyr1Status = new StackPane();
        plyr1Status.setStyle("-fx-border-color: #DC5F00;");
        p1S = new Label("Current Balance: 1000 \nPortfolio Value: 1000");
        p1S.setStyle("-fx-text-fill: #DC5F00");
        

        plyr1Name.getChildren().addAll(p1N);
        plyr1Status.getChildren().addAll(p1S);

        StackPane plyr2Name = new StackPane();
        plyr2Name.setStyle("-fx-border-color: #1E1E1E; -fx-background-color:  #DC5F00; ");
        p2N = new Label("EMMA");
        p2N.setStyle("-fx-text-color: #1E1E1E;");
        StackPane plyr2Status = new StackPane();
        plyr2Status.setStyle("-fx-border-color: #DC5F00;");
        p2S = new Label("Current Balance: 1000 \nPortfolio Value: 1000");
        p2S.setStyle("-fx-text-fill: #DC5F00");


        plyr2Name.getChildren().addAll(p2N);
        plyr2Status.getChildren().addAll(p2S);

        StackPane plyr3Name = new StackPane();
        plyr3Name.setStyle("-fx-border-color: #1E1E1E; -fx-background-color:  #DC5F00; ");
        p3N = new Label("LIAM");
        p3N.setStyle("-fx-text-color: #1E1E1E;");
        StackPane plyr3Status = new StackPane();
        plyr3Status.setStyle("-fx-border-color: #DC5F00;");
        p3S = new Label("Current Balance: 1000 \nPortfolio Value: 1000");
        p3S.setStyle("-fx-text-fill: #DC5F00");


        plyr3Name.getChildren().addAll(p3N);
        plyr3Status.getChildren().addAll(p3S);

        plyr1Status.setAlignment(Pos.TOP_LEFT);
        plyr2Status.setAlignment(Pos.TOP_LEFT);
        plyr3Status.setAlignment(Pos.TOP_LEFT);

        p1S.setTranslateX(10);
        p2S.setTranslateX(10);
        p3S.setTranslateX(10);




        leaderGrid.add(leaderBoardTitle, 0, 0, 2, 1);
        leaderGrid.add(plyr1Name, 0, 1, 1, 1);
        leaderGrid.add(plyr1Status, 1, 1, 1, 1);
        leaderGrid.add(plyr2Name, 0, 2, 1, 1);
        leaderGrid.add(plyr2Status, 1, 2, 1, 1);
        leaderGrid.add(plyr3Name, 0, 3, 1, 1);
        leaderGrid.add(plyr3Status, 1, 3, 1, 1);




        infoGridPane.add(topLdrPane, 0, 2, 1, 2);



        // Robot Design 
       /*  StackPane roboFace = new StackPane();
        Region roboDesign = new Region();
        roboDesign.setStyle("-fx-background-color:  #DC5F00;");

        leaderGrid.add(roboFace,0, 4, 1, 1);
        leaderGrid.add(roboDesign,1, 4, 1, 1);*/

        

        infoPane.getChildren().addAll(infoGridPane);


        //BOTTOM RIGHT VIS PANE END 
        //#endregion
        

        //#region logout
        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("logout-btn");
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setTranslateX(40);
        logoutButton.setOnAction(e -> {
            try {
                this.curAccount = null;
                login loginPage = new login();
                loginPage.start(mainStage);


                //Saves Profile Changes
                if (mainpageTest.objProfileInstance != null){
                    mainpageTest.objProfileInstance.updateDataBase();
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox logoutPane = new HBox(logoutButton);
        grid.add(logoutPane, 5, 0, 1, 1);
        ////#endregion


        //#region BUTTON CLICK INTERACTIONS
        // Event handling to toggle button selection
        marketButton.setOnAction(event -> {
            marketButton.getStyleClass().clear(); // Clear existing styles
            marketButton.getStyleClass().addAll( "button-selected");
            marketPane.getStyleClass().add("mainpage-cellStyle");

            //Enable
            ctgryStackLabel.setVisible(true);
            priceStackLabel.setVisible(true);
            ctgrStackLabelOutput.setVisible(true);
            priceStackLabelOutput.setVisible(true);

             //Button Text
             buySellButton.setText("Buy");

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


            //Enable
            ctgryStackLabel.setVisible(true);
            priceStackLabel.setVisible(true);
            ctgrStackLabelOutput.setVisible(true);
            priceStackLabelOutput.setVisible(true);

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

            //Button Text
            buySellButton.setText("Sell");


            //Disable Categories and Prices Label
            ctgryStackLabel.setVisible(false);
            priceStackLabel.setVisible(false);
            ctgrStackLabelOutput.setVisible(false);
            priceStackLabelOutput.setVisible(false);
            
                    
            // Create colored content for profileButton
            StackPane profileContent = new StackPane();
            profileContent.setStyle("-fx-background-color: #1E1E1E;"); // Set background color
        
            GridPane profileContentGrid = new GridPane();
            profileContentGrid.setVgap(5);

            ColumnConstraints profileContentGridCol0 = new ColumnConstraints();
            profileContentGridCol0.setPercentWidth(40);
            ColumnConstraints profileContentGridCol1 = new ColumnConstraints();
            profileContentGridCol1.setPercentWidth(60);

            profileContentGrid.getColumnConstraints().addAll(profileContentGridCol0, profileContentGridCol1);

            RowConstraints profileContentGridRow0 = new RowConstraints();
            profileContentGridRow0.setPercentHeight(50);
            RowConstraints profileContentGridRow1 = new RowConstraints();
            profileContentGridRow1.setPercentHeight(50);

            profileContentGrid.getRowConstraints().addAll(profileContentGridRow0, profileContentGridRow1);


            ImageView profilePicture = new ImageView();
            profilePicture.setFitWidth(180);
            profilePicture.setFitHeight(180);
            profilePicture.setImage(new Image(new File("src/main/java/com/example/profile_picture.png").toURI().toString()));
            
            GridPane.setHgrow(profileContent, Priority.NEVER);
            GridPane.setVgrow(profileContent, Priority.NEVER);
            profileContent.setMinSize(250, 10);
            profileContent.setMaxSize(600, Double.MAX_VALUE);


            StackPane profStackPane = new StackPane(); 
            profStackPane.getChildren().add(profilePicture);
            // profStackPane.setStyle("-fx-background-color: #ffffff;");


            // Username
            Label usernameLabel = new Label((String)this.curAccount.get("name"));
            usernameLabel.setStyle("-fx-text-fill: #DC5F00; -fx-font-size: 40;"); // Set text color

            // Company ID
            Label companyIdLabel = new Label("Company ID: " + (String)this.curAccount.get("companyID"));
            companyIdLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 18;"); // White text, size 18
            
            // Total Investment
            Label userInvestment = new Label("Investment Total: [placeholder]");
            userInvestment.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 18;"); // White text, size 18

            // Current Account Cash
            Label userCurrentCash = new Label("Current Account Cash: "+ balance);
            userCurrentCash.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 18;"); // White text, size 18

            System.out.println(userCurrentCash);


            GridPane profileDetailsGrid = new GridPane();
            profileDetailsGrid.setVgap(2);
            profileDetailsGrid.add(usernameLabel, 0, 0);
            profileDetailsGrid.add(companyIdLabel, 0, 1);
            profileDetailsGrid.add(userInvestment, 0, 2);
            profileDetailsGrid.add(userCurrentCash, 0, 3);


            // List view
            StackPane profListStackPane = new StackPane();
            stocksListView = new ListView<>();
            stocksListView.getItems().addAll(formatStocks(mainpageTest.objProfileInstance.getStocks()));

            stocksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    System.out.println("Selected Stock: " + newValue);

                    String regex = "^(.*?) = \\d+\\.0 units";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(newValue);

                    // Check if the matcher finds a match
                    if (matcher.find()) {
                        // Extract the company ID
                        String stockVal = matcher.group(1); // Group 1 captures the (\d+) part
                        curStockName = stockVal;
                        subTitleTradeDet.setText("ITEM: " + stockVal);
                    } else {
                        System.out.println("No stock found in input.");
                    }


                   
                    
                    
                }   
            });

            profListStackPane.getChildren().add(stocksListView);

            profileContentGrid.add(profStackPane, 0, 0);
            profileContentGrid.add(profileDetailsGrid, 1, 0);
            profileContentGrid.add(profListStackPane, 0, 1, 2, 1);


            profileContent.getChildren().add(profileContentGrid);
            

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
        
        StackPane mainStackpane = new StackPane();
        mainStackpane.getChildren().add(grid);

        Scene scene = new Scene(mainStackpane, 900, 850);
        scene.setCamera(new PerspectiveCamera());
        scene.getStylesheets().add(getClass().getResource("/com/example/styles.css").toExternalForm());
        
        createOverlay(scene, mainStackpane);


        return scene;
    }
    //#endregion

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

      //#region Python 
    public static void createGateWayServer() {
        System.out.println("GateWay Server Connected");

        if (gatewayServer == null) {
            gatewayServer = new GatewayServer(new mainpageTest(objProfileInstance));
            gatewayServer.start();
        }
        
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        // Shutdown Py4J server when JavaFX application stops
        gatewayServer.shutdown();
    }
    //#endregion 


    //#region Stock Values and Simulation Updates
    public void updateStockPane(String JSONdata) {
        System.out.println("Updating Stock Pane");

        // Initialize Gson
        Gson gson = new Gson();

        // Parse the JSON data
        JsonArray jsonArray = JsonParser.parseString(JSONdata).getAsJsonArray();

        // Select 3 random stocks from the JSON array
        Random random = new Random();
        int[] randomIndices = new int[3];
        for (int i = 0; i < 3; i++) {
            randomIndices[i] = random.nextInt(jsonArray.size());
        }

        // Update UI on JavaFX Application Thread
        Platform.runLater(() -> {
            for (int i = 0; i < 3; i++) {
                JsonObject stockEntry = jsonArray.get(randomIndices[i]).getAsJsonObject();

                // Extract stockName
                String stockName = stockEntry.get("stockName").getAsString();
                // Extract stockPrice
                double stockPrice = stockEntry.get("stockPrice").getAsDouble();
                // Extract price_fluctuation
                double priceFluctuation = stockEntry.get("price_fluctuation").getAsDouble();

                // Convert stock name to abbreviation (example method)
                String stockAbbreviation = convertToAbbreviation(stockName);

                // Update respective square and labels
                if (i == 0) {
                    updateSquare(square1, stockAbbreviation, priceFluctuation, stockPrice);
                } else if (i == 1) {
                    updateSquare(square2, stockAbbreviation, priceFluctuation, stockPrice);
                } else if (i == 2) {
                    updateSquare(square3, stockAbbreviation, priceFluctuation, stockPrice);
                }

                System.out.println("Stock Name: " + stockName + " [" + stockAbbreviation + "]");
                System.out.println("Stock Price: " + stockPrice);
                System.out.println("Price Fluctuation: " + priceFluctuation);
            }

            // Update any other UI components
            lineGraphRef.updateStockValue(JSONdata);

            Double curDouble = lineGraphRef.getSpecificStockValue(curStockName);
            System.out.println(curDouble);

            // Update UI component on JavaFX Application Thread
            priceLabelOutput.setText(Double.toString((double) Math.round((curDouble * 10000)) / 10000));
        });
    }

    // Example method to update a square with labels
    private void updateSquare(StackPane square, String abbreviation, double fluctuation, double price) {
        // Clear previous content if any
        square.getChildren().clear();
        square.setMinSize(85, 75);
        square.setMaxSize(85, 75);

        // Create labels
        Label abbrvLabel = new Label("[" + abbreviation + "]");
        Label flucLabel = new Label("FLC " + String.format("%.4f", fluctuation));
        Label prcLabel = new Label("PRC " + String.format("%.2f", price));

        // Style labels as needed
        abbrvLabel.setStyle("-fx-font-size: 20px;");
        flucLabel.setStyle("-fx-font-size: 14px;");
        prcLabel.setStyle("-fx-font-size: 14px;");

        // Create VBox to hold labels
        VBox vbox = new VBox();
        vbox.getChildren().addAll(abbrvLabel, flucLabel, prcLabel);

        // Bind VBox size to square size
        vbox.prefWidthProperty().bind(square.widthProperty());
        vbox.prefHeightProperty().bind(square.heightProperty());

        // Add VBox to square
        square.getChildren().add(vbox);
    }

    // Example method to convert stock name to abbreviation
    private String convertToAbbreviation(String stockName) {
        String[] words = stockName.split(" ");
        StringBuilder abbreviation = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                abbreviation.append(word.charAt(0));
            }
        }

        return abbreviation.toString().toUpperCase();
    } 

    //#endregion
    public static boolean endOfDay = false;

    public Boolean GetLeaderBoardFromPy(String list) {
        Gson gson = new Gson();
        Object[] leaderboard = gson.fromJson(list, Object[].class);
        String json = gson.toJson(leaderboard);
        
        if (endOfDay == false) {
            //Show Top 3 
            for (int i = 0; i < 3; i++) {
                Map<String, Object> map = (Map<String, Object>) leaderboard[i];
                final String name = (String) map.get("name");
                final double amount = (double) map.get("amount");
                final double cashBalance = (double) map.get("cash_balance");
                final double totalInvestment = (double) map.get("total_investment");
                final int index = i; // Create a final variable

                
                System.out.println("Name: " + name);
                System.out.println("Amount: " + amount);
                System.out.println("Cash Balance: " + cashBalance);
                System.out.println("Total Investment: " + totalInvestment);
                System.out.println();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (index == 0) {
                            p1N.setText(name);
                            p1S.setText(String.format("Current Employee Balance: %.2f $HK\nTotal Investments: %.2f $HK", cashBalance, totalInvestment));
                        } else if (index == 1) {
                            p2N.setText(name);
                            p2S.setText(String.format("Current Employee Balance: %.2f $HK\nTotal Investments: %.2f $HK", cashBalance, totalInvestment));
                        } else if (index == 2) {
                            p3N.setText(name);
                            p3S.setText(String.format("Current Employee Balance: %.2f $HK\nTotal Investments: %.2f $HK", cashBalance, totalInvestment));
                        }
                    }
                });
            }

            //Show all Top 10 Players 
            // Show all Top 10 Players in ListView
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    listView = (ListView<String>) overlay.getChildren().get(0);
                    listView.getItems().clear(); // Clear existing items

                    for (int i = 0; i < leaderboard.length && i < 10; i++) {
                        Map<String, Object> map = (Map<String, Object>) leaderboard[i];
                        String name = (String) map.get("name");
                        double cashBalance = (double) map.get("cash_balance");
                        double totalInvestment = (double) map.get("total_investment");

                        listView.getItems().add(String.format("Employee: %s \nCash Balance: %.2f $HK \nTotal Investments: %.2f $HK", name, cashBalance, totalInvestment));
                    }
                }
            });
        }


        return true;
    }

    public void newDayFunction(String data) {
        System.out.println("Passed data "+ data);
        leaderBoardListener();
        simulateRoundEnd();

    }
    public static ListView<String> listView;
    public static VBox overlay;
    private TranslateTransition transitionUp;
    private TranslateTransition transitionDown;
    Label leaderBoardTitleLabel;

    private void createOverlay(Scene scene, StackPane stackPane) {
        overlay = new VBox(20);
        overlay.setStyle("-fx-background-color: black; -fx-alignment: center; -fx-padding: 10px 10px;");
        overlay.setPrefSize(scene.getWidth() * .5, scene.getHeight());
    
        // Add Performance Today title with inline CSS
        /*leaderBoardTitleLabel = new Label("Performance Today");
        leaderBoardTitleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;"); // Inline CSS for font size and text color
        overlay.getChildren().add(leaderBoardTitleLabel); // Add ListView directly to overlay*/

        // Create a ListView for leaderboard
        listView = new ListView<>();
        overlay.getChildren().add(listView); // Add ListView directly to overlay
    
        Button closeButton = new Button("Close Daily Performance List");
        closeButton.setOnAction(e -> closeOverlay());
    
        overlay.getChildren().add(closeButton); // Add close button
    
        stackPane.getChildren().add(overlay);
    
        // Initialize the overlay's position (off-screen at the bottom)
        overlay.setTranslateY(scene.getHeight());
    
        // Set up the transitions
        transitionUp = new TranslateTransition(Duration.seconds(1), overlay);
        transitionUp.setToY(0);
    
        transitionDown = new TranslateTransition(Duration.seconds(1), overlay);
        transitionDown.setToY(scene.getHeight());
    }
    
    

    
    private void simulateRoundEnd() {
        transitionUp.play();
        fastClock.pauseClock();
        endOfDay = true;
    }

    private void closeOverlay() {
        transitionDown.play();
        fastClock.resumeClock();
        endOfDay = false;
    }

    // Method to update the alert text
    public void updateAlertText(String event) {
        Platform.runLater(() -> {
            alertNewsText.setText(event);
            
            // Create a Timeline for blinking effect
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> alertNewsText.setVisible(false)),
                new KeyFrame(Duration.seconds(1), e -> alertNewsText.setVisible(true))
            );
            timeline.setCycleCount(4);  // Blinking 2 times (4 keyframes)
            timeline.play();

            // Revert to default text after blinking
            timeline.setOnFinished(e -> alertNewsText.setText("INTERSTELLAR UPDATES"));
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}