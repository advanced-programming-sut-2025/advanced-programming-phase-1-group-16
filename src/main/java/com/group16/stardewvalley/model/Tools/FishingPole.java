package com.group16.stardewvalley.model.Tools;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;

public class FishingPole extends Gadget{
    public FishingPole(String name, String material) {
        super(name);
        this.material = material;
    }

    public Result use(Tile targetTile, Game game) {

    }
}
