package com.group16.stardewvalley.view.menuGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

    public LobbyMenuView(LobbyMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;

        createLobbyButton = new TextButton("Create Lobby", skin);
        joinLobbyButton = new TextButton("Join Lobby", skin);
        backButton = new TextButton("Back", skin);

        lobbyList = new List<>(skin);
        lobbyList.setItems("Loading..."); // placeholder, to be updated by controller

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
        left.add(lobbyScrollPane).width(300).height(400);

        Table right = new Table();
        right.top();
        right.add(createLobbyButton).width(500).padBottom(20).row();
        right.add(joinLobbyButton).width(500).padBottom(20).row();
        right.add(backButton).width(500);

        root.add(left).padRight(80);
        root.add(right);

        stage.addActor(root);

        // button logic
        createLobbyButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();

                controller.createLobby(); // opens dialog or sends to server
            }
        });

        joinLobbyButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                String selectedLobby = lobbyList.getSelected();
                if (selectedLobby != null)
                    controller.joinLobby(selectedLobby);
            }
        });

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();

                Main.getMain().setScreen(new MainMenuView(
                    new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        controller.fetchLobbyList(); // initial request
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

    public void updateLobbyList(String[] lobbyNames) {
        lobbyList.setItems(lobbyNames);
    }
}
