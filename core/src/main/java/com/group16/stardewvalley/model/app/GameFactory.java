package com.group16.stardewvalley.model.app;

import com.group16.stardewvalley.Message;
import com.group16.stardewvalley.controller.MessageFactory;
import com.group16.stardewvalley.model.DTO.GameClientDTO;
import com.group16.stardewvalley.model.DTO.PlayerDTO;
import com.group16.stardewvalley.model.DTO.TileDTO;
import com.group16.stardewvalley.model.agriculture.Crop;
import com.group16.stardewvalley.model.agriculture.CropType;
import com.group16.stardewvalley.model.agriculture.Tree;
import com.group16.stardewvalley.model.agriculture.TreeType;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.SecurityQuestions;
import com.group16.stardewvalley.model.user.User;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameFactory {

    public static Game fromClientDTO(GameClientDTO dto) {

        // بازیکنان
        ArrayList<Player> players = new ArrayList<>();
        Player creator = null;
        for (PlayerDTO playerData : dto.getPlayers()) {
            String username = playerData.getUsername();
            User user = MessageFactory.getUser(username);
            if (user == null) continue;
            Player player = new Player(user);
            player.setPosition(new Pos(playerData.getX(), playerData.getY()));
            player.setPlayerGraphics(playerData.getCharacterPath(), 48, 64);
            if (username.equals(dto.getCreatorUsername())) {
                creator = player;
            }
        }
        Game game = new Game(creator, players);

        Tile[][] map = new Tile[dto.getMap().length][dto.getMap()[0].length];
        for (int y = 0; y < dto.getMap().length; y++) {
            for (int x = 0; x < dto.getMap()[y].length; x++) {
                map[y][x] = fromTileDTO(dto.getMap()[y][x]);
            }
        }
        game.setMap(map);

        return game;
    }

    private static Tile fromTileDTO(TileDTO dto) {
        Tile tile = new Tile(dto.getType());
        tile.setHasWater(dto.isHasWater());

//        if (dto.getCropName() != null) {
//            CropType cropType = CropType.valueOf(dto.getCropName());
//            Crop crop = new Crop(cropType);
//            crop.setStage(dto.getCropStage());
//            tile.setCrop(crop);
//        }
//        if (dto.getTreeType() != null) {
//            tile.setTree(new Tree(TreeType.valueOf(dto.getTreeType())));
//        }
//        if (dto.getItemName() != null) {
//            tile.setItem(new Item(dto.getItemName()));
//        }
        return tile;
    }
}
