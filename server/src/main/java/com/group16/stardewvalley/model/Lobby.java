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
    private List<User> users = new ArrayList<>();
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

        users.add(creator);
    }


    public void addPlayer(User user) {
        users.add(user);
        readyMap.put(user.getUsername(), false);
        lastEmptyTime = -1;
    }

    public void removePlayer(User user) {
        users.remove(user);
        users.removeIf(u -> u.getUsername().equals(user.getUsername()));
        if (users.size() < 2) {
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
        if (users.size() > 1) {
            User admin = users.get(1);
            creator = admin;
        }
    }

    public void setReady(String username, boolean ready) {
        readyMap.put(username, ready);
    }

    public boolean allReady() {
        if (users.size() < 2) return false;
        for (boolean ready : readyMap.values()) {
            if (!ready) return false;
        }
        return true;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isPlayerExists(User user) {
        for (User joinedUser : users) {
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
