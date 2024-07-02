import json
import time
import numpy as np
import random
from py4j.java_gateway import JavaGateway, CallbackServerParameters


class ProfileEntryPoint:
    def __init__(self, gateway):
        self.gateway = gateway

    def get_profile_data(self, obj):
        print("Notified by Java")
        print(obj)
        self.gateway.jvm.System.out.println("Hello from python!")

        return "A Return Value"

    class Java:
        implements = ["com.example.ProfileInterface"]


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
        if len(self.portfolio) >= 1:
            # Increase uncertainty at a random rate for each stock
            self.increase_uncertainty()
            # Decide whether to sell based on uncertainty level
            self.check_uncertainty_and_sell(stockList)

        if len(self.portfolio) <= 3:
            choice = stockList[random.randint(0, len(stockList) - 1)]
            if self.buy_stock(choice, random.randint(1, 20)):
                choice = stockList[random.randint(0, len(stockList) - 1)]
            if not self.buy_stock(choice, random.randint(1, 20)):
                pass
    
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
                self.uncertainty[stock.stockName] = random.uniform(0, 0.2)
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


class Events:
    def __init__(self):
        self.event = "DEFAULT"

    def passStockData(self, stock_list, gateway):
        java_app = gateway.entry_point  # Access JavaApp instance

        data = json.dumps([vars(obj) for obj in stock_list])

        try:
            java_app.updateStockPane(data)
        except Exception as e:
            print(f"Error passing stock data: {e}")

    def generate_random_event(self, stock_list):
        events = [
            "WAR",
            "INFLATION",
            "TECHNOLOGICAL BREAKTHROUGH",
            "ASTEROID MINING BOOM",
            "SPACE TOURISM REGULATIONS",
            "COLONIZATION OF MARS",
            "TECH STOCK CRASH",
            "ALIEN ENCOUNTER",
            "SPACE WEATHER DISRUPTION",
            "RESOURCE SCARCITY",
            "SPACE PIRATE ATTACK",
            "NOTHING NEW"
        ]
        self.event = random.choice(events)
        self.affect_stock_prices(stock_list)

    def affect_stock_prices(self, stock_list):
        for stock in stock_list:
            if self.event == "WAR":
                if "MILITARY" in stock.category:
                    stock.price_fluctuation *= 1.5
            elif self.event == "INFLATION":
                stock.price_fluctuation *= 0.5
            elif self.event == "TECHNOLOGICAL BREAKTHROUGH":
                if "INFRA" in stock.category:
                    stock.price_fluctuation *= 1.2
            elif self.event == "ASTEROID MINING BOOM":
                if "COMMERCE" in stock.category:
                    stock.price_fluctuation *= 1.3
            elif self.event == "SPACE TOURISM REGULATIONS":
                if "COMMERCE" in stock.category:
                    stock.price_fluctuation *= 0.8
            elif self.event == "COLONIZATION OF MARS":
                if "INFRA" in stock.category:
                    stock.price_fluctuation *= 1.4
                elif "COMMERCE" in stock.category:
                    stock.price_fluctuation *= 1.8
            elif self.event == "TECH STOCK CRASH":
                if "TECH" in stock.category:
                    stock.price_fluctuation *= 0.7
            elif self.event == "ALIEN ENCOUNTER":
                if "MILITARY" in stock.category:
                    stock.price_fluctuation *= 1.5
                elif "INFRA" in stock.category:
                    stock.price_fluctuation *= 0.8
            elif self.event == "SPACE WEATHER DISRUPTION":
                if "INFRA" in stock.category:
                    stock.price_fluctuation *= 0.9
                elif "COMMERCE" in stock.category:
                    stock.price_fluctuation *= 0.9
            elif self.event == "RESOURCE SCARCITY":
                if "COMMERCE" in stock.category:
                    stock.price_fluctuation *= 0.8
            elif self.event == "SPACE PIRATE ATTACK":
                if "COMMERCE" in stock.category:
                    stock.price_fluctuation *= 0.7
            else:
                stock.price_fluctuation = stock.price_fluctuation_base


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
        self.stockPrice = current_price * (1 + change_percent)
        return round(self.stockPrice, 2)


def main():
    # Start the Py4J gateway for ProfileEntryPoint

    #Gate Way For JAVA TO PYTHON
    profile_gateway = JavaGateway(callback_server_parameters=CallbackServerParameters())
    listener = ProfileEntryPoint(profile_gateway)

    profile_gateway.entry_point.registerListener(listener)
    profile_gateway.entry_point.notifyAllListeners()

    #END Gate Way For JAVA TO PYTHON


    # Connect to the Java GatewayServer
    java_gateway = JavaGateway()
    
    # Instantiate classes
    player = Player("John Doe", 10000)
    event_system = Events()

    count = 0
    tick_limit = 40
    space_nasdaq = [
        Stock("Space Rocks", 100, "INFRA"),
        Stock("Hyper Accelerators", 90, "MILITARY"),
        Stock("Tiki Torches", 120, "COMMERCE"),
        Stock("Space Worm Jelly", 2000, "COMMERCE"),
        Stock("Stone Pick Axe", 10, "INFRA"),
        Stock("Quantum Crystals", 150, "TECH"),
        Stock("Galactic Spices", 300, "COMMERCE"),
        Stock("Nebula Diamonds", 5000, "LUXURY"),
        Stock("Warp Engines", 800, "TECH"),
        Stock("Starship Blueprints", 50, "TECH"),
        Stock("Plasma Cannons", 180, "MILITARY"),
        Stock("Dimensional Artifacts", 250, "CURIOSITIES"),
        Stock("Cosmic Energy Cells", 400, "ENERGY"),
        Stock("Alien Relics", 700, "CURIOSITIES"),
        Stock("Neutronium Ore", 1200, "MATERIALS"),
        Stock("Holographic Entertainment", 80, "CULTURE"),
        Stock("Teleportation Devices", 350, "TECH"),
        Stock("Asteroid Mining Rights", 2000, "MATERIALS"),
        Stock("Exotic Pets", 180, "CURIOSITIES"),
        Stock("Void Crystals", 280, "ENERGY")
    ]
    
    stop_flag = False
    
    while not stop_flag:
        for stock in space_nasdaq:
            stock.simulate_stock_price(stock.stockPrice)
            print(stock.display())

        print(f"Cash balance: ${player.cash_balance:.2f}")
        print(f"Portfolio: {player.portfolio}")
        for stock_name, uncertainty in player.uncertainty.items():
            print(f"Uncertainty for {stock_name}: {uncertainty:.2f}")
        print()

        player.simulate(space_nasdaq)
        time.sleep(1)
        
        count += 1
        print(f"TICKS TILL NEXT EVENT {tick_limit - count}")
        
        if count % 5 == 0:
            print("Passing stock data")
            event_system.passStockData(space_nasdaq, java_gateway)
        
        if count >= tick_limit:
            event_system.generate_random_event(space_nasdaq)
            count = 0

    # Shutdown the gateway servers
    #profile_gateway.shutdown()
    #java_gateway.close()

if __name__ == "__main__":
    main()
