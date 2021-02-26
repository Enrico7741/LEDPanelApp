package com.example.ledpanelapp;

public class MainController {
    ControlConnection connection;

    public MainController() {
        connection = new ControlConnection();
    }

    public boolean connect(String ip) {
        return connection.connect(ip);
    }

    public boolean disconnect() {
        return connection.disconnect();
    }
}