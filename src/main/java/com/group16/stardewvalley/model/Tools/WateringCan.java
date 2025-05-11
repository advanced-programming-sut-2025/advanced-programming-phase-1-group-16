package com.group16.stardewvalley.model.Tools;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;

public class WateringCan extends Gadget{
    public WateringCan(String name, String material) {
        super(name);
        this.material = material;
    }

    @Override
    public int getPrice() {

    }

    public int getConsumptionEnergy() {

    }

    @Override
    public Result use(Tile targetTile, Game game) {

    }
}
