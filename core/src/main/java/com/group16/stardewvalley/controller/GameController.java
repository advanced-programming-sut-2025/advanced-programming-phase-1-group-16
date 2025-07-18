package com.group16.stardewvalley.controller;


import com.badlogic.gdx.Input;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
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
        int speed = 1;

        switch (keycode) {
            case Input.Keys.UP:
                nextY += speed;
                up = true;
                break;
            case Input.Keys.RIGHT:
                nextX += speed;
                right = true;
                break;
            case Input.Keys.DOWN:
                nextY -= speed;
                down = true;
                break;
            case Input.Keys.LEFT:
                nextX -= speed;
                left = true;
                break;
            case Input.Keys.M:
                showMiniMap = !showMiniMap;
            case Input.Keys.C:
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
        System.out.println(player.getEnergy());
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
