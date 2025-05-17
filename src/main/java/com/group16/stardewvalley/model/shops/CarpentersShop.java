package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.items.Stone;
import com.group16.stardewvalley.model.items.Wood;
import com.group16.stardewvalley.model.map.PlaceType;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.user.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.BackingStoreException;

import static com.group16.stardewvalley.controller.map.MapController.isPlayerInFarm;

public class CarpentersShop extends Shop{
    private static CarpentersShop instance;
    public static CarpentersShop getInstance() {
        if (instance == null) {
            return instance;
        }
        return null;
    }
    public CarpentersShop() {
        super("Carpenter's Shop", "Robin", 9, 8);
        initializeItems();
    }

    public void initializeItems() {
        addItem(new Wood("Wood"), Integer.MAX_VALUE);
        addItem(new Stone("Stone"), Integer.MAX_VALUE);
        addItem(new Building("Barn"), 1);
        addItem(new Building("Big Barn"), 1);
        addItem(new Building("Deluxe Barn"), 1);
        addItem(new Building("Coop"), 1);
        addItem(new Building("Big Coop"), 1);
        addItem(new Building("Deluxe Coop"), 1);
        addItem(new Building("Well"), 1);
        addItem(new Building("Shipping Bin"), 1);

    private int barn;
    private int bigBarn;
    private int deluxeBarn;
    private int coop;


    public Result buildCoop_Barn(String buildingName, int x, int y) {
        Player player = App.getActiveGame().getCurrentPlayer();
        Game game = App.getActiveGame();
        Map<Item, Integer> items  = player.getInventory().getItems();

        //check if player is inside farm
        if(!isPlayerInFarm(player)){
            return new Result(false, "You are not inside farm");
        }

        //create building
        BuildingType buildingType = null;
        for (BuildingType type : BuildingType.values()) {
            if (type.getName().equalsIgnoreCase(buildingName)) {
                buildingType = type;
            }
        }

        //check if name is valid
        if (buildingName == null) {
            return new Result(false, "no building found with the name " + buildingName);
        }

        //check if ground is empty
        for (int i = y; i < y + buildingType.getLength() ; i++) {
            for (int j = x; j < x + buildingType.getWidth(); j++) {
                if(game.getMap()[y][x].getItem() != null){
                    return new Result(false, "there is something on the ground");
                }
            }
        }

        //check if it has enough money
        if (player.getCoin() < buildingType.getCost()){
            return new Result(false, "you dont have enough coin");
        }

        Item woodItem = null;
        for (Item item : items.keySet()) {
            if (item instanceof Wood) {
                woodItem = item;
                int woodAmount = items.get(item);
                if (woodAmount < buildingType.getWoodCost()) {
                    return new Result(false, "You don't have enough wood");
                }
            }
        }

        Item stoneItem =  null;
        for (Item item : items.keySet()) {
            if (item instanceof Stone) {
                stoneItem = item;
                int stoneAmount = items.get(item);
                if (stoneAmount < buildingType.getStoneCost()) {
                    return new Result(false, "You don't have enough stone");
                }
            }
        }

        //everything ok, lets build
        Pos buildingPos = new Pos(x, y);
        Item newBuilding = new Building(buildingType.getName(), buildingType, buildingPos);

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
