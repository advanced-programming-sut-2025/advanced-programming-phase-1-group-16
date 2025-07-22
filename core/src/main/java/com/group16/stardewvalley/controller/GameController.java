package com.group16.stardewvalley.controller;


import com.badlogic.gdx.Input;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.map.Direction;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;

import static com.group16.stardewvalley.view.graphics.GameScreen.showMiniMap;

public class GameController {
    private final PlayersController playersController;
    private final MapController mapController;

    public GameController() {
        this.playersController = new PlayersController();
        this.mapController = new MapController();
    }

    public void update(float delta) {
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
            case Input.Keys.C:
                if (player.getCurrentEquipment() != null) {
                    int targetY = player.getPosition().getY() + player.getCurrentDirection().getyDelta();
                    int targetX = player.getPosition().getX() + player.getCurrentDirection().getxDelta();
                    Tile targetTile = App.getActiveGame().getMap()[targetY][targetX];
                    Result resultOfUsingGadget = player.getCurrentEquipment().use(targetTile, App.getActiveGame());
                    System.out.println(resultOfUsingGadget);
                }
                if (player.getCurrentThing() != null) {
                    int targetY = player.getPosition().getY() + player.getCurrentDirection().getyDelta();
                    int targetX = player.getPosition().getX() + player.getCurrentDirection().getxDelta();
                    Tile targetTile = App.getActiveGame().getMap()[targetY][targetX];
                    targetTile.setItem(player.getCurrentThing());
                    player.setCurrentThing(null);
                }
                return true;
            case Input.Keys.X:
                return true;
            case Input.Keys.E:
            case Input.Keys.ESCAPE:
                return true;
            case Input.Keys.F:
                return true;
            case Input.Keys.TAB:
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
