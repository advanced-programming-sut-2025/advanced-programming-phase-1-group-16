package com.group16.stardewvalley.model.Tools;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;

public class FishingPole extends Gadget{
    public FishingPole(String name, String material) {
        super(name);
        this.material = material;
    }

    public int getPrice() {
        return ToolDataManager.getToolPrice("Fishing pole", this.material);
    }

    public int getConsumptionEnergy() {
        return ToolDataManager.getEnergyConsumption("Fishing pole", this.material);
    }

    public Result use(Tile targetTile, Game game) {
        Player player = game.getCurrentPlayer();
        int requiredEnergy = this.getConsumptionEnergy();
        if (player.getFishingAbilityLevel() == 4) {
            requiredEnergy--;
        }

        // خطای انرژی
        if (player.getEnergy() < requiredEnergy) {
            player.decreaseEnergy(requiredEnergy);
            player.faint();
            return new Result(false, "Have you not eaten bread today?");
        }

        // بتواند ماهی گیری کند ---> نیلی
        if (true) {
            player.decreaseEnergy(requiredEnergy);

        }

        // هیچکار نتواند بکند : روی زمین خالی مثلا چوب ماهیگیری انداخته
        player.decreaseEnergy(requiredEnergy - 1);
        return new Result(false, "You cannot use the Fishing Pole in this area!×");
    }
}
