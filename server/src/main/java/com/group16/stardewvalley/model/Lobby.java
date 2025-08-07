package com.group16.stardewvalley.model;

import com.group16.stardewvalley.model.user.User;

import java.util.*;

import static com.group16.stardewvalley.controller.LobbyManager.lobbyIdAlreadyExists;

public class Lobby {
    private String name;
    private User creator;
    private String lobbyId;
    private String password;
    private boolean isPrivate;
    private boolean isVisible;
    private List<User> players = new ArrayList<>();
    private Map<String, Boolean> readyMap = new HashMap<>();
    private long lastEmptyTime =  System.currentTimeMillis();


    public Lobby(String name, User creator, String password, boolean isPrivate) {
        this.name = name;
        this.creator = creator;
        this.password = password;
        this.isPrivate = isPrivate;
        this.isVisible = true;
        Random random = new Random();
        do {
            int randomId = random.nextInt(900000) + 100000;
            this.lobbyId = String.valueOf(randomId);

        } while (lobbyIdAlreadyExists(lobbyId));

        players.add(creator);
    }


    public void addPlayer(User user) {
        players.add(user);
        readyMap.put(user.getUsername(), false);
        lastEmptyTime = -1;
    }

    public void removePlayer(User user) {
        players.remove(user);
        players.removeIf(u -> u.getUsername().equals(user.getUsername()));
        if (players.size() < 2) {
            lastEmptyTime = System.currentTimeMillis();
        }
    }

    public long getLastEmptyTime() {
        return lastEmptyTime;
    }

    public void setLastEmptyTime(long lastEmptyTime) {
        this.lastEmptyTime = lastEmptyTime;
    }

    public void nextAdmin() {
        if (players.size() > 1) {
            User admin = players.get(1);
            creator = admin;
        }
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

    public boolean isPlayerExists(User user) {
        for (User joinedUser : players) {
            if (joinedUser.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
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

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
