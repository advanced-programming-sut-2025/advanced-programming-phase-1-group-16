package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.ServerApp;
import com.group16.stardewvalley.controller.GameController;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Tree;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.graphics.GameHUD;
import com.group16.stardewvalley.model.graphics.TileRenderer;
import com.group16.stardewvalley.model.map.*;
import com.group16.stardewvalley.model.user.Player;

import static com.group16.stardewvalley.controller.menu.HomeMenuController.findIngredient;

public class GameScreen implements Screen, InputProcessor {
    private GameController controller;
    private SpriteBatch batch;
    private TileTextureManager textureManager;
    public static OrthographicCamera camera;
    private Viewport viewport;
    TileRenderer tileRenderer;
    public static float totalGameTime = 0f;
    public static boolean showMiniMap = false;
    private OrthographicCamera miniMapCamera;
    private Viewport miniMapViewport;
    private Stage stage;
    private GameHUD gameHUD;


    public static final int TILE_SIZE = 17;

    public GameScreen() {
        this.controller = new GameController();
        camera = new OrthographicCamera();
        viewport = new FillViewport(30 * TILE_SIZE, 20 * TILE_SIZE, camera);
        viewport.apply();
        miniMapCamera = new OrthographicCamera();
        int mapPixelWidth = App.getActiveGame().getMapWidth() * TILE_SIZE;
        int mapPixelHeight = App.getActiveGame().getMapHeight() * TILE_SIZE;

        miniMapViewport = new FillViewport(mapPixelWidth, mapPixelHeight, miniMapCamera);
        miniMapViewport.apply();

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch = Main.getBatch();
        textureManager = new TileTextureManager();
        tileRenderer = new TileRenderer();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        gameHUD = new GameHUD(stage, GameAssetManager.getGameAssetManager().getSkin());

    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        gameHUD.updateHUD();
        handleTurn();

        if (App.getActiveGame().getCurrentPlayer().isAtHome()) {
            setCameraForHouse();
        }
        else if (showMiniMap) {
            setCameraForMiniMap();
        } else {
            setCameraForMap();
        }

        Gdx.gl.glClearColor(0.2f, 0.5f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        controller.render();

        batch.end();

        stage.act(delta);
        stage.draw();

    }

    public Stage getStage() {
        return stage;
    }

    private void setCameraForHouse() {
        camera.position.set(camera.viewportWidth + 200, camera.viewportHeight + 100, 0);
        camera.zoom = 3.5f;

        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }


    private void setCameraForMiniMap() {
        miniMapCamera.position.set(
            App.getActiveGame().getMapWidth() * TILE_SIZE / 2f,
            App.getActiveGame().getMapHeight() * TILE_SIZE / 2f,
            0
        );
        miniMapCamera.update();
        batch.setProjectionMatrix(miniMapCamera.combined);
    }

    private static void handleTurn() {
        totalGameTime += Gdx.graphics.getDeltaTime();
        if (totalGameTime > 100f) {
            App.getActiveGame().nextTurn();
            totalGameTime = 0f;
        }
    }

    private void setCameraForMap() {
        float viewportWidth = camera.viewportWidth;
        float viewportHeight = camera.viewportHeight;

        float mapPixelHeight = App.getActiveGame().getMapHeight() * TILE_SIZE;
        float mapPixelWidth = App.getActiveGame().getMapWidth() * TILE_SIZE;

        float cameraX = MathUtils.clamp(
            App.getActiveGame().getCurrentPlayer().getX() * TILE_SIZE,
            viewportWidth / 2f,
            mapPixelWidth - viewportWidth / 2f);

        float cameraY = MathUtils.clamp(
            App.getActiveGame().getCurrentPlayer().getY() * TILE_SIZE,
            viewportHeight / 2f,
            mapPixelHeight - viewportHeight / 2f);

        camera.position.set(cameraX, cameraY, 0);
        camera.zoom = 1f;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }


    @Override public void dispose() {
        batch.dispose();
        textureManager.dispose();
        for (Player player : App.getActiveGame().getPlayers()) {
            player.getPlayerGraphics().dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public boolean keyDown(int keycode) {
        return controller.handleInput(keycode);
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) {
            handleRightClick(screenX, screenY);
            return true;
        }
        return false;
    }

    private void handleRightClick(int screenX, int screenY) {
        Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
        int tileX = (int) worldCoordinates.x / TILE_SIZE;
        int tileY = (int) worldCoordinates.y / TILE_SIZE;

        Player player = App.getActiveGame().getCurrentPlayer();
        boolean isAdjacent = (Math.abs(player.getX() - tileX) + Math.abs(player.getY() - tileY) ) == 1;
        Tree tree = App.getActiveGame().getMap()[tileY][tileX].getTree();
        if (isAdjacent && tree != null) {
            if (tree.HasFruit()) {
                tree.handpickFruit();
                String fruitName = tree.getTreeType().getFruitName().toUpperCase().replace(" ", "_");
                Ingredient ingredient = findIngredient(fruitName);
                if (ingredient != null) {
                    Result result = player.getInventory().addItem(
                        new FoodIngredient(fruitName, tree.getFruitSellPrice(), ingredient),
                        4
                    );

                    // Record inventory change for multiplayer sync
                    ServerApp.recordInventoryChange(
                        App.getActiveGame(),
                        player.getUsername(),
                        player.getInventory()
                    );
                }
            }
        }

        else if (App.getActiveGame().getMap()[tileY][tileX].getType().equals(TileType.Cottage) &&
                    !player.isAtHome()) {
            player.setHomeMap(new HomeMap(player));
            player.setAtHome(true);
        } else if (player.isAtHome()) {
            player.setAtHome(false);
        }
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
