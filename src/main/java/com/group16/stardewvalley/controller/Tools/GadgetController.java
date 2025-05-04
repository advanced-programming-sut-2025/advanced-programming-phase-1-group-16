package com.stardewvalley.Tools;

import com.stardewvalley.Game;
import com.stardewvalley.Player;
import com.stardewvalley.Result;
import com.stardewvalley.TileType;

import javax.swing.text.Position;
import  java.util.Map;
import java.util.regex.Matcher;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class GadgetController {

    public Result equip(Matcher matcher) {
        String toolName = matcher.group("toolName");
        Player currentPlayer = Game.getCurrentPlayer();

        if (currentPlayer.getPlayerInventory().findToolByName(toolName) == null) {
            return new Result(false, "Oops! You don't have this tool (~_^) ");
        }

        Gadget toolToEquip = Game.getCurrentPlayer().getPlayerInventory().findToolByName(toolName);
        currentPlayer.equip(toolToEquip);

        return new Result(true, "Now you have " + toolName + " equipped!");
    }

    public Result showCurrentTool() {
        Gadget targetTool = Game.getCurrentPlayer().getCurrentEquipment();
        if (targetTool == null) {
            return new Result(false, "you don't have any gadget in your hand (~_^)");
        }
        return new Result(true, targetTool.getName());
    }

    public Result showAvailableTools() {
        Map<Gadget, Integer> tools = Game.getCurrentPlayer().getPlayerInventory().getTools();

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

    public Result upgradeTool(Matcher matcher) {
        String toolName = matcher.group("toolName");
        if (Game.getCurrentPlayer().getPosition() != TileType.BLACKSMITH_SHOP) {
            return new Result(false,"You should be at Blacksmith Shop to upgrade ^ ^");
        }
        Gadget targetGadget = Game.getCurrentPlayer().getPlayerInventory().findToolByName(toolName);
        targetGadget.upgrade();
        return new Result(true, "");
    }

    public Result useTool(Matcher matcher) {
        String direction = matcher.group("direction");
        Gadget gadget = Game.getCurrentPlayer().getCurrentEquipment();
        Player currentPlayer = Game.getCurrentPlayer();
        ToolAction toolAction;
        gadget.use(currentPlayer.getX(), currentPlayer.getY(), Game.getMap());
        TileType targetPosition = Game.getCurrentPlayer().getPosition();
        // خطا ها:
        // برای اینکه این بزار مناسب این پوزیشن نیست
        // انرژی کافی ندارد

    }

    private TileType findFinalPosition(String direction) {
        TileType[][] map = Game.getMap();
        Player player = Game.getCurrentPlayer();
        case


    }
    // یا هیچی روی اون موقعیت سازگار نیست یا یکی از این کارهای ابزاره هست
    private ToolAction whichOneIsSuitable() {

    }

    private boolean hasEnoughEnergy(Gadget gadget, ToolAction action) {
        Player currentPlayer = Game.getCurrentPlayer();
        int requiredEnergy = ToolDataManager.getEnergyConsumption(gadget.getClass().getSimpleName()
                .toLowerCase(), gadget.getMaterial());
        return requiredEnergy > currentPlayer.getEnergy();

    }

}
