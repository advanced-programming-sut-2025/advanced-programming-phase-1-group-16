package com.group16.stardewvalley.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GameClientApp {
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;
    private static Thread listenerThread;
    private static boolean connected = false;

    public static void connect(String serverIp, int serverPort) throws IOException {
        socket = new Socket(serverIp, serverPort);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        connected = true;

        startListening();
    }

    private static void startListening() {
        listenerThread = new Thread(() -> {
            try {
                while (connected) {
                    String message = in.readUTF();
                    // پیام دریافتی رو برای کنترلرها بفرست
                    System.out.println("SERVER: " + message);
                    // مثلاً یه Dispatcher صدا بزن
                }
            } catch (IOException e) {
                System.err.println("Disconnected from server.");
                connected = false;
            }
        });
        listenerThread.start();
    }

    public static void sendMessage(String msg) throws IOException {
        if (connected) {
            out.writeUTF(msg);
            out.flush();
        }
    }

    public static void disconnect() {
        try {
            connected = false;
            if (listenerThread != null) listenerThread.interrupt();
            if (socket != null) socket.close();
        } catch (IOException ignored) {}
    }

    public static boolean isConnected() {
        return connected;
    }
}
