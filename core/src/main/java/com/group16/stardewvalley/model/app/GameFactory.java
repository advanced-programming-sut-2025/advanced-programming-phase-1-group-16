package com.group16.stardewvalley.model.app;

import com.group16.stardewvalley.controller.MessageFactory;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.model.DTO.GameClientDTO;
import com.group16.stardewvalley.model.DTO.PlayerDTO;
import com.group16.stardewvalley.model.DTO.TileDTO;
import com.group16.stardewvalley.model.map.Farm;
import com.group16.stardewvalley.model.map.FarmType;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.User;

import java.util.ArrayList;

public class GameFactory {

    public static Game fromClientDTO(GameClientDTO dto) {
        GameMenuController gameMenuController = new GameMenuController();
        System.out.println(dto.getPlayers().size() + " players connected in factory");

        ArrayList<Player> players = new ArrayList<>();
        Player creator = null;
        for (PlayerDTO playerData : dto.getPlayers()) {
            System.out.println("player " + playerData.getUsername() + "is here");
            String username = playerData.getUsername();
            User user = MessageFactory.getUser(username);
            if (user == null) {
                System.out.println("user " + username + " not found");
                continue;
            }

            Player player = new Player(user);
            player.setPosition(new Pos(playerData.getX(), playerData.getY()));
            player.setGraphicPath(playerData.getCharacterPath());
            if (playerData.getFarmType().equalsIgnoreCase("small")){
                gameMenuController.chooseFarm(player, "1");
            } else {
                gameMenuController.chooseFarm(player, "2");
            }
            player.getFarm().setStartPosition(new Pos(playerData.getFarmX(), playerData.getFarmY()));
            if (username.equals(dto.getCreatorUsername())) {
                creator = player;
            }
            players.add(player);
        }
        Game game = new Game(creator, players);
        for (Player player : game.getPlayers()) {
            if (player.getUser().getUsername().equals(App.getLoggedInUser().getUsername())) {
                game.setCurrentPlayer(player);
            }
        }
        MapController mapController = new MapController();
        mapController.createMap(game);

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
