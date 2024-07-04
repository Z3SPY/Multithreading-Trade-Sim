package com.example;

import java.util.HashMap;
import java.util.Map;

public class Profile {
    public String UserName;
    public String CompanyID;
    public Map<String, Float> stocks;
    public Float balance;

    public Profile(String userName, String compID, Map<String, Float> stockArray, Float accBal) {
        this.UserName = userName;
        this.CompanyID = compID;
        this.stocks = stockArray != null ? stockArray : new HashMap<>();
        this.balance = accBal;

        System.out.println("Stocks: " + stockArray);
        System.out.println("Balance: " + accBal);
    }

    // If Buy Or Sell Call This 
    public void updateDataBase() {
        MongoDBUtil mongoDBUtil = new MongoDBUtil();
        mongoDBUtil.updateStock(this.stocks, this.UserName);
        mongoDBUtil.updateBal(this.balance, UserName);
        mongoDBUtil.close();
    }

    public Map<String, Float> getStocks() {
        return this.stocks;
    }


    public void updateProfileData(Float balance, Map<String, Float> stock) {
        // Update balance
        this.balance = balance;
        System.out.println(this.balance);
    
        // Update stock
        this.stocks = stock;
        System.out.println("Current Stock of Profile: " + stock);
    
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
