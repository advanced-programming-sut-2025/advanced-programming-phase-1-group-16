package com.group16.stardewvalley.app;

import com.group16.stardewvalley.JSONUtils;
import com.group16.stardewvalley.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class ServerConnection extends Thread {
    private Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final BlockingQueue<Message> receivedMessagesQueue;
    private String otherSideIP;
    private int otherSidePort;
    private Socket socket;
    private AtomicBoolean end;
    private boolean initialized = false;

    private volatile boolean running = true;

    public ServerConnection(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Connected to server");
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.receivedMessagesQueue = new LinkedBlockingQueue<>();
            this.end = new AtomicBoolean(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message sendAndWaitForResponse(Message message, int timeoutMilli) {
        sendMessage(message);
        try {
            if (initialized) return receivedMessagesQueue.poll(timeoutMilli, TimeUnit.MILLISECONDS);
            socket.setSoTimeout(timeoutMilli);
            var result = JSONUtils.fromJson(dataInputStream.readUTF());
            socket.setSoTimeout(0);
            return result;
        } catch (Exception e) {
            System.err.println("Request Timed out.");
            return null;
        }
    }

    public void run() {
        while (!end.get()) {
            try {
                String receivedStr = dataInputStream.readUTF();
                Message message = JSONUtils.fromJson(receivedStr);
                boolean handled = handleMessage(message);
                if (!handled) try {
                    receivedMessagesQueue.put(message);
                } catch (InterruptedException e) {}
            } catch (Exception e) {
                break;
            }
        }

        end();
    }

    public void sendMessage(String msg) {
        //output.println(msg);
    }

    protected boolean handleMessage(Message message) {
        if (message.getType() == Message.Type.file_request) {
            //sendMessage(TrackerConnectionController.handleCommand(message));
            return true;
        }
        return false;
    }

    public void end() {
        end.set(true);
        try {
            socket.close();
        } catch (IOException e) {}
    }
}
