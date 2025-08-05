package com.group16.stardewvalley;

import com.group16.stardewvalley.app.ClientConnectionThread;
import com.group16.stardewvalley.app.ListenerThread;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class ServerApp {
    public static ArrayList<Game> games = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static final int TIMEOUT_MILLIS = 500;
    private static final ArrayList<ClientConnectionThread> connections = new ArrayList<>();
    private static boolean exitFlag = false;
    private static ListenerThread listenerThread;

    public static ClientConnectionThread getConnectionByIpPort(String ip, int port) {
        synchronized (connections) {
            for (ClientConnectionThread connection : connections) {
                if (connection.getOtherSidePort() == port && ip.equals(connection.getOtherSideIP())) {
                    return connection;
                }
            }
        }
        return null;
    }

    public static boolean isEnded() {
        return exitFlag;
    }

    public static void setListenerThread(ListenerThread listenerThread) {
        ServerApp.listenerThread = listenerThread;
    }

    public static List<ClientConnectionThread> getConnections() {
        synchronized (connections) {
            return List.copyOf(ServerApp.connections);
        }
    }

    public static void startListening() {
        if (listenerThread != null && !listenerThread.isAlive()) {
            listenerThread.start();
        } else {
            throw new IllegalStateException("Listener thread is already running or not set.");
        }
    }

    public static void endAll() {
        exitFlag = true;
        synchronized (connections) {
            for (ClientConnectionThread connection : connections) {
                connection.end();
            }
            connections.clear();
        }
    }

    public static void removeClientConnection(ClientConnectionThread clientConnectionThread) {
        if (clientConnectionThread != null) {
            synchronized (connections) {
                connections.remove(clientConnectionThread);
            }
            clientConnectionThread.end();
        }
    }

    public static void addClientConnection(ClientConnectionThread clientConnectionThread) {
        if (clientConnectionThread != null) {
            synchronized (connections) {
                if (!connections.contains(clientConnectionThread)) {
                    connections.add(clientConnectionThread);
                }
            }
        }
    }

    public static ArrayList<Game> getGroups() {
        return games;
    }

    public static void setGroups(ArrayList<Game> games) {
        ServerApp.games = games;
    }

    public static void addUser(User user) {
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        ServerApp.users = users;
    }

}
