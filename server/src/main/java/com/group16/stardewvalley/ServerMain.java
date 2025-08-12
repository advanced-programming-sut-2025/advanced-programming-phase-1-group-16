package com.group16.stardewvalley;

import com.group16.stardewvalley.app.ListenerThread;
import com.group16.stardewvalley.controller.LobbyManager;
import com.group16.stardewvalley.model.Lobby;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerMain {
    public static void main(String[] args) {
        try {
            System.out.println("Server started on port 8888...");
            ServerApp.setListenerThread(new ListenerThread(8888));
            ServerApp.startListening();
            startLobbyCleanupTask();
            startGameStateBroadcastTask();

        } catch (IOException e) {
            System.err.println("Error starting tracker: " + e.getMessage());
        }
    }

    private static void startLobbyCleanupTask() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            for (Lobby lobby : LobbyManager.getAllLobbies()) {
                if (lobby.getUsers().size() < 2) {
                    long emptySince = lobby.getLastEmptyTime();
                    if (emptySince != -1 && now - emptySince >= 5 * 60 * 1000) {
                        System.out.println("Removing idle lobby: " + lobby.getName());
                        LobbyManager.removeLobby(lobby.getName());
                    }
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private static void startGameStateBroadcastTask() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            ServerApp.broadcastDeltas(); // send only changes instead of full game
        }, 0, 100, TimeUnit.MILLISECONDS); // much faster than 1 minute
    }

}
