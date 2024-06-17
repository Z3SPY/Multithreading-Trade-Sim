import pyautogui
import time
import numpy as np
import pandas as pd

# Initialize stock data
stock_price = 100  # Starting stock price
capital = 10000  # Starting capital
portfolio = 0  # Number of stocks owned
trade_history = []  # Log of trades

# Parameters
price_fluctuation = 0.02  # Max percentage change in stock price per tick
tick_interval = 1  # Time in seconds between price updates
trading_duration = 60  # Duration of the trading session in seconds

def simulate_stock_price(current_price):
    """Simulate the stock price fluctuation."""
    change_percent = np.random.uniform(-price_fluctuation, price_fluctuation)
    new_price = current_price * (1 + change_percent)
    return round(new_price, 2)

def buy_stock(price, amount):
    """Buy stocks with the given amount of money."""
    global capital, portfolio
    num_stocks = int(amount / price)
    if capital >= num_stocks * price:
        capital -= num_stocks * price
        portfolio += num_stocks
        trade_history.append(('buy', num_stocks, price))
        return num_stocks
    else:
        return 0

def sell_stock(price, num_stocks):
    """Sell the given number of stocks."""
    global capital, portfolio
    if portfolio >= num_stocks:
        capital += num_stocks * price
        portfolio -= num_stocks
        trade_history.append(('sell', num_stocks, price))
        return num_stocks
    else:
        return 0

def display_trading_info(price):
    """Display trading information."""
    global capital, portfolio
    print(f"Current Stock Price: ${price}")
    print(f"Capital: ${capital}")
    print(f"Portfolio: {portfolio} stocks")
    print()

def automate_trading(price):
    """Automate trading decisions."""
    global capital
    if capital > 5000:
        buy_stock(price, 1000)  # Buy $1000 worth of stock
    elif portfolio > 10:
        sell_stock(price, 10)  # Sell 10 stocks

# Main trading loop
start_time = time.time()
while time.time() - start_time < trading_duration:
    stock_price = simulate_stock_price(stock_price)
    display_trading_info(stock_price)
    automate_trading(stock_price)
    time.sleep(tick_interval)

# Output the trading history
df = pd.DataFrame(trade_history, columns=['Action', 'Quantity', 'Price'])
print(df)

# Use pyautogui to display the final trading result in a dialog box
pyautogui.alert(f"Final Capital: ${capital}\nFinal Portfolio: {portfolio} stocks\nTrading session complete!")

# Save trading history to a CSV file
df.to_csv('trade_history.csv', index=False)
