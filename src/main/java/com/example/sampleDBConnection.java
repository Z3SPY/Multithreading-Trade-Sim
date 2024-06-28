package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

public class sampleDBConnection {

    public static void main(String[] args) {
        
        // Corrected connection string with authSource specified
        MongoClient client = MongoClients.create("mongodb+srv://testUser:test12345678@cluster0.kbewg3q.mongodb.net/sampleDB?authSource=admin&retryWrites=true&w=majority");
        
        MongoDatabase db = client.getDatabase("users");
        
        MongoCollection<Document> col = db.getCollection("sampleCollection");
        
        Document sampleDoc = new Document("_id", "1").append("name", "John Doe");
        
        InsertOneResult result = col.insertOne(sampleDoc);
        
        System.out.println("Document inserted successfully: " + result.getInsertedId());
    }
}
