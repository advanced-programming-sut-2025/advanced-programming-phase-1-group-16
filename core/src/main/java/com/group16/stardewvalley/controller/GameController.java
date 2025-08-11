package com.group16.stardewvalley.controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.agriculture.AgricultureController;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.HomeMenuController;
import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Seed;
import com.group16.stardewvalley.model.agriculture.Seeds;
import com.group16.stardewvalley.model.agriculture.Tree;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.*;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.map.Direction;
import com.group16.stardewvalley.model.map.HomeMap;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.tools.Hoe;
import com.group16.stardewvalley.model.tools.Scythe;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.view.graphics.CookingMenu;
import com.group16.stardewvalley.view.graphics.FridgeMenu;

import static com.group16.stardewvalley.controller.menu.HomeMenuController.findIngredient;
import static com.group16.stardewvalley.view.graphics.GameScreen.*;

public class GameController {
    private final PlayersController playersController;
    private final MapController mapController;
    private final AgricultureController agricultureController;
    private final HomeMenuController homeMenuController;
    private CookingMenu cookingMenu;
    private boolean isCookingMenuOpen = false;

    private FridgeMenu fridgeMenu;
    private boolean isFridgeMenuOpen = false;

    private final DragAndDrop dragAndDrop = new DragAndDrop();


    public GameController() {
        this.agricultureController = new AgricultureController();
        this.playersController = new PlayersController();
        this.mapController = new MapController();
        this.homeMenuController = new HomeMenuController();
    }

    public void update(float delta) {
        if (isCookingMenuOpen) return; // بازی آپدیت نشه
        playersController.update(delta);
    }

    public void render() {
        Player player = App.getActiveGame().getCurrentPlayer();
        if (player.isAtHome()){
            player.getHomeMap().render(Main.getBatch());
        } else {
            mapController.drawMap(Main.getBatch());
        }

        playersController.render();

        drawPlayerBuff();

    }

    private void drawPlayerBuff() {
        Player player = App.getActiveGame().getCurrentPlayer();
        BuffType buff = player.getBuffer();

        if (buff != BuffType.NONE) {
            Texture buffTexture = GameAssetManager.getGameAssetManager().getTexture(buff.getTexturePath());

            // موقعیت نمایش: وسط بالای صفحه
            float x = 20;
            float y = Gdx.graphics.getHeight() - 84;  // از بالا 64 پیکسل پایین‌تر، با فاصله


            Main.getBatch().draw(buffTexture, x, y, 64, 64);
        }
    }

    public boolean handleInput(int keycode) {
        Player player = App.getActiveGame().getCurrentPlayer();

        float nextX = player.getX();
        float nextY = player.getY();
        boolean up = false, down = false, left = false, right = false;
        float speed = player.getSpeed();

        switch (keycode) {
            case Input.Keys.NUM_0:
            case Input.Keys.NUM_1:
            case Input.Keys.NUM_2:
            case Input.Keys.NUM_3:
            case Input.Keys.NUM_4:
            case Input.Keys.NUM_5:
            case Input.Keys.NUM_6:
            case Input.Keys.NUM_7:
            case Input.Keys.NUM_8:
            case Input.Keys.NUM_9:
                Main.getMain().getGameScreen().toggleShowInventory();
                return true;
            case Input.Keys.UP:
                player.setCurrentDirection(Direction.UP);
                nextY += speed;
                up = true;
                break;
            case Input.Keys.RIGHT:
                player.setCurrentDirection(Direction.RIGHT);
                nextX += speed;
                right = true;
                break;
            case Input.Keys.DOWN:
                player.setCurrentDirection(Direction.DOWN);
                nextY -= speed;
                down = true;
                break;
            case Input.Keys.LEFT:
                player.setCurrentDirection(Direction.LEFT);
                nextX -= speed;
                left = true;
                break;
            case Input.Keys.M:
                showMiniMap = !showMiniMap;
            case Input.Keys.V:
                if (player.getCurrentEquipment() != null) {
                    int targetY = player.getPosition().getY() + player.getCurrentDirection().getyDelta();
                    int targetX = player.getPosition().getX() + player.getCurrentDirection().getxDelta();
                    if (targetX < 0 || targetY < 0 || targetX > App.getActiveGame().getMapWidth() || targetY > App.getActiveGame().getMapHeight()) {
                        return false;
                    }
                    Tile targetTile = App.getActiveGame().getMap()[targetY][targetX];
                    Result resultOfUsingGadget = player.getCurrentEquipment().use(targetTile, App.getActiveGame());
                    System.out.println(resultOfUsingGadget);
                }
                if (player.getCurrentThing() != null) {
                    Item item = player.getCurrentThing();
                    int targetY = player.getPosition().getY() + player.getCurrentDirection().getyDelta();
                    int targetX = player.getPosition().getX() + player.getCurrentDirection().getxDelta();
                    if (targetX < 0 || targetY < 0 || targetX > App.getActiveGame().getMapWidth() || targetY > App.getActiveGame().getMapHeight()) {
                        return false;
                    }
                    Tile targetTile = App.getActiveGame().getMap()[targetY][targetX];
                    if (item instanceof Seed seed) {
                        Result result = agricultureController.planting(seed, targetX, targetY);
                        if (!result.isSuccessful()) {
                            System.out.println(result);
                        }
                    }
                    else targetTile.setItem(player.getCurrentThing());
                    player.setCurrentThing(null);
                }
                return true;
            case Input.Keys.C:
                if (player.isAtHome()) {
                    if (!isCookingMenuOpen) {
                        cookingMenu = new CookingMenu(
                                GameAssetManager.getGameAssetManager().getSkin(),
                                App.getActiveGame().getCurrentPlayer().getKnownRecipes()
                        );
                        Main.getMain().getGameScreen().getStage().addActor(cookingMenu);
                        Main.getMain().getGameScreen().getStage().addActor(cookingMenu.getTooltip());
                        isCookingMenuOpen = true;
                    } else {
                        cookingMenu.remove();
                        cookingMenu = null;
                        isCookingMenuOpen = false;
                    }
                }

                return true;
            case Input.Keys.X:
                if (player.getCurrentEquipment() == null) {
                    player.equip(new Hoe("hoe",0, "base"));
                } else {
                    player.equip(null);
                    player.setCurrentThing(Seeds.MIXED_SEED);
                }
                return true;
            case Input.Keys.Y:
                player.equip(new Scythe("scythe", 0, "base"));
                return true;
            case Input.Keys.E:
            case Input.Keys.ESCAPE:
                return true;
            case Input.Keys.F:
                System.out.println(agricultureController.fertilizePlant("speed gro", "up"));
                return true;
            case Input.Keys.TAB:
                App.getActiveGame().getTimeDate().advanceDateCheat(1);
                return true;
            case Input.Keys.O:
                Result result = homeMenuController.eat(FoodFactory.tripleShotEspresso().getName());
                if (result.isSuccessful()) {
                    Food food = FoodFactory.tripleShotEspresso();
                    showEatEffect(food.getName(), food.getBuff().getDescription(), food.getEnergy());
                }
                return true;
            case Input.Keys.T:
                Main.getMain().getGameScreen().toggleShowTools();
                return true;
            case Input.Keys.F4:
                return true;
            default:
                return false;
        }

        if (player.isAtHome()) {
            return false;
        }

        Result result = mapController.walk((int) nextX, (int) nextY);
        if (result.isSuccessful()) {
            playersController.move(player, speed, up, down, left, right);
            player.decreaseEnergy(player.getEnergy() * 0.05);
            return true;
        }
        System.out.println(result.message());
        if (result.message().contains("You fainted")) {
            player.setFaintStatus(true);
        }
        return false;
    }

    public void showEatEffect(String foodName, String buff, int energy) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.YELLOW;

        String message = " Ate " + foodName + "\n+" + energy + " Energy";

        if (!buff.isEmpty()) {
            message += "\n" + buff + " Buff Activated!";
        }

        final Label label = new Label(message, labelStyle);
        label.setFontScale(1.2f);
        label.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        label.setAlignment(Align.center);
        label.addAction(Actions.sequence(
            Actions.parallel(
                Actions.moveBy(0, 100, 2f),
                Actions.fadeOut(2f)
            ),
            Actions.removeActor()
        ));

        Main.getMain().getGameScreen().getStage().addActor(label);
    }

    public void handleRightClick(int screenX, int screenY) {
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
        else if (App.getActiveGame().getMap()[tileY][tileX].getType().equals(TileType.Cottage) &&
                !player.isAtHome()) {
            player.setHomeMap(new HomeMap(player));
            player.setAtHome(true);
        } else if (player.isAtHome()) {
            player.setAtHome(false);
        }
    }

    public void handleLeftClick(int screenX, int screenY) {
        Player player = App.getActiveGame().getCurrentPlayer();

        // فقط وقتی بازیکن داخل خونه است
        if (player.isAtHome()) {

            Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
            Vector2 clickPos = new Vector2(worldCoordinates.x, worldCoordinates.y);


            if (player.getHomeMap().isOnFridge(clickPos)) {
                if (!isFridgeMenuOpen) {
                    fridgeMenu = new FridgeMenu(
                            GameAssetManager.getGameAssetManager().getSkin(),
                            player.getFarm().getRefrigerator(),
                            dragAndDrop
                    );
                    Main.getMain().getGameScreen().getStage().addActor(fridgeMenu);
                    isFridgeMenuOpen = true;
                } else {
                    fridgeMenu.remove();
                    fridgeMenu = null;
                    isFridgeMenuOpen = false;
                }
            }
        }
    }

    public DragAndDrop getDragAndDrop() {
        return dragAndDrop;
    }

    public void showErrorPopup(String message) {
        Dialog dialog = new Dialog("error", GameAssetManager.getGameAssetManager().getSkin());
        dialog.text(message);
        dialog.button("Ok");
        dialog.show(Main.getMain().getGameScreen().getStage());
    }

}
