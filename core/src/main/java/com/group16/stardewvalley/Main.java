package com.group16.stardewvalley;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.controller.menu.LobbyMenuController;
import com.group16.stardewvalley.controller.menu.StartMenuController;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.PlayerGraphics;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.view.graphics.GameScreen;
import com.group16.stardewvalley.view.menuGraphics.LobbyMenuView;
import com.group16.stardewvalley.view.menuGraphics.StartMenuView;

public class Main extends Game {
    GameMenuController controller = new GameMenuController();
    private final MapController mapController = new MapController();
    private static Main main;
    private static SpriteBatch batch;
    private GameScreen gameScreen;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        getMain().setScreen(new StartMenuView(new StartMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }
}
