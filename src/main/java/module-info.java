module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires py4j; 
    

    opens com.example to javafx.fxml;
    exports com.example;
}
