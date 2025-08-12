package com.group16.stardewvalley.app;

import com.group16.stardewvalley.model.Lobby;
import com.group16.stardewvalley.model.user.User;

import java.sql.Connection;

// اطلاعات هر بازیکن آنلاین
public class PlayerSession {
    private User user;
    private Lobby currentLobby;
    private ClientConnectionThread connection;

    public PlayerSession(User user, ClientConnectionThread connection, Lobby currentLobby) {
        this.user = user;
        this.connection = connection;
        this.currentLobby = currentLobby;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClientConnectionThread getConnection() {
        return connection;
    }

    public void setConnection(ClientConnectionThread connection) {
        this.connection = connection;
    }

    public Lobby getCurrentLobby() {
        return currentLobby;
    }

    public void setCurrentLobby(Lobby currentLobby) {
        this.currentLobby = currentLobby;
    }
}
