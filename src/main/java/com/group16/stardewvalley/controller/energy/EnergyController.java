package com.stardewvalley.Energy;

import com.stardewvalley.*;
import com.stardewvalley.Tools.Gadget;

import java.util.Map;
import java.util.regex.Matcher;

public class EnergyController {

    public Result show(){
        return new Result(true, Integer.toString(Game.getCurrentPlayer().getEnergy()));
    }

    public Result setEnergy(Matcher matcher) {
        String valueStr = matcher.group("value");
        int value;
        try {
            value = Integer.parseInt(valueStr);
        } catch (NumberFormatException e) {
            return new Result(false, "Seriously? You call this energy? :/");
        }
        if (value < 0) {
            return new Result(false, "Seriously? You call this energy? :/");
        }
        Game.getCurrentPlayer().setEnergy(value);
        return new Result(true, "");
    }

    public Result inventoryShow() {
        Map<Gadget, Integer> tools = Game.getCurrentPlayer().getPlayerInventory().getTools();

        if (tools.isEmpty()) {
            return new Result(false, "Your inventory is empty! (•_•)");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Available tools:\n");

        for (Map.Entry<Gadget, Integer> entry : tools.entrySet()) {
            Gadget tool = entry.getKey();
            int count = entry.getValue();
            sb.append("- ").append(tool.getName()).append(")\n");
        }

        return new Result(true, sb.toString());
        // همین ولی thing رو هم در نظر بگیره
    }
}
