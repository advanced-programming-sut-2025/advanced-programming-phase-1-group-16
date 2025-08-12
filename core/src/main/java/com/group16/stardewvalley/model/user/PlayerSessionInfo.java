package com.group16.stardewvalley.model.user;

import com.group16.stardewvalley.model.LobbyInfo;

public class PlayerSessionInfo {
    private String username;
    private LobbyInfo currentLobby;

    public PlayerSessionInfo(String username, LobbyInfo currentLobby) {
        this.username = username;
        this.currentLobby = currentLobby;
    }

    public String getUsername() {
        return username;
    }

    public LobbyInfo getCurrentLobby() {
        return currentLobby;
    }

    public String getDisplayName() {
        return username +
               (currentLobby != null ? " (" + currentLobby.getName() + ")" : "");
    }
}
