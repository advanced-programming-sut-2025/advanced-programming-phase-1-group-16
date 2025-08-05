package com.group16.stardewvalley.app;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class ClientApp {
    public static final int TIMEOUT_MILLIS = 500;

    private static String clientIP;
    private static int clientPort;

    private static C2SConnectionThread serverThread;

    private static boolean exitFlag = false;

    public static void init() {
        try {
            clientIP = InetAddress.getLocalHost().getHostAddress();
            clientPort = new Random().nextInt(10000) + 10000; // پورت تصادفی در بازه امن

            Socket serverSocket = new Socket("localhost", 8888);
            serverThread = new C2SConnectionThread(serverSocket);
        } catch (IOException e) {
            System.err.println("Failed to initialize ClientApp: " + e.getMessage());
        }
    }


    public static boolean isEnded() {
        return exitFlag;
    }

    public static void endAll() {
        exitFlag = true;

        try {
            if (serverThread != null) serverThread.end();

        } catch (Exception ignored) {
        }
    }

    public static void connectTracker() {
        if (serverThread != null && !serverThread.isAlive()) {
            serverThread.start();
        }
    }


    public static String getClientIP() {
        return clientIP;
    }

    public static int getClientPort() {
        return clientPort;
    }

    private static Map<String, List<String>> getCopyListMap(Map<String, List<String>> files) {
        Map<String, List<String>> copy = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : files.entrySet()) {
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copy;
    }

    public static C2SConnectionThread getP2TConnection() {
        return serverThread;
    }
}
