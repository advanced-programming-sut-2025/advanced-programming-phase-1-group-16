package com.group16.stardewvalley.view;

import com.badlogic.gdx.Screen;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.map.TileTextureManager;
import com.group16.stardewvalley.model.menu.Menu;
import com.group16.stardewvalley.view.graphics.GameScreen;

import java.util.Scanner;

public class AppView implements Screen {

    private final Main game;
    private static GameScreen gameScreen;


    public AppView(Main game) {
        this.game = game;
        gameScreen = new GameScreen();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Scanner scanner = new Scanner(System.in);
        // معادل حلقه‌ی do-while قبلی
        App.getCurrentMenu().checkCommand(scanner); // اگر هنوز Scanner استفاده می‌کنی، باید UI بشه
        if (App.getActiveGame() != null) {
            gameScreen = new GameScreen();
            game.setScreen(gameScreen);

        }

        if (App.getCurrentMenu() == Menu.ExitMenu) {
            GameAssetManager.getGameAssetManager().dispose();
            TileTextureManager.getTileTextureManager().dispose();
            // بسته شدن بازی
            System.out.println("Exiting...");
            // در آینده بهتره exit رو کنترل کنی
        }
    }

    public static GameScreen getGameScreen() {
        return gameScreen;
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
