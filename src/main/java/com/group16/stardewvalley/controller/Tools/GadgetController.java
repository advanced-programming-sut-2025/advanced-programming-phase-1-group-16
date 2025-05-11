package com.group16.stardewvalley.controller.Tools;

import com.group16.stardewvalley.model.app.*;
import com.group16.stardewvalley.model.user.*;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.map.*;
import com.group16.stardewvalley.model.Tools.*;

import javax.swing.text.Position;
import  java.util.Map;
import java.util.regex.Matcher;

public class GadgetController {
    private final Game game;

    public GadgetController(Game game) {
        this.game = game;
    }

    public Result equip(Matcher matcher) {
        String toolName = matcher.group("toolName");
        Player currentPlayer = game.getCurrentPlayer();

        if (currentPlayer.getInventory().findToolByName(toolName) == null) {
            return new Result(false, "Oops! You don't have this tool (~_^) ");
        }

        Gadget toolToEquip = game.getCurrentPlayer().getInventory().findToolByName(toolName);
        currentPlayer.equip(toolToEquip);

        return new Result(true, "Now you have " + toolName + " equipped!");
    }

    public Result showCurrentTool() {
        Gadget targetTool = game.getCurrentPlayer().getCurrentEquipment();
        if (targetTool == null) {
            return new Result(false, "you don't have any gadget in your hand (~_^)");
        }
        return new Result(true, targetTool.getName());
    }
    public Result showAvailableTools() {
        Map<Gadget, Integer> tools = game.getCurrentPlayer().getInventory().getTools();

        if (tools.isEmpty()) {
            return new Result(false, "Your inventory is empty! (•_•)");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Available tools:\n");

        for (Map.Entry<Gadget, Integer> entry : tools.entrySet()) {
            Gadget tool = entry.getKey();
            int count = entry.getValue();
            sb.append("- ").append(tool.getName()).append("\n");
        }

        return new Result(true, sb.toString());
    }

    public Result useTool(Matcher matcher) {
        String direction = matcher.group("direction");
        Player currentPlayer = game.getCurrentPlayer();
        Gadget gadget = currentPlayer.getCurrentEquipment();
        Direction dir = Direction.fromString(direction);
        Tile targetTile = dir.applyPosition(game);

        if (targetTile == null) {
            return new Result(false, "Out of bound");
        }

        gadget.use(targetTile, game);

    }

    // یا هیچی روی اون موقعیت سازگار نیست یا یکی از این کارهای ابزاره هست
    private ToolAction whichOneIsSuitable() {

    }

    private boolean hasEnoughEnergy(Gadget gadget, ToolAction action) {
        Player currentPlayer = game.getCurrentPlayer();
        int requiredEnergy = ToolDataManager.getEnergyConsumption(gadget.getClass().getSimpleName()
                .toLowerCase(), gadget.getMaterial());
        return requiredEnergy > currentPlayer.getEnergy();

    }

    //

}
