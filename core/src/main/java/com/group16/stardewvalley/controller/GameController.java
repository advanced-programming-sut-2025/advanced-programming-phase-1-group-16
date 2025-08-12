package com.group16.stardewvalley.controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.agriculture.AgricultureController;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.HomeMenuController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Seed;
import com.group16.stardewvalley.model.agriculture.Seeds;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.BuffType;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodFactory;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.map.Direction;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.tools.Hoe;
import com.group16.stardewvalley.model.tools.Scythe;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.view.graphics.CookingMenu;
import com.group16.stardewvalley.view.graphics.CraftMenu;

import static com.group16.stardewvalley.view.graphics.GameScreen.showMiniMap;

public class GameController {
    private final PlayersController playersController;
    private final MapController mapController;
    private final AgricultureController agricultureController;
    private final HomeMenuController homeMenuController;
    private CookingMenu cookingMenu;
    private boolean isCookingMenuOpen = false;
    private CraftMenu craftMenu;
    private boolean isCraftingMenuOpen = false;
    public static final float ENERGYSCALE = 0.0005f;
    private boolean upPressed, downPressed, leftPressed, rightPressed;

    public GameController() {
        this.agricultureController = new AgricultureController();
        this.playersController = new PlayersController();
        this.mapController = new MapController();
        this.homeMenuController = new HomeMenuController();
    }

//    public void update(float delta) {
//        if (isCookingMenuOpen) return; // بازی آپدیت نشه
//        playersController.update(delta);
//    }

    public void update(float delta) {
        if (isCookingMenuOpen) return;

        handleContinuousMovement(delta);
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
            //MOVEMENT-1
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

                //MOVEMENT-2
            case Input.Keys.W:
                player.setCurrentDirection(Direction.UP);
                nextY += speed;
                up = true;
                break;
            case Input.Keys.D:
                player.setCurrentDirection(Direction.RIGHT);
                nextX += speed;
                right = true;
                break;
            case Input.Keys.S:
                player.setCurrentDirection(Direction.DOWN);
                nextY -= speed;
                down = true;
                break;
            case Input.Keys.A:
                player.setCurrentDirection(Direction.LEFT);
                nextX -= speed;
                left = true;
                break;


            case Input.Keys.M:
                //minimize and maximize map
                showMiniMap = !showMiniMap;
                return true;

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
                //cooking menu
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
            case Input.Keys.B:
                //Crafting menu
                if (!isCraftingMenuOpen) {
                    craftMenu = new CraftMenu(
                        GameAssetManager.getGameAssetManager().getSkin(),
                        App.getActiveGame().getCurrentPlayer().getInventory().getCraftingRecipes());
                    Main.getMain().getGameScreen().getStage().addActor(craftMenu);
                    Main.getMain().getGameScreen().getStage().addActor(craftMenu.getTooltip());
                    isCraftingMenuOpen = true;
                } else {
                    craftMenu.remove();
                    craftMenu = null;
                    isCraftingMenuOpen = false;
                }
                return true;

            //TODO: cheat codes:
            case Input.Keys.TAB:
                //time cheat code: +1 day
                TimeDate.getInstance(App.getActiveGame()).advanceDateCheat(1);
                return true;
            case Input.Keys.CAPS_LOCK:
                //next turn cheat code
                App.getActiveGame().nextTurn();
                return true;
            case Input.Keys.G:
                //energy cheat code +200
                App.getActiveGame().getCurrentPlayer().increaseEnergy(200);
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
                //TODO: for inventory menu
                return true;


            case Input.Keys.F:
                System.out.println(agricultureController.fertilizePlant("speed gro", "up"));
                return true;
            case Input.Keys.T:
                Result result = homeMenuController.eat(FoodFactory.tripleShotEspresso().getName());
                if (result.isSuccessful()) {
                    Food food = FoodFactory.tripleShotEspresso();
                    showEatEffect(food.getName(), food.getBuff().getDescription(), food.getEnergy());
                }
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
            player.decreaseEnergy(player.getEnergy() * ENERGYSCALE);
            return true;
        }
        System.out.println(result.message());
        if (result.message().contains("You fainted")) {
            player.setFaintStatus(true);
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
            case Input.Keys.W:
                upPressed = true;
                return true;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                downPressed = true;
                return true;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                leftPressed = true;
                return true;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                rightPressed = true;
                return true;
        }
        return handleInput(keycode); // Still handle one-time actions
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
            case Input.Keys.W:
                upPressed = false;
                return true;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                downPressed = false;
                return true;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                leftPressed = false;
                return true;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                rightPressed = false;
                return true;
        }
        return false;
    }


    private void handleContinuousMovement(float delta) {
        Player player = App.getActiveGame().getCurrentPlayer();
        float speed = player.getSpeed();

        float nextX = player.getX();
        float nextY = player.getY();

        boolean moved = false;
        if (upPressed) {
            player.setCurrentDirection(Direction.UP);
            nextY += speed;
            moved = true;
        }
        if (downPressed) {
            player.setCurrentDirection(Direction.DOWN);
            nextY -= speed;
            moved = true;
        }
        if (leftPressed) {
            player.setCurrentDirection(Direction.LEFT);
            nextX -= speed;
            moved = true;
        }
        if (rightPressed) {
            player.setCurrentDirection(Direction.RIGHT);
            nextX += speed;
            moved = true;
        }

        if (moved && !player.isAtHome()) {
            Result result = mapController.walk((int) nextX, (int) nextY);
            if (result.isSuccessful()) {
                playersController.move(player, speed, upPressed, downPressed, leftPressed, rightPressed);
                player.decreaseEnergy(player.getEnergy() * ENERGYSCALE);
            } else if (result.message().contains("You fainted")) {
                player.setFaintStatus(true);
            }
        }
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

}
