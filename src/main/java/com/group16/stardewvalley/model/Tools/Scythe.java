package com.group16.stardewvalley.model.Tools;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;

public class Scythe extends Gadget{
    public Scythe(String name, String material) {
        super(name);
        this.material = material;
    }

    public int getPrice() {
        return ToolDataManager.getToolPrice("Scythe", this.material)
    }

    public int getConsumptionEnergy() {
        return ToolDataManager.getEnergyConsumption("Scythe", this.material);
    }

    @Override
    public Result use(Tile targetTile, Game game) {
        Player player = game.getCurrentPlayer();
        // خطای انرژی
        if (player.getEnergy() < 2) {
            player.decreaseEnergy(2);
            player.faint();
            return new Result(false, "Have you not eaten bread today?");
        }

        // فقط برداشت محصولات
        if (targetTile.getCrop() != null) {
            if (!targetTile.getCrop().isColossal()) {
                player.getInventory().addCrop(targetTile.getCrop(), 1);
                player.decreaseEnergy(2);
                player.addFarmingAbilityScore(5);
            } else {
                //TODO اتنا
                player.decreaseEnergy(2);
            }
        }

        // هیچکار نمیتونسته باهاش بکنه
        player.decreaseEnergy(2);
        return new Result(false, "You cannot use the Scythe in this area!×");


    }
}
