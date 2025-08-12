package com.group16.stardewvalley.view.menuGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.Message;
import com.group16.stardewvalley.controller.menu.MainMenuController;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.user.PlayerSessionInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OnlinePlayersView implements Screen {
    private Stage stage;
    private Skin skin;
    private static final List<String> playerDisplayListData = new ArrayList<>();
    private static final com.badlogic.gdx.scenes.scene2d.ui.List<String> playerDisplayList = new com.badlogic.gdx.scenes.scene2d.ui.List<>(GameAssetManager.getGameAssetManager().getSkin());;
    private ScrollPane playerScrollPane;
    private Label statusLabel;
    private TextButton backButton;

    public OnlinePlayersView(Skin skin) {
        this.skin = skin;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Texture bgTexture = new Texture(Gdx.files.internal("Background/mainBack.jpeg"));
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        // لیست بازیکنان
        playerScrollPane = new ScrollPane(playerDisplayList, skin);

        backButton = new TextButton("Back", skin);
        statusLabel = new Label("", skin);

        Table root = new Table();
        root.setFillParent(true);
        root.center().pad(40);

        root.add(new Label("Online Players", skin)).padBottom(10).center().row();
        root.add(playerScrollPane).width(600).height(500).padBottom(10).center().row();
        root.add(statusLabel).padTop(10).center().row();
        root.add(backButton).width(500).center();

        stage.addActor(root);

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                Main.getMain().setScreen(new MainMenuView(
                    new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

    }
    public void setMessage(String msg) {
        statusLabel.setText(msg);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }

    public static void fetchOnlinePlayers(Message message) {
        if (message != null && (boolean) message.getFromBody("success")) {
            Type listType = new TypeToken<List<PlayerSessionInfo>>() {}.getType();
            Object rawPlayers = message.getFromBody("players");

            List<PlayerSessionInfo> players = new Gson()
                .fromJson(new Gson().toJson(rawPlayers), listType);

            updatePlayerList(players);
        }
    }

    public static void updatePlayerList(List<PlayerSessionInfo> upToDatePlayers) {
        playerDisplayListData.clear();

        if (upToDatePlayers.isEmpty()) {
            playerDisplayListData.add("No players online");
        } else {
            for (PlayerSessionInfo player : upToDatePlayers) {
                playerDisplayListData.add(player.getDisplayName());
            }
        }

        playerDisplayList.setItems(playerDisplayListData.toArray(new String[0]));
    }



    public void onOnlinePlayersReceived(List<PlayerSessionInfo> players) {
        Gdx.app.postRunnable(() -> updatePlayerList(players));
    }

}
