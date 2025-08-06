package com.group16.stardewvalley.model;

import com.group16.stardewvalley.model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lobby {
    private String name;
    private List<User> players = new ArrayList<>();
    private Map<String, Boolean> readyMap = new HashMap<>();

    public Lobby(String name) {
        this.name = name;
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
}
