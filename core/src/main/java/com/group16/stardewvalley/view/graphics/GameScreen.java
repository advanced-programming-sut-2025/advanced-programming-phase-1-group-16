package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.graphic.CharacterController;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.TileRenderer;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileTextureManager;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.user.Player;

import java.util.Map;

public class GameScreen implements Screen, InputProcessor {
    private SpriteBatch batch;
    private Tile[][] map;
    private TileTextureManager textureManager;
    public static OrthographicCamera camera;
    private Viewport viewport;
    TileRenderer tileRenderer;
    public static float totalGameTime = 0f;
    private MapController mapController = new MapController();


    public static final int TILE_SIZE = 17;

    public GameScreen() {
        this.map = App.getActiveGame().getMap();
        camera = new OrthographicCamera();
        viewport = new FillViewport(30 * TILE_SIZE, 20 * TILE_SIZE, camera);
        viewport.apply();

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        textureManager = new TileTextureManager();
        tileRenderer = new TileRenderer();

    }

    @Override
    public void render(float delta) {
        totalGameTime += Gdx.graphics.getDeltaTime();
        if (totalGameTime > 100f) {
            App.getActiveGame().nextTurn();
            totalGameTime = 0f;
        }
        camera.position.set(App.getActiveGame().getCurrentPlayer().getX() * TILE_SIZE, App.getActiveGame().getCurrentPlayer().getY() * TILE_SIZE, 0);
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
        Gdx.gl.glClearColor(0.2f, 0.5f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                Tile tile = map[y][x];
                Texture texture;
                if (tile.getType() == TileType.Cottage || tile.getType() == TileType.CottageStartPos) {
                    texture = textureManager.getTexture(TileType.Ground);
                } else texture = textureManager.getTexture(tile.getType());
                batch.draw(texture, x * TILE_SIZE, y * TILE_SIZE);
            }
        }


        for (int y = map.length - 1; y >= 0; y--) {
            for (int x = 0; x < map[y].length; x++) {
                tileRenderer.renderTile(batch, map[y][x], x, y);
            }
        }
        for (Player player1 : App.getActiveGame().getPlayers()) {
            player1.getController().render(batch);
        }
        handlePlayerInput();

        batch.end();

    }

    public void handlePlayerInput(){
        Player player = App.getActiveGame().getCurrentPlayer();

        float nextX = player.getX();
        float nextY = player.getY();
        boolean up = false, down = false, left = false, right = false;
        int speed = 1;

        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            nextY += speed;
            up = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            nextX += speed;
            right = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            nextY -= speed;
            down = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            nextX -= speed;
            left = true;
        }

        player.setPosition(new Pos((int) nextX, (int) nextY));

    }

    @Override public void dispose() {
        batch.dispose();
        textureManager.dispose();
        for (Player player : App.getActiveGame().getPlayers()) {
            player.getController().dispose();
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
    public boolean keyDown(int i) {
        return false;
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
}
