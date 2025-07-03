package com.group16.stardewvalley.model.map;

import com.group16.stardewvalley.model.shops.Shop;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;

public enum Location {

    NPCFarm,
    //no special place:
    Game,

    Farm,
    MiningLevel,
    FarmingLevel,
    Foraging,
    PierresGeneralStore,
    FishShop,
    Blacksmith,
    CarpentersShop,
    JojaMart,
    MarniesRanch,
    TheStardropSaloon;


    private final Game game = App.getActiveGame();

    public Shop getShopByLocation() {
        switch(this) {
            case PierresGeneralStore:
                return game.getPierresGeneralStore();
            case FishShop:
                return game.getFishShop();
            case Blacksmith:
                return game.getBlacksmith();
            case CarpentersShop:
                return game.getCarpentersShop();
            case JojaMart:
                return game.getJojaMart();
            case MarniesRanch:
                return game.getMarniesRanch();
            case TheStardropSaloon:
                return game.getTheStardropSaloon();
            default:
                return null;
        }
    }
}