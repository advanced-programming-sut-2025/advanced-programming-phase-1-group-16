package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.GameController;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Tree;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.graphics.TileRenderer;
import com.group16.stardewvalley.model.map.TileTextureManager;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.view.menuGraphics.PreGameMenuView;

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
    private Skin skin = GameAssetManager.getGameAssetManager().getSkin();
    private Table pauseMenu;
    private boolean isPaused = false;

    private ClockHUD clockHUD;


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
        batch = Main.getBatch();
        textureManager = new TileTextureManager();
        tileRenderer = new TileRenderer();
        Gdx.input.setInputProcessor(new InputMultiplexer(this, stage = new Stage()));

        clockHUD = new ClockHUD();





        // === Pause Button ===
//        TextButton pauseButton = new TextButton("Pause", GameAssetManager.getGameAssetManager().getSkin());
//        pauseButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                togglePause();
//            }
//        });
//
//        Table topLeft = new Table();
//        topLeft.top().left().setFillParent(true);
//        topLeft.add(pauseButton).pad(10).padLeft(50).padTop(50);
//        stage.addActor(topLeft);
//
//        createPauseMenu();
    }


    @Override
    public void render(float delta) {
        if (!isPaused) {
            controller.update(delta);
            handleTurn();
        }

        if (showMiniMap) {
            setCameraForMiniMap();
        } else {
            setCameraForMap();
        }

        Gdx.gl.glClearColor(0.2f, 0.5f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        controller.render();
        float clockX = camera.position.x + camera.viewportWidth / 2f - 80; // top right corner-ish
        float clockY = camera.position.y + camera.viewportHeight / 2f - 80;

        clockHUD.render(batch, clockX, clockY);

        batch.end();

        stage.act(delta);
        stage.draw();
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

        // For example: 30 seconds real time = 1 hour game time
        if (totalGameTime >= 10f) {
            TimeDate.getInstance(App.getActiveGame()).advanceOneHour(); // advance game time
            System.out.println(TimeDate.getInstance(App.getActiveGame()).getDateTime());

            totalGameTime = 0f;
            App.getActiveGame().nextTurn(); // if needed for other game state updates
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
        stage.dispose();
        clockHUD.dispose();

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
                    Result result = player.getInventory().addItem(new FoodIngredient(fruitName, tree.getFruitSellPrice(), ingredient), 4);
                }
            }
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

    private void createPauseMenu() {
        pauseMenu = new Table();
        pauseMenu.setFillParent(true);
        pauseMenu.center();

        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                togglePause(); // resume game
            }
        });

        TextButton mainMenuButton = new TextButton("Main Menu", skin);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();

                // ✅ Dispose current screen and switch to PreGameMenuView
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new PreGameMenuView(
                    new GameMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin()
                ));
            }
        });

        pauseMenu.add(new Label("Game Paused", skin)).padBottom(20).row();
        pauseMenu.add(continueButton).width(250).pad(10).row();
        pauseMenu.add(mainMenuButton).width(250).pad(10).row();

        pauseMenu.setVisible(false);
        stage.addActor(pauseMenu);
    }


    private void togglePause() {
        isPaused = !isPaused;
        pauseMenu.setVisible(isPaused);
    }

}
