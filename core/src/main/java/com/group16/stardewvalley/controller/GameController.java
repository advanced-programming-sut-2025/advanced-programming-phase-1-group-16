package com.group16.stardewvalley.controller;


import com.badlogic.gdx.Input;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.agriculture.AgricultureController;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Seed;
import com.group16.stardewvalley.model.agriculture.Seeds;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.map.Direction;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.tools.Hoe;
import com.group16.stardewvalley.model.tools.Scythe;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.view.graphics.CookingMenu;

import static com.group16.stardewvalley.view.graphics.GameScreen.showMiniMap;

public class GameController {
    private final PlayersController playersController;
    private final MapController mapController;
    private final AgricultureController agricultureController;
    private CookingMenu cookingMenu;
    private boolean isCookingMenuOpen = false;

    public GameController() {
        this.agricultureController = new AgricultureController();
        this.playersController = new PlayersController();
        this.mapController = new MapController();
    }

    public void update(float delta) {
        if (isCookingMenuOpen) return; // بازی آپدیت نشه
        playersController.update(delta);
    }

    public void render() {
        mapController.drawMap(Main.getBatch());
        playersController.render();

    }

    public boolean handleInput(int keycode) {
        Player player = App.getActiveGame().getCurrentPlayer();

        float nextX = player.getX();
        float nextY = player.getY();
        boolean up = false, down = false, left = false, right = false;
        float speed = player.getSpeed();

        switch (keycode) {
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
            case Input.Keys.T:
                return true;
            case Input.Keys.F4:
                return true;
            default:
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
}
