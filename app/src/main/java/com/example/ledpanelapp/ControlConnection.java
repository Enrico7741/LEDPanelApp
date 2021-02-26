package com.example.ledpanelapp;

import org.zeromq.ZMQ;
import org.zeromq.ZContext;
import org.zeromq.SocketType;

public class ControlConnection {

    private ZContext context;
    private ZMQ.Socket socket;

    private String connectionStr = "";

    final String STATUS = "0";
    final String TURNON = "1";
    final String TURNOFF = "2";

    public ControlConnection() {
        context = new ZContext();
        socket = context.createSocket(SocketType.REQ);
    }

    public boolean connect(String ip) {
        connectionStr = "tcp://" + ip + ":5555";
        socket.connect(connectionStr);
        getStatus();
        return true;
    }

    public boolean disconnect() {
        return socket.disconnect(connectionStr);
    }

    public void getStatus() {
        send(STATUS, "");
    }

    public void turnOn() {
        send(TURNON, "");
    }

    public void turnOff() {
        send(TURNOFF, "");
    }

    private byte[] send(String msgType, String payload) {
        socket.send(msgType + " " + payload);
        return socket.recv(0);
    }
}