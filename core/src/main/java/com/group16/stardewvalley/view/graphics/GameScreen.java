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
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.TileRenderer;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileTextureManager;
import com.group16.stardewvalley.model.user.Player;

public class GameScreen implements Screen, InputProcessor {
    private SpriteBatch batch;
    private Tile[][] map;
    private TileTextureManager textureManager;
    public static OrthographicCamera camera;
    TileRenderer tileRenderer;


    private final int TILE_SIZE = 15;

    public GameScreen() {
        this.map = App.getActiveGame().getMap();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        textureManager = new TileTextureManager();
        tileRenderer = new TileRenderer();
    }

    @Override
    public void render(float delta) {
        camera.position.set(App.getActiveGame().getCurrentPlayer().getX(), App.getActiveGame().getCurrentPlayer().getY(), 0);
        float viewportWidth = camera.viewportWidth;
        float viewportHeight = camera.viewportHeight;

        float cameraX = MathUtils.clamp(App.getActiveGame().getCurrentPlayer().getX(), viewportWidth / 2f, 4500 - viewportWidth / 2f);
        float cameraY = MathUtils.clamp(App.getActiveGame().getCurrentPlayer().getY(), viewportHeight / 2f, 3000 - viewportHeight / 2f);

        camera.position.set(cameraX, cameraY, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0.2f, 0.5f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                Tile tile = map[y][x];
                Texture texture = textureManager.getTexture(tile.getType());
                batch.draw(texture, x * TILE_SIZE, y * TILE_SIZE);
            }
        }


        for (int y = map.length - 1; y >= 0; y--) {
            for (int x = 0; x < map[y].length; x++) {
                tileRenderer.renderTile(batch, map[y][x], x, y);
            }
        }

        App.getActiveGame().getCurrentPlayer().getPlayerSprite().draw(batch);
        handlePlayerInput();

        batch.end();

    }

    public void handlePlayerInput(){
        Player player = App.getActiveGame().getCurrentPlayer();

        float nextX = player.getX();
        float nextY = player.getY();

        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            nextY += 10;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            nextX += 10;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            nextY -= 10;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            nextX -= 10;
            player.getPlayerSprite().flip(true, false);
        }

        nextX = MathUtils.clamp(nextX, 0, 4500 - player.getPlayerSprite().getWidth());
        nextY = MathUtils.clamp(nextY, 0, 3000 - player.getPlayerSprite().getHeight());

        player.setPosition(new Pos((int) nextX, (int) nextY));

        player.getPlayerSprite().setPosition(nextX, nextY);

    }

    @Override public void dispose() {
        batch.dispose();
        textureManager.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public boolean keyDown(int i) {
        Player player = App.getActiveGame().getCurrentPlayer();
        int newX = player.getX();
        int newY = player.getY();

        if (i == Input.Keys.UP) {
            newY = player.getY() + 1;
        } else if (i == Input.Keys.DOWN) {
            newY = player.getY() - 1;
        } else if (i == Input.Keys.LEFT) {
            newX = player.getX() - 1;
        } else if (i == Input.Keys.RIGHT) {
            newX = player.getX() + 1;
        }
        player.setPosition(new Pos(newX, newY));
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
