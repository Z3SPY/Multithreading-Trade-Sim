module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires py4j;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires com.google.gson;
    

    opens com.example to javafx.fxml, com.google.gson; // Open com.example package to JavaFX FXML and Gson
    exports com.example;
    
}
