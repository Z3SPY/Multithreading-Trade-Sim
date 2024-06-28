package com.example;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import java.util.ArrayList; 

public class Profile {
    public String UserName;
    public String CompanyID;
    public ArrayList<DataObject> stocks;

    public Profile(String userName, String compID) {
        this.UserName = userName;
        this.CompanyID = compID;
        this.stocks = new ArrayList<>(); // Initialize the stocks ArrayList
    }

    // If Buy Or Sell Call This 
    public void updateStock() {
        MongoDBUtil mongoDBUtil = new MongoDBUtil();
        mongoDBUtil.updateStock(this.stocks, this.UserName);
        mongoDBUtil.close();
    }

    public Map<String, Object> getProfileData() {
        Map<String, Object> profileData = new HashMap<>();
        profileData.put("name", this.UserName);
        profileData.put("companyID", this.CompanyID);
        profileData.put("stocks", this.stocks);
        return profileData;
    }
}