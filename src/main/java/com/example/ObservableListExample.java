package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;

public class ObservableListExample {

    public static void main(String[] args) {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        // Add elements to the list
        observableList.add("Apple");
        observableList.add("Banana");
        observableList.add("Orange");

        // Register a ListChangeListener
        observableList.addListener((ListChangeListener.Change<? extends String> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    System.out.println("Added: " + change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    System.out.println("Removed: " + change.getRemoved());
                }
                // Handle other change types if needed (replacing elements, etc.)
            }
        });

        // Modify the list (will trigger change notifications)
        observableList.add("Grapes");
        observableList.remove("Banana");

        // Output the final list
        System.out.println("Final List: " + observableList);
    }
}
