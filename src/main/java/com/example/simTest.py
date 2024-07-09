import json
import time
import numpy as np
import random
from py4j.java_gateway import JavaGateway, CallbackServerParameters
from threading import Condition



class Player:
    def __init__(self, name, amount, user=False):
        self.transaction_cost = 0.0075  # Price for account transactions
        self.amount = amount
        self.name = name
        
        self.portfolio = {}  # Dictionary to track stocks in portfolio
        self.uncertainty = {}  # Dictionary to track stock uncertainty

        self.cash_balance = amount
        self.total_investment = 0.0
        self.uncertainty_threshold = 1.0  # Threshold at which the player decides to sell
        self.user = user  # Boolean to control uncertainty

    def getBal(self):
        return self.cash_balance
    
    def getPort(self):
        return self.portfolio
    
    def getName(self):
        return self.name

    def updateStockList(self, newPortfolio):
        self.portfolio = newPortfolio
        print(self.portfolio)
    
    def simulate(self, stockList):
        if len(self.portfolio) >= 1 and not self.user:
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
        print(self.name + " bought " + str(quantity) + " " + stock.stockName)

        total_cost = stock.stockPrice * quantity
        if self.cash_balance >= total_cost:
            print(self.name + " bought "  + stock.stockName  +" stock")
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
                if not self.user:
                    self.uncertainty[stock.stockName] = random.uniform(0, 0.2)
            # Update total investment
            self.total_investment += total_cost
            return True
        else:
            return False
    
    def sell_stock(self, stock, quantity):
        print(self.name + " sold " + str(quantity) + " " + stock.stockName)

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
                if not self.user and stock.stockName in self.uncertainty:
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
        if not self.user:
            for stock_name in self.uncertainty:
                self.uncertainty[stock_name] += random.uniform(0.01, 0.1)

    def check_uncertainty_and_sell(self, stockList):
        if not self.user:
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
            print(data)
            java_app.updateStockPane(data)
        except Exception as e:
            print(f"Error passing stock data: {e}")

    def generate_random_event(self, stock_list, gateway):
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
        print(f"Random Event: {self.event}")
        self.affect_stock_prices(stock_list)
        

        # Send the event to Java
        java_app = gateway.entry_point
        java_app.updateAlertText(self.event)


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
    
class Leaderboard:
    def __init__(self, players=None):
        if players is None:
            players = []
        self.players = players

    def add_player(self, player):
        if not isinstance(self.players, list):
            self.players = list(self.players)  # Convert to list if it's not already

        if player not in self.players:
            self.players.append(player)

    def remove_player(self, player_name):
        self.players = [player for player in self.players if player.name != player_name]

    def update_amount(self, newPlayers, gateway):
        for player in newPlayers:
            if player not in self.players:
                self.players.append(player)
        print(self.players)
        json_data = json.dumps(self.get_ranked_list(), default=lambda o: o.__dict__)
        gateway.GetLeaderBoardFromPy(json_data)

    def get_ranked_list(self):
        return sorted(self.players, key=lambda player: player.cash_balance, reverse=True)

    def print_leaderboard(self):
        sorted_players = self.get_ranked_list()
        for rank, player in enumerate(sorted_players, start=1):
            print(f"{rank}. {player.name}: {player.amount}")
    
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

# Instantiate classes
active_players = {
    Player("John Doe", 10000),
    Player("Alongy", 10000),
    Player("Haijee", 10000),
    Player("Bob", 10000),
    Player("Samenta", 10000),
    Player("Alice", 10000),
    Player("Eve", 10000),
    Player("Charlie", 10000),
    Player("Grace", 10000),
    Player("Michael", 10000),
    Player("Olivia", 10000),
    Player("Sophia", 10000),
    Player("Liam", 10000),
    Player("Emma", 10000),
    Player("Noah", 10000)

}

leaderboard = Leaderboard(active_players)


#Profile Entry Point
class ProfileEntryPoint:
    def __init__(self, gateway):
        self.gateway = gateway
        self.condition = Condition()
        self.event_triggered = False

        self.player_profile = None
        self.profileName = None
        self.profileList = None
        self.profileBal = None

    def updateLeader(self):
        for player_active in active_players:
            player_active.sell_stocks(space_nasdaq)
            print(player_active.getPort())

    def search_stock(self, stock_name):
        print(stock_name)
        for stock in space_nasdaq:
            if stock.stockName == stock_name:
                print("Stock Found")
                return stock
        print("Stock Not Found")
        return None

    def notify(self, obj, jsonData, btnState, qty, stockName):

        print("Notified Python", jsonData)
        try:
            loaded = json.loads(jsonData)

            if self.profileName is None or self.profileName != loaded["name"]:
                self.profileName = loaded["name"]
                self.profileBal = loaded["balance"]
                self.profileList = loaded["stocks"]
                print("This is your profile instance, " , self.profileName )
                print("This is your profile stock, " , self.profileList)
                print("This is your profile balance, " , self.profileBal)
            elif self.profileName is not None and self.profileName == loaded["name"]:
                self.profileName = self.player_profile.getName()
                self.profileBal = self.player_profile.getBal()
                self.profileList = self.player_profile.getPort()

            if self.player_profile is not None:
                leaderboard.add_player(self.player_profile)

            # Checks if player is still same player or if player doesnt exist
            if self.player_profile is None or self.player_profile.getName() != loaded["name"] :
                self.instantiateProfile(self.profileName, self.profileBal, self.profileList)
            

            if jsonData is not None and self.player_profile is not None: 
                if btnState == True: # Sell
                    print(self.player_profile.cash_balance)
                    print("Sell")
                    self.player_profile.sell_stock(self.search_stock(stockName), qty)
                elif btnState == False: #buy
                    print(self.player_profile.cash_balance)
                    
                    self.player_profile.buy_stock(self.search_stock(stockName), qty)
                    print("Buy")
                    print("Current Profile: ", self.player_profile.getName())
                    print("Added To Profile Portfolio: ", self.player_profile.getPort())
                    print("Current Balance: ", self.player_profile.getBal())

                return_data = {
                    "portfolio": self.player_profile.getPort(),
                    "balance": self.player_profile.getBal()
                }

        except json.JSONDecodeError as e:
            print(f"Error parsing JSON: {e}")



        print("Notified by Java")
        self.gateway.jvm.System.out.println("Hello from python!")
        with self.condition:
            self.event_triggered = True
            self.condition.notify()
        return json.dumps(return_data)
    
    def instantiateProfile(self, name, bal, stock):
        self.player_profile = Player(name, bal, True)
        self.player_profile.updateStockList(stock) # Get Stock from db
        print(f"Created new profile for {name} with amount {bal}")
        print("Profile already exists")
        print(self.player_profile.getName())
        leaderboard.add_player(self.player_profile)

        return self.player_profile

    def toString(self):
        return

    def wait_for_event(self):
        with self.condition:
            while not self.event_triggered:
                self.condition.wait()
            # Reset event for future use
            self.event_triggered = False
            print("Event received from Java!")

    class Java:
        implements = ["com.example.ProfileInterface"]




def main():
    # Start the Py4J gateway for ProfileEntryPoint

    #Gate Way For JAVA TO PYTHON
    profile_gateway = JavaGateway(callback_server_parameters=CallbackServerParameters())
    listener = ProfileEntryPoint(profile_gateway)
    profile_gateway.entry_point.registerListener(listener)


    #END Gate Way For JAVA TO PYTHON


    # Connect to the Java GatewayServer
    java_gateway = JavaGateway()
    
    


    event_system = Events()

    count = 0
    tick_limit = 10
   
    
    stop_flag = False
    
    #Initial 
    event_system.passStockData(space_nasdaq, java_gateway)
    leaderboard.update_amount(active_players, java_gateway) # updates leaderboards every passed datastock

    while not stop_flag:
        for stock in space_nasdaq:
            stock.simulate_stock_price(stock.stockPrice)
            #print(stock.display())

        #print(f"Cash balance: ${player.cash_balance:.2f}")
        #print(f"Portfolio: {player.portfolio}")
        """for stock_name, uncertainty in player.uncertainty.items():
            print(f"Uncertainty for {stock_name}: {uncertainty:.2f}")
            print()"""
        
        for player in active_players:
            player.simulate(space_nasdaq)
            
        time.sleep(1)
        
        count += 1
        #print(f"TICKS TILL NEXT EVENT {tick_limit - count}")
        
        if count % 5 == 0:
            #print("Passing stock data")
            leaderboard.update_amount(active_players, java_gateway) # updates leaderboards every passed datastock
            event_system.passStockData(space_nasdaq, java_gateway)
        
        if count >= tick_limit:
            event_system.generate_random_event(space_nasdaq, java_gateway)
            count = 0

    # Shutdown the gateway servers
    #profile_gateway.shutdown()
    #java_gateway.close()

if __name__ == "__main__":
    main()