package com.group16.stardewvalley.controller.map;

import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.user.Player;

public class MapController {
    public void createMap() {
        Game game = App.getActiveGame();
        TileType[][] map = new TileType[game.getMapHeight()][game.getMapWidth()];
        for (int i = 0; i < game.getMapHeight(); i++) {
            for (int j = 0; j < game.getMapWidth(); j++) {
                map[i][j] = TileType.Ground;
            }
        }
        //مشخص کردن نقطه شروع مزرعه
        Pos[] positions = {
                new Pos(0,0),
                new Pos(game.getMapWidth() - 70, 2),
                new Pos(70, game.getMapHeight() - 70),
                new Pos(game.getMapWidth() - 70, game.getMapHeight() - 70)
        };
        int index = 0;
        for (Player player : game.getPlayers()) {
            player.getFarm().setStartPosition(positions[index]);
            index++;
        }
        for (Player player : game.getPlayers()) {
            for (int i = 0; i < player.getFarm().getType().getHeight(); i++) {
                if (player.getFarm().getType().getWidth() >= 0)
                    System.arraycopy(player.getFarm().getType().getTiles()[i], 0, map[i + player.getFarm().getStartPosition().getY()], player.getFarm().getStartPosition().getX(), player.getFarm().getType().getWidth());
            }
        }
        game.setMap(map);
    }
}
