import keyboard
import time
import numpy as np

class Player:
    def __init__(self, name, amount):
        self.transaction_cost = 0.0075  # Price for account transactions
        self.amount = amount
        self.name = name
        self.portfolio = {}  # Dictionary to track stocks in portfolio
        self.cash_balance = amount
        self.total_investment = 0.0
    
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
                self.portfolio[stock.stockName] = quantity
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
            return True
        else:
            return False

class Stock:
    def __init__(self, name, price):
        self.stockName = name
        self.stockPrice = price
        self.price_fluctuation = 0.02  # Define the price fluctuation range here

    def display(self):
        return f"{self.stockName} current price: ${self.stockPrice:.2f}"
    
    def simulate_stock_price(self, current_price):
        """Simulate the stock price fluctuation."""
        # Simulate a more realistic price change based on a normal distribution
        change_percent = np.random.normal(0, self.price_fluctuation)
        self.stockPrice = current_price * (1 + change_percent)
        return round(self.stockPrice, 2)

def main():
    player = Player("John Doe", 10000)
    myStock = Stock("Space Rocks", 100)
    stop_flag = False
    print("Press '1' to stop...")

    def on_press(event):
        nonlocal stop_flag
        if event.name == '1':
            stop_flag = True
    
    # Registering the on_press callback
    keyboard.on_press_key('1', on_press)
    
    while not stop_flag:
        current_price = myStock.simulate_stock_price(myStock.stockPrice)
        print(myStock.display())
        print(f"Cash balance: ${player.cash_balance:.2f}")
        print(f"Portfolio: {player.portfolio}")
        print()  # Print an empty line for spacing
        time.sleep(1)
    
    # Clean up: unregister the callback
    keyboard.unhook_all()

if __name__ == "__main__":
    main()
