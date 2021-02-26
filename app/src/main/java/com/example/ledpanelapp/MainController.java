package com.example.ledpanelapp;

public class MainController {
    private ControlConnection connection;
    private boolean isConnected = false;

    public MainController() {
        connection = new ControlConnection();
    }

    public boolean connect(String ip) {
        isConnected = true;
        return connection.connect(ip);
    }

    public boolean disconnect() {
        isConnected = false;
        return connection.disconnect();
    }

    public boolean isConnected() {
        return isConnected;
    }
}