package com.group16.stardewvalley.controller;

import com.group16.stardewvalley.model.Lobby;
import com.group16.stardewvalley.model.user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LobbyManager {
    private static final Map<String, Lobby> lobbies = new ConcurrentHashMap<>();

    public static void createLobby(String lobbyName, User creator, String password, boolean isPrivate) {
        lobbies.put(lobbyName, new Lobby(lobbyName, creator, password, isPrivate));
    }

    public static Lobby getLobby(String name) {
        return lobbies.get(name);
    }

    public static List<String> getAllLobbyNames() {
        return new ArrayList<>(lobbies.keySet());
    }

    public static Collection<Lobby> getAllLobbies() {
        return lobbies.values();
    }


    public static void removeLobby(String name) {
        lobbies.remove(name);
    }

}
