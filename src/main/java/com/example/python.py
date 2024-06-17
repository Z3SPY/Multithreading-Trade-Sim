from py4j.java_gateway import JavaGateway

# Connect to the Java GatewayServer
gateway = JavaGateway()

# Access JavaApp instance
java_app = gateway.entry_point

# Example data to pass to JavaFX
data_from_python = "Hello from Python!"

# Call Java method to update JavaFX UI
java_app.updateLabel(data_from_python)

# Close the gateway connection
gateway.close()
