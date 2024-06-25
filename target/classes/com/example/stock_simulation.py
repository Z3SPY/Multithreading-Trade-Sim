import time
import numpy as np
import random
from py4j.java_gateway import JavaGateway

class Player:
    def __init__(self, name, amount):
        self.transaction_cost = 0.0075
        self.amount = amount
        self.name = name
        
        self.portfolio = {}
        self.uncertainty = {}

        self.cash_balance = amount
        self.total_investment = 0.0
        self.uncertainty_threshold = 1.0
    
    def simulate(self, stockList):
        if len(self.portfolio) >= 1:
            self.increase_uncertainty()
            self.check_uncertainty_and_sell(stockList)

        if len(self.portfolio) <= 3:
            choice = stockList[random.randint(0, len(stockList) - 1)]
            if self.buy_stock(choice, random.randint(1, 20)):
                choice = stockList[random.randint(0, len(stockList) - 1)]
            if not self.buy_stock(choice, random.randint(1, 20)):
                return
    
    def buy_stock(self, stock, quantity):
        total_cost = stock.stockPrice * quantity
        if self.cash_balance >= total_cost:
            transaction_fee = total_cost * self.transaction_cost
            total_cost += transaction_fee
            self.cash_balance -= total_cost
            if stock.stockName in self.portfolio:
                self.portfolio[stock.stockName] += quantity
            else:
                self.portfolio[stock.stockName] = quantity
                self.uncertainty[stock.stockName] = random.uniform(0, 0.2)
            self.total_investment += total_cost
            return True
        else:
            return False
    
    def sell_stock(self, stock, quantity):
        if stock.stockName in self.portfolio and self.portfolio[stock.stockName] >= quantity:
            sale_amount = stock.stockPrice * quantity
            transaction_fee = sale_amount * self.transaction_cost
            sale_amount -= transaction_fee
            self.cash_balance += sale_amount
            self.portfolio[stock.stockName] -= quantity
            self.total_investment -= (stock.stockPrice * quantity)
            if self.portfolio[stock.stockName] == 0:
                del self.portfolio[stock.stockName]
                del self.uncertainty[stock.stockName]
            return True
        else:
            return False
    
    def increase_uncertainty(self):
        for stock_name in self.uncertainty:
            self.uncertainty[stock_name] += random.uniform(0.01, 0.1)

    def check_uncertainty_and_sell(self, stockList):
        for stock_name in list(self.uncertainty.keys()):
            if self.uncertainty[stock_name] >= self.uncertainty_threshold:
                for stock in stockList:
                    if stock.stockName == stock_name:
                        self.sell_stock(stock, self.portfolio[stock_name])
                        break

class Events:
    def __init__(self):
        self.event = "DEFAULT"

    def passStockData(self, stock_list):
        gateway = JavaGateway()
        java_app = gateway.entry_point

        for stock in stock_list:
            if stock.stockName == "Space Rocks": 
                java_app.updateStockValue(stock.stockName, stock.stockPrice)

        gateway.close()

class Stock:
    def __init__(self, name, price, category):
        self.stockName = name
        self.stockPrice = price
        self.price_fluctuation_base = 0.02
        self.price_fluctuation = 0.02
        self.category = category

    def display(self):
        return f"{self.stockName} current price: ${self.stockPrice:.2f}"
    
    def simulate_stock_price(self, current_price):
        change_percent = np.random.normal(-self.price_fluctuation / 200, self.price_fluctuation)
        new_price = current_price * (1 + change_percent)
        # Ensure the price doesn't drop below a reasonable minimum
        if new_price < 1.0:
            new_price = 1.0 + abs(change_percent)
        self.stockPrice = new_price
        return round(self.stockPrice, 2)

def main():
    player = Player("John Doe", 10000)
    myStock1 = Stock("Space Rocks", 100, "INFRA")
    myStock2 = Stock("Hyper Accelerators", 90, "MILITARY")
    myStock3 = Stock("Tiki Torches", 120, "COMMERCE")
    myStock4 = Stock("Space Worm Jelly", 2000, "COMMERCE")
    myStock5 = Stock("Stone Pick Axe", 10, "INFRA")
    
    EventSystem = Events()

    count = 0
    tick_limit = 40
    Space_NasDaq = [myStock1, myStock2, myStock3, myStock4, myStock5]

    while True:
        for stock in Space_NasDaq:
            stock.simulate_stock_price(stock.stockPrice)

        player.simulate(Space_NasDaq)
        time.sleep(1)
        
        count += 1
        
        if count % 3 == 0:
            EventSystem.passStockData(Space_NasDaq)
        if count >= tick_limit:
            count = 0

if __name__ == "__main__":
    main()
