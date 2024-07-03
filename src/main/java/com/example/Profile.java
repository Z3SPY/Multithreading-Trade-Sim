package com.example;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import java.util.ArrayList; 

public class Profile {
    public String UserName;
    public String CompanyID;
    public Object stocks;
    public Float balance;

    public Profile(String userName, String compID, Object stockArray, Float accBal) {
        this.UserName = userName;
        this.CompanyID = compID;
        this.stocks = stockArray;
        this.balance = accBal;

        System.out.println("Stocks: " + stockArray);
        System.out.println("Balance: " + accBal);

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
        profileData.put("balance", this.balance);
        return profileData;
    }
}