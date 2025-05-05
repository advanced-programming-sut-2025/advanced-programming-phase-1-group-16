package com.group16.stardewvalley.model.Tools;

import com.group16.stardewvalley.model.app.*;
import com.group16.stardewvalley.model.user.*;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.map.*;

import java.util.Map;
import java.util.regex.Matcher;

public class GadgetController {
    private final Game game;
    public GadgetController(Game game) {
        this.game = game;
    }

    public Result equip(Matcher matcher) {
        String toolName = matcher.group("toolName");
        Player currentPlayer = game.getCurrentPlayer();

        // بررسی وجود ابزار در inventory
        if (currentPlayer.getPlayerInventory().findToolByName(toolName) == null) {
            return new Result(false, "Oops! You don't have this tool (~_^) ");
        }

        Gadget toolToEquip = currentPlayer.getPlayerInventory().findToolByName(toolName);
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
    }

    public Result upgradeTool(Matcher matcher) {
        String toolName = matcher.group("toolName");
        if (game.getCurrentPlayer().getPosition() != TileType.BLACKSMITH_SHOP) {
            return new Result(false,"You should be at Blacksmith Shop to upgrade ^ ^")
        }
        Gadget targetGadget = game.getCurrentPlayer().getPlayerInventory().findToolByName(toolName);
        targetGadget.upgrade();
    }

    public Result useTool(Matcher matcher) {
        String direction = matcher.group("direction");
        Gadget gadget = game.getCurrentPlayer().getCurrentEquipment();
        Player currentPlayer = game.getCurrentPlayer();
        gadget.use(currentPlayer.getX(), currentPlayer.getY(), Game.);
        TileType targetPosition = game.getCurrentPlayer().getPosition();



    }

}
