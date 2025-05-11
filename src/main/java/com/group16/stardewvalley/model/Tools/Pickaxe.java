package com.group16.stardewvalley.model.Tools;

import com.group16.stardewvalley.model.Items.Ore;
import com.group16.stardewvalley.model.Items.Stone;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.user.Player;

public class Pickaxe extends Gadget{
    public Pickaxe(String name, String material) {
        super(name);
        this.material = material;
    }

    public int getPrice() {
         return ToolDataManager.getToolPrice("pickaxe", this.material);
    }

    public int getConsumptionEnergy() {
        return ToolDataManager.getEnergyConsumption("pickaxe", this.material);
    }

    public Result use(Tile targetTile, Game game) {
        Player player = game.getCurrentPlayer();
        if (game.getCurrentPlayer().getEnergy() < this.getConsumptionEnergy()) {
            player.decreaseEnergy(this.getConsumptionEnergy());
            player.faint();
            return new Result(false, "Have you not eaten bread today?");
        }
        // زمانی که بیل خورده است
        if (targetTile.getType() == TileType.Plowed) {
            player.decreaseEnergy(this.getConsumptionEnergy());
            targetTile.setType(TileType.Ground);
            return new Result(true, "The earth returns to its natural state.");
        }
        // زمانی که سنگ هست
        if (targetTile.getItem() instanceof Stone) {
            player.decreaseEnergy(this.getConsumptionEnergy());
            targetTile.setItem(null);
            return new Result(true, "Stone shattered! The shovel proves its might.");
        }

        // زمانی که کانی است
        if (targetTile.getItem() instanceof Ore) {
            if (this.material.equalsIgnoreCase("iridium")) {
                player.
            }
        }

        // از بین بردن ایتم هایی که بازیکن روی زمین گذاشته است
        if (targetTile.getItem() != null) {
            player.decreaseEnergy(this.getConsumptionEnergy());
            targetTile.setItem(null);
            return new Result(true, "Cleared! Every player-placed block has been removed.");
        }

        // برای استفاده ی ناموفق یک واحد کمتر از چیزی که میگیری باید مصرف کنی
        player.decreaseEnergy(this.getConsumptionEnergy() - 1);
    return new Result(true, "");
    }
}
