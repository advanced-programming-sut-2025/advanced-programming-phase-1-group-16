package com.group16.stardewvalley;

import java.io.*;
import java.net.*;

public class ServerMain {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("Server started on port 8888...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // برای هر کلاینت یک ترد جدید راه‌اندازی کن
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String request;
            while ((request = in.readLine()) != null) {
                System.out.println("Received from client: " + request);
                out.println("Server received: " + request); // پاسخ به کلاینت
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
