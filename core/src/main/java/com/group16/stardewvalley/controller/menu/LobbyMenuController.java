package com.group16.stardewvalley.controller.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.group16.stardewvalley.Message;
import com.group16.stardewvalley.controllers.ClientNetworkManager;
import com.group16.stardewvalley.model.LobbyInfo;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.view.menuGraphics.LobbyMenuView;

import com.badlogic.gdx.scenes.scene2d.ui.*;

import javax.print.attribute.standard.Media;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LobbyMenuController extends Table {
    private final Skin skin;
    private LobbyMenuView view;
    private final List<String> playerNames = new ArrayList<>();
    private final Map<String, Boolean> readinessMap = new HashMap<>();
    private final Label statusLabel;
    private final TextButton readyButton;

    private boolean isReady = false;
    private final String localPlayerName;

    public LobbyMenuController(Skin skin, String localPlayerName) {
        this.skin = skin;
        this.localPlayerName = localPlayerName;

        setFillParent(true);
        pad(20);
        top();

        Label title = new Label("Multiplayer Lobby", skin, "title");
        add(title).colspan(2).padBottom(20);
        row();

        statusLabel = new Label("Waiting for players...", skin);
        add(statusLabel).colspan(2).padBottom(10);
        row();

        readyButton = new TextButton("I'm Ready", skin);
        readyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleReady();
            }
        });
        add(readyButton).colspan(2).padTop(10);
    }

    public void updatePlayerList(List<String> updatedNames) {
        for (String name : updatedNames) {
            if (!playerNames.contains(name)) {
                playerNames.add(name);
                readinessMap.put(name, false);
            }
        }
        refreshStatus();
    }

    private void toggleReady() {
        isReady = !isReady;
        readyButton.setText(isReady ? "Cancel Ready" : "I'm Ready");
        readinessMap.put(localPlayerName, isReady);
        sendReadyStateToServer(isReady);  // متدی فرضی برای ارسال به سرور
        refreshStatus();
    }

    public void setPlayerReady(String playerName, boolean ready) {
        readinessMap.put(playerName, ready);
        refreshStatus();
    }

    private void refreshStatus() {
        StringBuilder sb = new StringBuilder("Players:\n");
        for (String player : playerNames) {
            boolean ready = readinessMap.getOrDefault(player, false);
            sb.append("- ").append(player).append(ready ? " ✅" : " ❌").append("\n");
        }
        statusLabel.setText(sb.toString());

        if (allReady()) {
            startGame();
        }
    }

    private boolean allReady() {
        if (playerNames.size() < 2) return false; // حداقل ۲ بازیکن
        for (String name : playerNames) {
            if (!readinessMap.getOrDefault(name, false)) {
                return false;
            }
        }
        return true;
    }

    private void sendReadyStateToServer(boolean isReady) {
        // باید پیغامی به سرور بفرستی که این بازیکن آماده‌ست یا نه
        // این متد رو طبق سیستم socket یا networking خودت کامل کن
    }

    private void startGame() {
        System.out.println("All players are ready. Starting game...");
        // مثلا:
        // game.setScreen(new GameScreen());
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
        Message response = ClientNetworkManager.sendAndWait(message);
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
            System.out.println("No lobby selected");
            return new Result(false, "No lobby selected");
        }
        System.out.println("Joining lobby: " + lobbyName);
        // send to server
        HashMap<String, Object> bodyMessage = new HashMap<>();
        bodyMessage.put("username", App.getLoggedInUser().getUsername());
        bodyMessage.put("lobbyName", lobbyName);
        Message message = new Message(bodyMessage, Message.Type.JOIN_LOBBY);
        Message response = ClientNetworkManager.sendAndWait(message);
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
        Message response = ClientNetworkManager.sendAndWait(message);

        if (response != null && (boolean) response.getFromBody("success")) {
            Type listType = new TypeToken<List<LobbyInfo>>() {}.getType();
            Object rawLobbies = response.getFromBody("lobbies");
            List<LobbyInfo> lobbies = new Gson().fromJson(new Gson().toJson(rawLobbies), listType);
            view.updateLobbyList(lobbies);
        } else {
            view.setMessage("Failed to fetch lobby list.");
        }
    }
}

