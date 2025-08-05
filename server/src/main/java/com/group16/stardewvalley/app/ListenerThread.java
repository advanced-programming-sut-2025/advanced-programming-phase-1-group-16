package com.group16.stardewvalley.app;

import com.group16.stardewvalley.ServerApp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenerThread extends Thread {
	private final ServerSocket serverSocket;

	public ListenerThread(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	private void handleConnection(Socket socket) {
		if (socket == null) return;
		try {
			new ClientConnectionThread(socket).start();
		} catch (IOException e) {
			try {socket.close();} catch (IOException ex) {}
		}
	}

	@Override
	public void run() {
		while (!ServerApp.isEnded()) {
			try {
				Socket socket = serverSocket.accept();
				handleConnection(socket);
			} catch (Exception e) {
				break;
			}
		}

		try {serverSocket.close();} catch (Exception ignored) {}
	}
}
