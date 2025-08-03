package com.group16.stardewvalley;

import java.io.*;
import java.net.*;

public class ClientMain {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8888);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to server.");
            String userInput;
            while ((userInput = console.readLine()) != null) {
                output.println(userInput);
                String response = input.readLine();
                System.out.println("Response from server: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
