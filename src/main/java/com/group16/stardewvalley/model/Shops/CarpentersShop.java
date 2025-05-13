package com.group16.stardewvalley.model.Shops;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.items.Stone;
import com.group16.stardewvalley.model.items.Wood;
import com.group16.stardewvalley.model.user.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.BackingStoreException;

import static com.group16.stardewvalley.controller.map.MapController.isPlayerInFarm;

public class CarpentersShop extends com.group16.stardewvalley.model.shops.Shop {
    public CarpentersShop() {
        super("Carpenter's Shop", "Robin", 9, 8);
    }

    public void initializeItems() {
        addItem(new Wood("Wood"), Integer.MAX_VALUE);
        addItem(new Stone("Stone"), Integer.MAX_VALUE);
    }

    private int barn;
    private int bigBarn;
    private int deluxeBarn;
    private int coop;


    public Result buildCoop_Barn(String buildingName, int x, int y) {
        Player player = App.getActiveGame().getCurrentPlayer();
        Game game = App.getActiveGame();
        Map<Item, Integer> items  = player.getInventory().getItems();

            if(!isPlayerInFarm(player)){
            return new Result(false, "You are not inside farm");
        }



        BuildingType buildingType = null;
        for (BuildingType type : BuildingType.values()) {
            if (type.getName().equalsIgnoreCase(buildingName)) {
                buildingType = type;
            }
        }

        if (buildingName == null) {
            return new Result(false, "no building found with the name " + buildingName);
        }


        for (int i = y; i < y + buildingType.getLength() ; i++) {
            for (int j = x; j < x + buildingType.getWidth(); j++) {
                if(game.getMap()[y][x].getItem() != null){
                    return new Result(false, "there is something on the ground");
                }

            }

        }




        if (player.getCoin() < buildingType.getCost()){
            return new Result(false, "you dont have enough coin");
        }

        Item woodItem = new Wood("myWood");
        if(items.get(woodItem) < buildingType.getWoodCost()){
            return new Result(false, "you dont have enough wood");
        }
        Item stoneItem = new Stone("myStone");
        if (items.get(stoneItem) < buildingType.getStoneCost()){
            return new Result(false, "you dont have enough stone");
        }

        //everything ok, lets build
        Item newBuilding = new Building(buildingType.getName(), buildingType);

        //remove wood/stone from inventory
        items.compute(woodItem, (k, currentAmount) -> currentAmount - 350);
        items.compute(stoneItem, (k, currentAmount) -> currentAmount - 150);

        //place building
        for (int i = y; i < y + buildingType.getLength() ; i++) {
            for (int j = x; j < x + buildingType.getWidth(); j++) {
                game.getMap()[y][x].setItem(newBuilding);

            }

        }

        return new Result(true, newBuilding.getName() + "build successfully");

    }



}
