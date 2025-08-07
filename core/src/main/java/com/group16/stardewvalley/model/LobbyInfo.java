package com.group16.stardewvalley.model;

import com.group16.stardewvalley.model.user.Player;

import java.util.List;

public class LobbyInfo {
    private String name;
    private String lobbyId;
    private boolean isPrivate;
    private boolean isVisible;
    private String password;
    private String creatorName;
    private List<String> playerUsernames;

    public LobbyInfo(String name, List<String> playerUsernames, String password, boolean isPrivate, String lobbyId, String creatorName, boolean isVisible) {
        this.name = name;
        this.playerUsernames = playerUsernames;
        this.password = password;
        this.isPrivate = isPrivate;
        this.lobbyId = lobbyId;
        this.isVisible = isVisible;
        this.creatorName = creatorName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
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

    public boolean isPlayerInLobby(String username) {
        return playerUsernames.contains(username);
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
