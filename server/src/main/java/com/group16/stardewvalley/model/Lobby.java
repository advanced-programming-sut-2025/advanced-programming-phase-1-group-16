package com.group16.stardewvalley.model;

import com.group16.stardewvalley.model.user.User;

import java.util.*;

public class Lobby {
    private String name;
    private User creator;
    private String lobbyId;
    private String password;
    private boolean isPrivate;
    private List<User> players = new ArrayList<>();
    private Map<String, Boolean> readyMap = new HashMap<>();

    public Lobby(String name, User creator, String password, boolean isPrivate) {
        this.name = name;
        this.creator = creator;
        this.password = password;
        this.isPrivate = isPrivate;
        this.lobbyId = String.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits()));
    }

    public void addPlayer(User user) {
        players.add(user);
        readyMap.put(user.getUsername(), false);
    }

    public void setReady(String username, boolean ready) {
        readyMap.put(username, ready);
    }

    public boolean allReady() {
        if (players.size() < 2) return false;
        for (boolean ready : readyMap.values()) {
            if (!ready) return false;
        }
        return true;
    }

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(List<User> players) {
        this.players = players;
    }

    public Map<String, Boolean> getReadyMap() {
        return readyMap;
    }

    public void setReadyMap(Map<String, Boolean> readyMap) {
        this.readyMap = readyMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
