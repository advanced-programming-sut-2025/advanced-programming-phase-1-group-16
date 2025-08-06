package com.group16.stardewvalley.model;

import java.util.List;

public class LobbyInfo {
    private String name;
    private String lobbyId;
    private boolean isPrivate;
    private String password;
    private List<String> playerUsernames;

    public LobbyInfo(String name, List<String> playerUsernames, String password, boolean isPrivate, String lobbyId) {
        this.name = name;
        this.playerUsernames = playerUsernames;
        this.password = password;
        this.isPrivate = isPrivate;
        this.lobbyId = lobbyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getPlayerUsernames() {
        return playerUsernames;
    }

    public void setPlayerUsernames(List<String> playerUsernames) {
        this.playerUsernames = playerUsernames;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }
}
