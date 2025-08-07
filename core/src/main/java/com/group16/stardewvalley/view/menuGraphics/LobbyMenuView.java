package com.group16.stardewvalley.view.menuGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.menu.LobbyMenuController;
import com.group16.stardewvalley.controller.menu.MainMenuController;
import com.group16.stardewvalley.model.LobbyInfo;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.GameAssetManager;

import javax.swing.event.ChangeEvent;
import java.util.ArrayList;

public class LobbyMenuView implements Screen {
    private Stage stage;
    private Skin skin;

    private final TextButton createLobbyButton;
    private final TextButton joinLobbyButton;
    private final Label messageLabel;
    private final TextButton backButton;

    private final ScrollPane lobbyScrollPane;

    private final TextButton refreshButton;
    private final TextButton searchButton;
    private final TextField lobbyIdSearchField;

    private final List<String> lobbyDisplayList;

    private java.util.List<LobbyInfo> lobbies;

    private final LobbyMenuController controller;

    private Label statusLabel;

    public LobbyMenuView(LobbyMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;

        createLobbyButton = new TextButton("Create Lobby", skin);
        joinLobbyButton = new TextButton("Join Lobby", skin);
        backButton = new TextButton("Back", skin);

        refreshButton = new TextButton("Refresh", skin);
        searchButton = new TextButton("Search Lobby", skin);
        lobbyIdSearchField = new TextField("", skin);

        lobbyDisplayList = new List<>(skin);
        lobbyDisplayList.setItems("Loading...");
        lobbyScrollPane = new ScrollPane(lobbyDisplayList, skin);


        this.messageLabel = new Label("", skin);

        controller.setView(this);
        lobbies = new ArrayList<>();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Texture bgTexture = new Texture(Gdx.files.internal("Background/mainBack.jpeg"));
        Image background = new Image(bgTexture);
        background.setFillParent(true); // Make it stretch to screen size

        stage.addActor(background);

        Table root = new Table();
        root.setFillParent(true);
        root.center().pad(40);

        Table left = new Table();
        left.top();
        left.add(new Label("Available Lobbies", skin)).padBottom(10).row();
        left.add(lobbyScrollPane).width(600).height(500).padBottom(10).row();


        left.row().padTop(10);
        left.add(refreshButton).width(300);


        // وضعیت اتصال یا پیام‌ها
        statusLabel = new Label("", skin);
        left.add(statusLabel).padTop(10);

        Table right = new Table();
        right.top();
        right.add(new Label("Search lobby by ID: ", skin)).width(450).padBottom(20).row();
        right.add(lobbyIdSearchField).width(450).padBottom(20).row();
        right.add(searchButton).width(500).padBottom(20).row();
        right.add(createLobbyButton).width(500).padBottom(20).row();
        right.add(joinLobbyButton).width(500).padBottom(20).row();
        right.add(backButton).width(500);

        root.add(left).padRight(80);
        root.add(right);
        root.row();

        root.add(messageLabel).colspan(2).center().padTop(50);

        stage.addActor(root);

        searchButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String inputId = lobbyIdSearchField.getText().trim();
                if (inputId.isEmpty()) {
                    setMessage("Please enter a Lobby ID to search.");
                    return;
                }
                controller.searchLobbyById(inputId);
            }
        });

        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.fetchLobbyList();
            }
        });


        // دکمه ساخت لابی
        createLobbyButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                showCreateLobbyDialog();
            }
        });

        lobbyDisplayList.addListener(new ClickListener() {
            private long lastClickTime = 0;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                long clickTime = System.currentTimeMillis();
                if (clickTime - lastClickTime < 400) { // double click
                    String selectedLobby = lobbyDisplayList.getSelected();
                    if (selectedLobby != null) {
                        String lobbyName = selectedLobby.split(" \\(")[0];
                        LobbyInfo lobby = getLobbyByName(lobbyName);
                        if (lobby != null && lobby.isPlayerInLobby(App.getLoggedInUser().getUsername())) {
                            showLobbyDetailsDialog(lobby);
                        }
                    }
                }
                lastClickTime = clickTime;
            }
        });


        // دکمه پیوستن به لابی
        joinLobbyButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                String selectedLobby = lobbyDisplayList.getSelected();
                if (selectedLobby != null && !selectedLobby.equals("Loading...")) {
                    String lobbyName = selectedLobby.split(" \\(")[0];
                    LobbyInfo lobby = getLobbyByName(lobbyName);
                    if (lobby == null) {
                        setMessage("no lobby selected");
                        return;
                    }

                    if (lobby.isPrivate()) {
                        showPasswordEnterDialog(password -> {
                            if (password == null || password.isEmpty()) {
                                setMessage("Please enter a password.");
                                return;
                            }

                            if (!password.equals(lobby.getPassword())) {
                                setMessage("Invalid password");
                                return;
                            }

                            Result result = controller.joinLobby(lobbyName);
                            setMessage(result.toString());
                        });
                    } else {
                        Result result = controller.joinLobby(lobbyName);
                        setMessage(result.toString());
                    }
                } else {
                    setMessage("Please select a lobby first.");
                }
            }
        });

        // دکمه بازگشت
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                Main.getMain().setScreen(new MainMenuView(
                    new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        controller.fetchLobbyList();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    public void showCreateLobbyDialog() {
        Dialog dialog = new Dialog("Create Lobby", skin) {
            public float getPrefWidth() { return 800; }
            public float getPrefHeight() { return 700; }
        };

        dialog.setModal(true);
        dialog.setMovable(true);

        Table content = new Table(skin);

        Label lobbyNameLabel = new Label("Lobby Name:", skin);
        final TextField lobbyNameField = new TextField("", skin);
        content.add(lobbyNameLabel).padBottom(40);
        content.add(lobbyNameField).width(300).padBottom(40);
        content.row();

        final CheckBox publicCheck = new CheckBox(" Public", skin);
        final CheckBox privateCheck = new CheckBox(" Private", skin);
        content.add(publicCheck).padBottom(50).padRight(20);
        content.add(privateCheck).padBottom(50);
        content.row();

        final Label passwordLabel = new Label("Password:", skin);
        final TextField passwordField = new TextField("", skin);
        content.add(passwordLabel).padTop(10);
        content.add(passwordField).width(300).padBottom(10);
        content.row();

        passwordLabel.setVisible(false);
        passwordField.setVisible(false);

        // رفتار exclusive برای چک باکس‌ها
        publicCheck.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (publicCheck.isChecked()) {
                    privateCheck.setChecked(false);
                    passwordLabel.setVisible(false);
                    passwordField.setVisible(false);
                }
            }
        });

        privateCheck.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (privateCheck.isChecked()) {
                    publicCheck.setChecked(false);
                    passwordLabel.setVisible(true);
                    passwordField.setVisible(true);
                } else {
                    passwordLabel.setVisible(false);
                    passwordField.setVisible(false);
                }
            }
        });

        TextButton createBtn = new TextButton("Create", skin);
        TextButton cancelBtn = new TextButton("Cancel", skin);

        createBtn.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                String lobbyName = lobbyNameField.getText().trim();
                boolean isPrivate = privateCheck.isChecked();
                String password = passwordField.getText().trim();

                if (lobbyName.isEmpty()) {
                    setMessage("Lobby name cannot be empty.");
                    return;
                }

                if (isPrivate && password.isEmpty()) {
                    setMessage("Password is required for private lobbies.");
                    return;
                }

                setMessage(controller.createLobby(lobbyName, isPrivate, password).message());

                dialog.hide();
            }
        });

        cancelBtn.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                dialog.hide();
            }
        });

        Table buttonTable = new Table();
        buttonTable.add(createBtn).padRight(20);
        buttonTable.add(cancelBtn);

        dialog.getContentTable().add(content);
        dialog.getButtonTable().add(buttonTable);

        dialog.show(stage);
    }

    public interface PasswordCallback {
        void onPasswordEntered(String password);
    }


    public void showPasswordEnterDialog(PasswordCallback callback) {
        Dialog dialog = new Dialog("Enter password", skin) {
            public float getPrefWidth() { return 800; }
            public float getPrefHeight() { return 700; }
        };

        dialog.setModal(true);
        dialog.setMovable(true);

        Table content = new Table(skin);

        Label passwordLabel = new Label("This Lobby is private, enter the password:", skin);
        final TextField passwordField = new TextField("", skin);
        content.add(passwordLabel);
        content.row();
        content.add(passwordField).width(200);
        content.row();

        TextButton checkPass = new TextButton("Check Password", skin);
        TextButton cancelBtn = new TextButton("Cancel", skin);

        checkPass.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                String enteredPassword = passwordField.getText();
                dialog.hide();
                callback.onPasswordEntered(enteredPassword);
            }
        });

        cancelBtn.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                dialog.hide();
                callback.onPasswordEntered(null);
            }
        });

        Table buttonTable = new Table();
        buttonTable.add(checkPass).padRight(10);
        buttonTable.add(cancelBtn);

        dialog.getContentTable().add(content);
        dialog.getButtonTable().add(buttonTable);

        dialog.show(stage);
    }

    public void showLobbyDetailsDialog(LobbyInfo lobby) {
        Dialog dialog = new Dialog("Lobby: " + lobby.getName(), skin) {
            public float getPrefWidth() { return 800; }
            public float getPrefHeight() { return 900; }
        };

        dialog.setModal(true);
        dialog.setMovable(true);

        Table content = new Table(skin);
        content.pad(10f).defaults().left().padBottom(10);

        // Lobby ID
        content.add("Lobby ID: " + lobby.getLobbyId()).row();

        // Visibility label (قابل تغییر در لحظه)
        Label visibilityLabel = new Label("Visibility: " + (lobby.isVisible() ? "Visible" : "Invisible"), skin);
        content.add(visibilityLabel).row();

        // Players list
        content.add("Players:").row();
        Table playersTable = new Table(skin);
        for (String username : lobby.getPlayerUsernames()) {
            boolean isAdmin = username.equals(lobby.getCreatorName());
            String label = isAdmin ? username + " (Admin)" : username;
            playersTable.add("- " + label).left().row();
        }
        ScrollPane scrollPane = new ScrollPane(playersTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        content.add(scrollPane).height(200).width(400).row();

        // Buttons
        Table buttonTable = new Table(skin);
        buttonTable.defaults().width(700).padTop(20).padBottom(10);

        TextButton leaveButton = new TextButton("Leave Lobby", skin);
        leaveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Result result = controller.leaveLobby(lobby);
                setMessage(result.message());
                dialog.hide();
            }
        });
        buttonTable.add(leaveButton).row();

        boolean isAdmin = lobby.getCreatorName().equals(App.getLoggedInUser().getUsername());

        if (isAdmin) {
            TextButton toggleVisibilityButton = getTextButton(lobby, visibilityLabel);
            buttonTable.add(toggleVisibilityButton).row();
        }

        if (isAdmin && lobby.getPlayerUsernames().size() >= 2) {
            TextButton startButton = new TextButton("Start Game", skin);
            startButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    //Result res = controller.startGame(lobby.getLobbyId());
                    setMessage("Starting game...");
                    dialog.hide();
                }
            });
            buttonTable.add(startButton).row();
        }

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });
        buttonTable.add(closeButton).row();

        // Add everything to dialog
        dialog.getContentTable().add(content).row();
        dialog.getButtonTable().add(buttonTable);

        dialog.show(stage);
    }

    private TextButton getTextButton(LobbyInfo lobby, Label visibilityLabel) {
        TextButton toggleVisibilityButton = new TextButton("Toggle Visibility", skin);
        toggleVisibilityButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean newVisibility = !lobby.isVisible();
                Result result = controller.setLobbyVisibility(lobby.getName(), newVisibility);
                if (result.isSuccessful()) {
                    lobby.setVisible(newVisibility);
                    visibilityLabel.setText("Visibility: " + (newVisibility ? "Visible" : "Invisible"));
                    setMessage("Lobby visibility changed.");
                } else {
                    setMessage(result.message());
                }
            }
        });
        return toggleVisibilityButton;
    }


    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }

    // آپدیت لیست لابی‌ها
    public void updateLobbyList(java.util.List<LobbyInfo> upToDateLobbies) {
        this.lobbies = upToDateLobbies;

        if (lobbies.isEmpty()) {
            lobbyDisplayList.setItems("No lobbies found");
            joinLobbyButton.setDisabled(true);
        } else {
            java.util.List<String> displayStrings = new ArrayList<>();
            for (LobbyInfo lobby : lobbies) {
                if (!lobby.isVisible() && !lobby.isPlayerInLobby(App.getLoggedInUser().getUsername())) continue;
                String name = lobby.getName();
                int count = lobby.getPlayerUsernames().size();
                String playersString = String.join(", ", lobby.getPlayerUsernames());
                String display = String.format("%s (%d members): %s", name, count, playersString);
                displayStrings.add(display);
            }

            lobbyDisplayList.setItems(displayStrings.toArray(new String[0]));
            joinLobbyButton.setDisabled(false);
        }
    }

    public LobbyInfo getLobbyByName(String lobbyName) {
        for (LobbyInfo lobby : lobbies) {
            if (lobby.getName().equals(lobbyName)) {
                return lobby;
            }
        }
        return null;
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }
}
