package com.example;

import java.util.Map;

public class ReturnData {
    private Map<String, Float> portfolio;  // Adjust the type based on your data
    private double balance;

    // Getters and setters
    public Map<String, Float> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Map<String, Float> portfolio) {
        this.portfolio = portfolio;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
