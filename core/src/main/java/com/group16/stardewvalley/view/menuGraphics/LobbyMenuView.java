package com.group16.stardewvalley.view.menuGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.menu.LobbyMenuController;
import com.group16.stardewvalley.controller.menu.MainMenuController;
import com.group16.stardewvalley.model.graphics.GameAssetManager;

public class LobbyMenuView implements Screen {
    private Stage stage;
    private Skin skin;

    private final TextButton createLobbyButton;
    private final TextButton joinLobbyButton;
    private final TextButton backButton;

    private final List<String> lobbyList;
    private final ScrollPane lobbyScrollPane;

    private final LobbyMenuController controller;

    private Label statusLabel;

    public LobbyMenuView(LobbyMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;

        createLobbyButton = new TextButton("Create Lobby", skin);
        joinLobbyButton = new TextButton("Join Lobby", skin);
        backButton = new TextButton("Back", skin);

        lobbyList = new List<>(skin);
        lobbyList.setItems("Loading...");

        lobbyScrollPane = new ScrollPane(lobbyList, skin);

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);
        root.center().pad(40);

        Table left = new Table();
        left.top();
        left.add(new Label("Available Lobbies", skin)).padBottom(10).row();
        left.add(lobbyScrollPane).width(300).height(400).padBottom(10).row();

        // وضعیت اتصال یا پیام‌ها
        statusLabel = new Label("", skin);
        left.add(statusLabel).padTop(10);

        Table right = new Table();
        right.top();
        right.add(createLobbyButton).width(500).padBottom(20).row();
        right.add(joinLobbyButton).width(500).padBottom(20).row();
        right.add(backButton).width(500);

        root.add(left).padRight(80);
        root.add(right);

        stage.addActor(root);

        // دکمه ساخت لابی
        createLobbyButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                showCreateLobbyDialog();
            }
        });

        // دکمه پیوستن به لابی
        joinLobbyButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                String selectedLobby = lobbyList.getSelected();
                if (selectedLobby != null && !selectedLobby.equals("Loading...")) {
                    controller.joinLobby(selectedLobby);
                } else {
                    setStatus("Please select a lobby first.");
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

        controller.fetchLobbyList(); // دریافت اولیه لابی‌ها
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
                    setStatus("Lobby name cannot be empty.");
                    return;
                }

                if (isPrivate && password.isEmpty()) {
                    setStatus("Password is required for private lobbies.");
                    return;
                }

                // فراخوانی کنترلر
                controller.createLobby(lobbyName, isPrivate, password);

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


    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }

    // آپدیت لیست لابی‌ها
    public void updateLobbyList(String[] lobbyNames) {
        Gdx.app.postRunnable(() -> {
            if (lobbyNames.length == 0) {
                lobbyList.setItems("No lobbies found");
                joinLobbyButton.setDisabled(true);
            } else {
                lobbyList.setItems(lobbyNames);
                joinLobbyButton.setDisabled(false);
            }
        });
    }

    // نمایش پیام یا وضعیت
    public void setStatus(String text) {
        Gdx.app.postRunnable(() -> statusLabel.setText(text));
    }
}
