package com.example;

import java.util.Random;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoDBUtil {

    private static final String CONNECTION_STRING = "mongodb+srv://testUser:test12345678@cluster0.kbewg3q.mongodb.net/sampleDB?authSource=admin&retryWrites=true&w=majority";
    private static final String DATABASE_NAME = "sampleDB";
    private static final String COLLECTION_NAME = "sampleCollections";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDBUtil() {
        mongoClient = MongoClients.create(CONNECTION_STRING);
        database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME);
    }

    public Document validateUser(String username, String password) {
        Document user = collection.find(Filters.eq("username", username)).first();
        if (user != null && user.getString("password").equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    public boolean addUser(String username, String password, String companyId) {
        if (isUsernameExists(username) || isCompanyIdExists(companyId)) {
            return false;
        }
        int uniqueId = generateUniqueRandomId();
        Document newUser = new Document("_id", uniqueId)
                .append("username", username)
                .append("password", password)
                .append("companyId", companyId)
                .append("Stocks", companyId);
        collection.insertOne(newUser);
        return true;
    }

    public boolean isUsernameExists(String username) {
        Document user = collection.find(Filters.eq("username", username)).first();
        return user != null;
    }

    public boolean isCompanyIdExists(String companyId) {
        Document company = collection.find(Filters.eq("companyId", companyId)).first();
        return company != null;
    }

    private int generateUniqueRandomId() {
        Random random = new Random();
        int randomId;
        boolean exists;
        do {
            randomId = 10000 + random.nextInt(90000); // Generates a 5-digit number
            Document idDocument = collection.find(Filters.eq("_id", randomId)).first();
            exists = idDocument != null;
        } while (exists);
        return randomId;
    }

    public void close() {
        mongoClient.close();
    }
}