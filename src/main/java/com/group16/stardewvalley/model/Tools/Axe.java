package com.group16.stardewvalley.model.Tools;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;

public class Axe extends Gadget{
    public Axe(String name, String material) {
        super(name);
        this.material = material;
    }

    public int getPrice() {

    }

    public int getEnoughLevel() {

    }

    public int getConsumptionEnergy() {
        return ToolDataManager.getEnergyConsumption("axe", this.material);
    }

    public Result use(Tile targetTile, Game game) {
        Player player = game.getCurrentPlayer();
        // خطای انرزی
        if (game.getCurrentPlayer().getEnergy() < this.getConsumptionEnergy()) {
            player.decreaseEnergy(this.getConsumptionEnergy());
            player.faint();
            return new Result(false, "Have you not eaten bread today?");
        }
        if ()

    }



}
