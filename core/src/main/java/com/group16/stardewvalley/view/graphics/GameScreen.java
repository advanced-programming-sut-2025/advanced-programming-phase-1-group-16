package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.GameController;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.TileRenderer;
import com.group16.stardewvalley.model.map.TileTextureManager;
import com.group16.stardewvalley.model.tools.Gadget;
import com.group16.stardewvalley.model.user.Player;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;


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
    Skin skin = new Skin(Gdx.files.internal("skin.json"));
    Stage uiStage = new Stage(new ScreenViewport());
    Table toolTable = new Table(skin);
    public static final int TILE_SIZE = 17;
    private static boolean showTools;
    private Stage toolStage;

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
        toolTable.top().left().pad(10);
        toolTable.setFillParent(true);
        toolStage = new Stage(new ScreenViewport());
        toolStage.addActor(toolTable);
    }

    @Override
    public void show() {
        batch = Main.getBatch();
        textureManager = new TileTextureManager();
        tileRenderer = new TileRenderer();
        Gdx.input.setInputProcessor(this);
    }

    public void toggleShowTools() {
        showTools = !showTools;
        if (showTools) {
            buildToolTable();
        }
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        handleTurn();

        if (showMiniMap) {
            setCameraForMiniMap();
        } else {
            setCameraForMap();
        }

        Gdx.gl.glClearColor(0.2f, 0.5f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        controller.render();

        batch.end();
        if (showTools) {
            App.getActiveGame().getCurrentPlayer().getInventory().showTools(toolStage, skin);
            toolStage.act(delta);
            toolStage.draw();
        }
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
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }


    @Override
    public void dispose() {
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

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

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
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
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

    private void buildToolTable() {
        toolTable.clear();

        for (Gadget tool : App.getActiveGame().getCurrentPlayer().getInventory().getTools().keySet()) {
            Texture texture = new Texture(Gdx.files.internal(tool.getAssetPath()));
            Image image = new Image(texture);
            toolTable.add(image).pad(10);
        }
    }


}

