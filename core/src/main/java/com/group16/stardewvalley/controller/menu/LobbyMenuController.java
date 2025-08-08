package com.group16.stardewvalley.controller.menu;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.group16.stardewvalley.Message;
import com.group16.stardewvalley.model.LobbyInfo;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.view.menuGraphics.LobbyMenuView;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LobbyMenuController extends Table {
    private LobbyMenuView view;
    private final List<String> playerNames = new ArrayList<>();
    private final Map<String, Boolean> readinessMap = new HashMap<>();

    public LobbyMenuController() {

    }

    public Result startGame(LobbyInfo lobby) {
        HashMap<String, Object> bodyMessage = new HashMap<>();
        bodyMessage.put("lobbyId", lobby.getLobbyId());

        Message message = new Message(bodyMessage, Message.Type.START_GAME);
        Message response = App.sendAndWait(message);

        if (response != null && (boolean) response.getFromBody("success")) {
            return new Result(true, "Game started!");
        } else {
            String error = response != null ? (String) response.getFromBody("error") : "No response from server";
            return new Result(false, error);
        }
    }


    public void setView(LobbyMenuView view) {
        this.view = view;
    }

    public Result createLobby(String lobbyName, boolean isPrivate, String password) {
        HashMap<String, Object> bodyMessage = new HashMap<>();
        bodyMessage.put("username", App.getLoggedInUser().getUsername());
        bodyMessage.put("lobbyName", lobbyName);
        bodyMessage.put("private", isPrivate);
        bodyMessage.put("password", password);

        Message message = new Message(bodyMessage, Message.Type.CREATE_LOBBY);
        Message response = App.sendAndWait(message);
        if (response == null) {
            return new Result(false, "response is null");
        }
        if (! (boolean) response.getFromBody("success")) {
            return new Result(false, "response is not success");
        }
        return new Result(true, "lobby created");
    }

    public Result joinLobby(String lobbyName) {
        if (lobbyName == null || lobbyName.isEmpty()) {
            return new Result(false, "No lobby selected");
        }
        HashMap<String, Object> bodyMessage = new HashMap<>();
        bodyMessage.put("username", App.getLoggedInUser().getUsername());
        bodyMessage.put("lobbyName", lobbyName);
        Message message = new Message(bodyMessage, Message.Type.JOIN_LOBBY);
        Message response = App.sendAndWait(message);
        if (response != null && ! (boolean) response.getFromBody("success")) {
            return new Result(false, response.getFromBody("error"));
        }
        return new Result(true, "Successfully joined lobby");

    }

    public void fetchLobbyList() {
        //server
        HashMap<String, Object> bodyMessage = new HashMap<>();
        bodyMessage.put("hello server", ":))");
        Message message = new Message(bodyMessage, Message.Type.GET_LOBBY_LIST);
        Message response = App.sendAndWait(message);

        if (response != null && (boolean) response.getFromBody("success")) {
            Type listType = new TypeToken<List<LobbyInfo>>() {}.getType();
            Object rawLobbies = response.getFromBody("lobbies");
            List<LobbyInfo> lobbies = new Gson().fromJson(new Gson().toJson(rawLobbies), listType);
            view.updateLobbyList(lobbies);
        } else {
            view.setMessage("Failed to fetch lobby list.");
        }
    }

    public Result leaveLobby(LobbyInfo lobby) {
        if (lobby == null) {
            return new Result(false, "No lobby selected");
        }
        HashMap<String, Object> bodyMessage = new HashMap<>();
        bodyMessage.put("username", App.getLoggedInUser().getUsername());
        bodyMessage.put("lobbyName", lobby.getName());
        Message message = new Message(bodyMessage, Message.Type.LEAVE_LOBBY);
        Message response = App.sendAndWait(message);
        if (response != null && ! (boolean) response.getFromBody("success")) {
            return new Result(false, response.getFromBody("error"));
        }
        return new Result(true, "You left the lobby");
    }

    public Result setLobbyVisibility(String lobbyName, boolean visible) {
        HashMap<String, Object> bodyMessage = new HashMap<>();
        bodyMessage.put("lobbyName", lobbyName);
        bodyMessage.put("visible", visible);
        Message message = new Message(bodyMessage, Message.Type.SET_LOBBY_VISIBILITY);
        Message response = App.sendAndWait(message);
        if (response != null && (boolean) response.getFromBody("success")) {
            return new Result(false, response.getFromBody("error"));
        }
        if (response == null) {
            return new Result(false, "response is null");
        }
        return new Result(true, "Visibility changed");
    }

    public void searchLobbyById(String ID) {
        HashMap<String, Object> bodyMessage = new HashMap<>();
        bodyMessage.put("id", ID);
        Message message = new Message(bodyMessage, Message.Type.SEARCH_LOBBY);
        Message response = App.sendAndWait(message);

        if (response != null && (boolean) response.getFromBody("success")) {
            String lobbyName = response.getFromBody("lobbyName");
            LobbyInfo lobby = view.getLobbyByName(lobbyName);
            if (lobby != null) {
                setLobbyVisibility(lobbyName, true);
            }
        } else {
            view.setMessage("Lobby not found with ID: " + ID);
        }
    }


}

