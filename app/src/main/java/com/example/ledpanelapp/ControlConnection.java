package com.example.ledpanelapp;

import org.zeromq.ZMQ;
import org.zeromq.ZContext;
import org.zeromq.SocketType;

public class ControlConnection {

    private ZContext context;
    private ZMQ.Socket socket;

    private String connectionStr;

    public ControlConnection() {
        context = new ZContext();
        socket = context.createSocket(SocketType.REQ);
    }

    public boolean connect(String ip) {
        connectionStr = "tcp://" + ip + ":5555";
        return socket.connect(connectionStr);
    }

    public boolean disconnect() {
        return socket.disconnect(connectionStr);
    }

    public byte[] send(String msgType, String payload) {
        socket.send(msgType + " " + payload);
        return socket.recv();
    }
}