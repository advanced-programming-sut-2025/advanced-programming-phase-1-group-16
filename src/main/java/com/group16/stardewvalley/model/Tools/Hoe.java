package com.group16.stardewvalley.model.Tools;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.user.Player;

import java.util.Map;

public class Hoe extends Gadget {

    public Hoe(String name, String material) {
        super(name);
        this.material = material;
    }

    public int getPrice() {
       return ToolDataManager.getToolPrice("Hoe", this.material);
    }

    public int getEnoughLevel() {
        // In your JSON, enoughLevel is only defined for fishing pole
        // You might want to add it for hoes too or return a default value
        return 0; // Default level
    }

    public int getConsumptionEnergy() {
       return ToolDataManager.getEnergyConsumption("Hoe", this.material);
    }

    public Result use(Tile targetTile, Game game) {
        Player player = game.getCurrentPlayer();
        if (player.getEnergy() < this.getConsumptionEnergy()) {
            player.decreaseEnergy(this.getConsumptionEnergy());
            player.faint();
            return new Result(false, "Have you not eaten bread today?");
        }

        if (targetTile.getType() != TileType.Ground) {
            player.decreaseEnergy(this.getConsumptionEnergy());
            return new Result(false, "Invalid operation: Hoe can only be used on empty dirt!");
        }

        targetTile.setType(TileType.Plowed);
        player.decreaseEnergy(this.getConsumptionEnergy());
        return new Result(true, "Success! Dirt dug up cleanly.");
    }
}