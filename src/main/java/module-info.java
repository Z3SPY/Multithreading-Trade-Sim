module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires py4j; 
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires com.google.gson;

    opens com.example to javafx.fxml, gson; // Open com.example package to Gson and JavaFX FXML
    exports com.example;
}
