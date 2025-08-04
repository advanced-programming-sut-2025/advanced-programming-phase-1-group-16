package com.group16.stardewvalley.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection extends Thread {
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    private volatile boolean running = true;

    public ServerConnection(String host, int port) {
        try {
            socket = new Socket(host, port);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String response;
            while (running && (response = input.readLine()) != null) {
                System.out.println("Server: " + response);
                // پیام دریافتی رو بده به کلاس مربوط (مثلاً GameLobby)
            }
        } catch (IOException e) {
            if (running) {
                e.printStackTrace();
            }
        } finally {
            closeConnection();
        }
    }

    public void sendMessage(String msg) {
        output.println(msg);
    }

    public void stopConnection() {
        running = false;
        closeConnection();
    }

    private void closeConnection() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
