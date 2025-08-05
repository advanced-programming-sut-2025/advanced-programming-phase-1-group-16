package com.group16.stardewvalley;

import com.group16.stardewvalley.app.ListenerThread;

import java.io.*;
import java.net.*;

public class ServerMain {
    public static void main(String[] args) {
        try {
            System.out.println("Server started on port 8888...");
            ServerApp.setListenerThread(new ListenerThread(8888));
            ServerApp.startListening();
        } catch (IOException e) {
            System.err.println("Error starting tracker: " + e.getMessage());
        }
    }
}
