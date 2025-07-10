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
import com.group16.stardewvalley.model.map.*;
import com.group16.stardewvalley.model.user.Player;

import java.util.HashMap;
import java.util.Map;

public class GameScreen implements Screen, InputProcessor {
    private SpriteBatch batch;
    private TileTextureManager textureManager;
    private TileRenderer tileRenderer;

    public static OrthographicCamera camera;
    private final int TILE_SIZE = 15;

    private final Map<MapType, Tile[][]> maps = new HashMap<>();
    private MapType currentMapType;

    Tile[][] currentMap;

    private int currentMapWidthInPixels;
    private int currentMapHeightInPixels;

    public GameScreen() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        textureManager = new TileTextureManager();
        tileRenderer = new TileRenderer();

        // نقشه‌ها رو بارگذاری کن
        maps.put(MapType.FARM1, MapLoader.loadFromJSON("assets/maps/farm1.json"));
        maps.put(MapType.FARM2, MapLoader.loadFromJSON("assets/maps/farm2.json"));
        maps.put(MapType.TOWN, MapLoader.loadFromJSON("assets/maps/town.json"));
        maps.put(MapType.NPC_VILLAGE, MapLoader.loadFromJSON("assets/maps/npcVillage.json"));

        currentMapType = MapType.FARM1; // فرض کن کاربر Farm1 رو انتخاب کرده

        updateMapSize();
    }

    private void updateMapSize() {
        Tile[][] map = maps.get(currentMapType);
        currentMapWidthInPixels = map[0].length * TILE_SIZE;
        currentMapHeightInPixels = map.length * TILE_SIZE;
    }

    @Override
    public void render(float delta) {
        Player player = App.getActiveGame().getCurrentPlayer();
        float playerX = player.getX();
        float playerY = player.getY();

        currentMap = maps.get(currentMapType);

        float viewportWidth = camera.viewportWidth;
        float viewportHeight = camera.viewportHeight;

        float cameraX = MathUtils.clamp(playerX, viewportWidth / 2f, currentMapWidthInPixels - viewportWidth / 2f);
        float cameraY = MathUtils.clamp(playerY, viewportHeight / 2f, currentMapHeightInPixels - viewportHeight / 2f);

        camera.position.set(cameraX, cameraY, 0);
        camera.update();

        Gdx.gl.glClearColor(0.2f, 0.5f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // ترسیم تایل‌ها
        for (int y = 0; y < currentMap.length; y++) {
            for (int x = 0; x < currentMap[y].length; x++) {
                Tile tile = currentMap[y][x];
                Texture texture = textureManager.getTexture(tile.getType());
                batch.draw(texture, x * TILE_SIZE, y * TILE_SIZE);
            }
        }

        // ترسیم Overlay
        for (int y = currentMap.length - 1; y >= 0; y--) {
            for (int x = 0; x < currentMap[y].length; x++) {
                tileRenderer.renderTile(batch, currentMap[y][x], x * TILE_SIZE, y * TILE_SIZE);
            }
        }

        // ترسیم بازیکن
        player.getPlayerSprite().setPosition(playerX, playerY);
        player.getPlayerSprite().draw(batch);

        batch.end();

        handlePlayerInput();
    }

    public void handlePlayerInput(){
        Player player = App.getActiveGame().getCurrentPlayer();

        float nextX = player.getX();
        float nextY = player.getY();

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            nextY += 10;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)){
            nextX += 10;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S)){
            nextY -= 10;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)){
            nextX -= 10;
            player.getPlayerSprite().flip(true, false);
        }

        nextX = MathUtils.clamp(nextX, 0, currentMapWidthInPixels - player.getPlayerSprite().getWidth());
        nextY = MathUtils.clamp(nextY, 0, currentMapHeightInPixels - player.getPlayerSprite().getHeight());

        player.setPosition(new Pos((int) nextX, (int) nextY));

        player.getPlayerSprite().setPosition(nextX, nextY);

        checkMapTransition(player);

    }

    private void checkMapTransition(Player player) {
        float x = player.getX();
        float y = player.getY();

        switch (currentMapType) {
            case FARM1, FARM2 -> {
                if (x <= 0) {
                    setCurrentMap(MapType.TOWN);
                    Pos pos = new Pos(currentMapWidthInPixels - TILE_SIZE, (int) y);
                    player.setPosition(pos);
                } else if (x >= currentMapWidthInPixels - TILE_SIZE) {
                    setCurrentMap(MapType.TOWN);
                    Pos pos = new Pos(TILE_SIZE, (int) y);
                    player.setPosition(pos);
                }
            }

            case TOWN -> {
                if (y <= 0) {
                    setCurrentMap(MapType.NPC_VILLAGE);
                    Pos pos = new Pos((int) x, currentMapHeightInPixels - TILE_SIZE);
                    player.setPosition(pos);
                } else if (y >= currentMapHeightInPixels - TILE_SIZE) {
                    // برگرد به مزرعه اول یا دوم (می‌تونی ذخیره کنی قبلاً کجا بودی)
                    setCurrentMap(MapType.FARM1); // یا FARM2
                    Pos pos = new Pos((int) x, TILE_SIZE);
                    player.setPosition(pos);
                }
            }

            case NPC_VILLAGE -> {
                if (y >= currentMapHeightInPixels - TILE_SIZE) {
                    setCurrentMap(MapType.TOWN);
                    player.setPosition(new Pos((int) x, TILE_SIZE));
                }
            }
        }
    }


    public void setCurrentMap(MapType mapType) {
        this.currentMapType = mapType;
        updateMapSize();
    }

    public Tile[][] getCurrentMap() {
        return maps.get(currentMapType);
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
