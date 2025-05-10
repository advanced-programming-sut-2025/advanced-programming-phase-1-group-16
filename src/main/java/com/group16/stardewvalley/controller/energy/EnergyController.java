package com.group16.stardewvalley.controller.energy;

import com.group16.stardewvalley.model.Tools.Gadget;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.Result;

import java.util.Map;
import java.util.regex.Matcher;

public class EnergyController {
    private final Game game;

    public EnergyController(Game game) {
        this.game = game;
    }

    public Result show(){
        return new Result(true, Integer.toString(game.getCurrentPlayer().getEnergy()));
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
        game.getCurrentPlayer().setEnergy(value);
        return new Result(true, "");
    }

    public Result inventoryShow() {
        Map<Gadget, Integer> tools = game.getCurrentPlayer().getPlayerInventory().getTools();

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
