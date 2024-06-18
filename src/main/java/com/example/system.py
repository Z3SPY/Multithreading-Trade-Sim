import time
import numpy as np
import random


class Player:
    def __init__(self, name, amount):
        self.transaction_cost = 0.0075  # Price for account transactions
        self.amount = amount
        self.name = name
        
        self.portfolio = {}  # Dictionary to track stocks in portfolio
        self.uncertainty = {}  # Dictionary to track stock uncertainty

        self.cash_balance = amount
        self.total_investment = 0.0
        self.uncertainty_threshold = 1.0  # Threshold at which the player decides to sell
    
    def simulate(self, stockList):
        print(self.portfolio)
        if len(self.portfolio) <= 3:
            choice = stockList[random.randint(0, len(stockList) - 1)]
            if self.buy_stock(choice, random.randint(1, 20)):
                choice = stockList[random.randint(0, len(stockList) - 1)]
                print("1")
            if not self.buy_stock(choice, random.randint(1, 20)):
                print("2")
                return
        
        # Increase uncertainty at a random rate for each stock
        self.increase_uncertainty()

        # Decide whether to sell based on uncertainty level
        self.check_uncertainty_and_sell(stockList)
    
    def buy_stock(self, stock, quantity):
        total_cost = stock.stockPrice * quantity
        if self.cash_balance >= total_cost:
            # Deduct transaction cost
            transaction_fee = total_cost * self.transaction_cost
            total_cost += transaction_fee
            # Update cash balance
            self.cash_balance -= total_cost
            # Add stock to portfolio
            if stock.stockName in self.portfolio:
                self.portfolio[stock.stockName] += quantity
            else:
                self.portfolio[stock.stockName] = quantity  # Dictionary Key = Value
                self.uncertainty[stock.stockName] = random.uniform(0, 0.2)  # If no value exists yet, assign a random uncertainty
            # Update total investment
            self.total_investment += total_cost
            return True
        else:
            return False
    
    def sell_stock(self, stock, quantity):
        if stock.stockName in self.portfolio and self.portfolio[stock.stockName] >= quantity:
            # Calculate sale amount
            sale_amount = stock.stockPrice * quantity

            # Deduct transaction cost
            transaction_fee = sale_amount * self.transaction_cost
            sale_amount -= transaction_fee

            # Update cash balance
            self.cash_balance += sale_amount

            # Reduce stock from portfolio
            self.portfolio[stock.stockName] -= quantity

            # Update total investment
            self.total_investment -= (stock.stockPrice * quantity)

            # Remove stock from portfolio if quantity becomes zero
            if self.portfolio[stock.stockName] == 0:
                del self.portfolio[stock.stockName]
                del self.uncertainty[stock.stockName]
            return True
        else:
            return False
    
    def sell_stocks(self, stockList):
        for stock in stockList:
            if stock.stockName in self.portfolio:
                quantity = self.portfolio[stock.stockName]
                self.sell_stock(stock, quantity)
    
    def increase_uncertainty(self):
        for stock_name in self.uncertainty:
            self.uncertainty[stock_name] += random.uniform(0.01, 0.1)
            print(f"Uncertainty level for {stock_name}: {self.uncertainty[stock_name]:.2f}")

    def check_uncertainty_and_sell(self, stockList):
        for stock_name in list(self.uncertainty.keys()):
            if self.uncertainty[stock_name] >= self.uncertainty_threshold:
                for stock in stockList:
                    if stock.stockName == stock_name:
                        self.sell_stock(stock, self.portfolio[stock_name])
                        break


class Stock:
    def __init__(self, name, price, category):
        self.stockName = name
        self.stockPrice = price
        self.price_fluctuation = 0.02  # Define the price fluctuation range here
        self.category = category

    def display(self):
        return f"{self.stockName} current price: ${self.stockPrice:.2f}"
    
    def simulate_stock_price(self, current_price):
        """Simulate the stock price fluctuation."""
        # Simulate a more realistic price change based on a normal distribution
        change_percent = np.random.normal(-self.price_fluctuation / 200, self.price_fluctuation)
        print(round(change_percent, 4))
        self.stockPrice = current_price * (1 + change_percent)
        return round(self.stockPrice, 2)

def main():
    player = Player("John Doe", 10000)
    myStock1 = Stock("Space Rocks", 100, "INFRA")
    myStock2 = Stock("Hyper Accelerators", 90, "MILITARY")
    myStock3 = Stock("Tiki Torches", 120, "COMMERCE")
    myStock4 = Stock("Space Worm Jelly", 2000, "COMMERCE")
    myStock5 = Stock("Stone Pick Axe", 10, "INFRA")

    Space_NasDaq = [myStock1, myStock2, myStock3, myStock4, myStock5]

    stop_flag = False
    print("Press '1' to stop...")

    """def on_press(event):
        nonlocal stop_flag
        if event.name == '.':
            stop_flag = True
    
    # Registering the on_press callback
    keyboard.on_press_key('.', on_press)"""
    
    while not stop_flag:
        for stocks in Space_NasDaq:
            stocks.simulate_stock_price(stocks.stockPrice)
            print(stocks.display())

        print(f"Cash balance: ${player.cash_balance:.2f}")
        print(f"Portfolio: {player.portfolio}")
        for stock_name, uncertainty in player.uncertainty.items():
            print(f"Uncertainty for {stock_name}: {uncertainty:.2f}")
        print()  # Print an empty line for spacing

        player.simulate(Space_NasDaq)
        time.sleep(1)
    

if __name__ == "__main__":
    main()
