package com.example;

public class DataObject {

    /* NOTE PROBLEMS WITH PRIVATE CLASS CALL */
    public String stockName;
    public double stockPrice;
    public double price_fluctuation_base;
    public double price_fluctuation;
    public String category;

    // Constructors
    public DataObject() {
    }

    public DataObject(String stockName, double stockPrice, double price_fluctuation_base, double price_fluctuation, String category) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.price_fluctuation_base = price_fluctuation_base;
        this.price_fluctuation = price_fluctuation;
        this.category = category;
    }

    // Getters and Setters
    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public double getPrice_fluctuation_base() {
        return price_fluctuation_base;
    }

    public void setPrice_fluctuation_base(double price_fluctuation_base) {
        this.price_fluctuation_base = price_fluctuation_base;
    }

    public double getPrice_fluctuation() {
        return price_fluctuation;
    }

    public void setPrice_fluctuation(double price_fluctuation) {
        this.price_fluctuation = price_fluctuation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "DataObject{" +
                "stockName='" + stockName + '\'' +
                ", stockPrice=" + stockPrice +
                ", price_fluctuation_base=" + price_fluctuation_base +
                ", price_fluctuation=" + price_fluctuation +
                ", category='" + category + '\'' +
                '}';
    }
}
