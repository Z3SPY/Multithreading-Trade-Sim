package com.example;

import py4j.GatewayServer;

public class Py4JGatewayServer {
    private GatewayServer gatewayServer;

    public Py4JGatewayServer(Object pythonObject) {
        this.gatewayServer = new GatewayServer(pythonObject);
    }

    public void start() {
        gatewayServer.start();
        System.out.println("Py4J Gateway Server Started");
    }

    public void shutdown() {
        gatewayServer.shutdown();
        System.out.println("Py4J Gateway Server Shutdown");
    }

    public GatewayServer getThisGateway() {
        return gatewayServer;
    }
}