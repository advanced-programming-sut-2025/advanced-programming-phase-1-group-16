package com.group16.stardewvalley.controller.Shops;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.Shops.Blacksmith;
import com.group16.stardewvalley.model.Shops.UpgradeType;
import com.group16.stardewvalley.model.Tools.Gadget;
import com.group16.stardewvalley.model.Tools.ToolDataManager;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.user.Player;

import java.util.regex.Matcher;

public class BlacksmithController {
    Blacksmith blacksmith = Blacksmith.getInstance();
    private final Game game;

    public BlacksmithController(Game game) {
        this.game = game;
    }

    private final TimeDate timeDate = TimeDate.getInstance(game);

    public Result handleCommand(String command, Matcher matcher) {
        // در اینجا باید اگر در این مغازه نبود ارور بدیم
        if (game.getCurrentPlayer().getPosition()) {

        }

        switch (command) {
            case "upgrade":
                return upgradeTool(matcher);
            case "buy":
                return buyOre(matcher);
            default:
                return new Result(false, "Invalid command");
        }
    }

    private Result upgradeTool(Matcher matcher) {
        String toolName = matcher.group("toolName");
        Player currentPlayer = game.getCurrentPlayer();
        // خطای مراجعخ در زمان نامناسب
        if (!isShopTime()) {
            return new Result(false, "Sorry! we're closed! Shop hours: 9 AM to 4 PM");
        }

        // خطای نداشتن این ابزار
        if (currentPlayer.getInventory().findToolByName(toolName) == null) {
            return new Result(false, "You want me to upgrade... " +
                    "what exactly? You don't even have this tool!");
        }

        Gadget currentTool = currentPlayer.getInventory().findToolByName(toolName);
        String currentMaterial = currentTool.getMaterial();

        // خطای اینکه این ابزار ارتفا پذیر نیست
        if (currentMaterial == null) {
            return new Result(false, "This tool cannot be upgraded");
        }

        // خطای اینکه ابزار تا بالاترین مرحله ارتقا یافته
        String nextMaterial = getNextMaterial(currentMaterial);
        if (nextMaterial == null) {
            return new Result(false, "Your " + toolName + " is already at the highest upgrade level!");
        }


        int upgradeCost = ToolDataManager.getUpgradeCost(toolName, currentMaterial, nextMaterial);
        // خطای پول کافی نداشتن
        if (currentPlayer.getCoin() < upgradeCost) {
            return new Result(false, "Heh. Looks like your wallet’s " +
                    "as rusty as this tool. Bring more gold next time!");
        }

        UpgradeType upgradeType;
        try {
            upgradeType = UpgradeType.valueOf(nextMaterial.toUpperCase() + "_TOOL");
        } catch (IllegalArgumentException e) {
            return new Result(false, "Invalid upgrade type for " + toolName);
        }

        // خطای اینکه امروز یکبار انجام شده
      if (!blacksmith.cabUpgradeToday(upgradeType)) {
          return new Result(false, "My anvil needs a break! " +
                  "One upgrade a day keeps the warranty valid ^ ^");
      }
    }

    private Result buyOre(Matcher matcher) {

    }

    private boolean isShopTime() {
        if (timeDate.getHour() <= 9 || timeDate.getHour() >= 16) {
            return false;
        }
        return true;
    }

    private String getNextMaterial(String currentMaterial) {
        if (currentMaterial.equalsIgnoreCase("base")) {
            return "Copper";
        }

        if (currentMaterial.equalsIgnoreCase("Copper")) {
            return "Iron";
        }

        if (currentMaterial.equalsIgnoreCase("Iron")) {
            return "Gold";
        }

        if (currentMaterial.equalsIgnoreCase("Gold")) {
            return "Iridium";
        }

        if (currentMaterial.equalsIgnoreCase("Iridium")) {
            return null;
        }
    }
}
