package com.group16.stardewvalley.controller.shops;

import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.shops.Shop;
import com.group16.stardewvalley.model.shops.UpgradeType;
import com.group16.stardewvalley.model.tools.Gadget;
import com.group16.stardewvalley.model.tools.ToolDataManager;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.map.Location;

import java.util.Set;
import java.util.regex.Matcher;

public class ShopController {
    private final Game game;

    public ShopController() {
        this.game = App.getActiveGame();
    }

    private Player currentPlayer = App.getActiveGame().getCurrentPlayer();


    public Result handleCommand(String command, Matcher matcher) {
        String[] parts = command.split(" ");
        Location l = game.getCurrentPlayer().getLocation().getLocation();
        // در اینجا باید اگر در این مغازه نبود ارور بدیم
        if (!(l == Location.Blacksmith || l == Location.JojaMart ||
              l == Location.CarpentersShop || l == Location.FishShop ||
              l == Location.MarniesRanch || l == Location.TheStardropSaloon ||
              l == Location.PierresGeneralStore  )) {

        }

        switch (parts[0]) {
            case "upgrade":
                return upgradeTool(matcher);
            case  "show":{
                if (parts[2].equalsIgnoreCase("products")) {
                    switch (currentPlayer.getLocation().getLocation()) {
                        case Blacksmith:
                            handleShowProducts(game.getBlacksmith().getAllProducts());
                        case JojaMart:
                            handleShowProducts(game.getJojaMart().getAllProducts());
                        case PierresGeneralStore:
                            handleShowProducts(game.getPierresGeneralStore().getAllProducts());
                        case CarpentersShop:
                            handleShowProducts(game.getCarpentersShop().getAllProducts());
                        case FishShop:
                            handleShowProducts(game.getFishShop().getAllProducts());
                        case MarniesRanch:
                            handleShowProducts(game.getMarniesRanch().getAllProducts());
                        case TheStardropSaloon:
                            handleShowProducts(game.getTheStardropSaloon().getAllProducts());

                        default:
                            return new Result(false, "Hmmmm" +
                                    " I think you'd better get yourself to a store first.");
                    }
                } else if (parts[2].equalsIgnoreCase("available"))  {
                    switch (currentPlayer.getLocation().getLocation()) {
                        case Blacksmith:
                            handleShowProducts(game.getBlacksmith().getAvailableItems());
                        case JojaMart:
                            handleShowProducts(game.getJojaMart().getAvailableItems());
                        case PierresGeneralStore:
                            handleShowProducts(game.getPierresGeneralStore().getAvailableItems());
                        case CarpentersShop:
                            handleShowProducts(game.getCarpentersShop().getAvailableItems());
                        case FishShop:
                            handleShowProducts(game.getFishShop().getAvailableItems());
                        case MarniesRanch:
                            handleShowProducts(game.getMarniesRanch().getAvailableItems());
                        case TheStardropSaloon:
                            handleShowProducts(game.getTheStardropSaloon().getAvailableItems());

                        default:
                            return new Result(false, "Hmmmm" +
                                    " I think you'd better get yourself to a store first.");
                    }
                }


            }
            case "purchase":
                handlePurchase(currentPlayer.getLocation().getLocation(), matcher);
                // کامند مربوط به ارتقای ابزار الات است که فقط باید در یکی اجرا بشود
            case "tools":
                if (currentPlayer.getLocation().getLocation() != Location.Blacksmith) {
                    return new Result(false, "Tool upgrades? You've come to the wrong place " +
                            "The blacksmith's shop is in another part of town.");
                }
                else {
                    upgradeTool(matcher);
                }

            case "sell":
                handleSellProduct(matcher);

            default:
                return new Result(false, "Invalid command");
        }
    }

    public Result handleSellProduct(Matcher matcher) {
        String productName = matcher.group("productName");
        String countStr = matcher.group("count");
        int count = Integer.parseInt(countStr);

        // قابلیت فروش نداشته باشد
        //TODO mirshekar
        return new Result(true, "");
    }


    public Result handleShowProducts(Set<Item> items) {
        if (items == null || items.isEmpty()) {
            return new Result(false, "No products available");
        }

        StringBuilder productsInfo = new StringBuilder("Available Products:\n");

        for (Item item : items) {
            productsInfo.append(String.format(
                    "- %s (Price: %d)%n",
                    item.getName(),
                    item.getPrice()
            ));
        }

        return new Result(true, productsInfo.toString());
    }

    private Result handlePurchase(Location location, Matcher matcher) {
        String productName = matcher.group("productName");
        String countStr = matcher.group("count");
        int count;
        if (countStr == null) {
            count = 1;
        } else {
            count = Integer.parseInt(countStr);
        }
        Item targetItem = null;
        Shop targetShop = currentPlayer.getLocation().getLocation().getShopByLocation();
        targetItem = targetShop.findItemByName(productName);
        // فروشگاه مورد نظر این محصول را نداشته باشد
        if (targetItem == null) {
            return new Result(false, "Sorry, we don't stock that item. " +
                    "Try the specialty shops around town.");
        }

        // فرد موجودی لازم برای خرید ان را نداشته باشد
        if (currentPlayer.getCoin() < targetItem.getPrice()) {
            return new Result(false, "Oops! Too expensive!");
        }

        // فروشگاه برای امروز تعداد کافی برای فروش نداشت
        if (targetShop.getAvailableCountForToday(targetItem) <= count) {
            return new Result(false, "Shop's stock is empty for today! Come back tomorrow.");
        }

        // اینونتوری اش جا نداشته باشد
        if (currentPlayer.getInventory().isFull()) {
            return new Result(false, "Oops! Your backpack is completely full!");
        }
        // با موفقیت خرید کند و به اینونتوری اش اضافه شود
        currentPlayer.getInventory().addItem(targetItem, count);
        currentPlayer.decreaseCoin(targetItem.getPrice());
        targetShop.addBalance(targetItem.getPrice());
        return new Result(true, "Purchase complete! Enjoy your new item");

    }

    public Result upgradeTool(Matcher matcher) {
        String toolName = matcher.group("toolName");
        Player currentPlayer = game.getCurrentPlayer();
        // خطای مراجعخ در زمان نامناسب
        if (!App.getActiveGame().getBlacksmith().isOpen()) {
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
        if (!game.getBlacksmith().cabUpgradeToday(upgradeType)) {
          return new Result(false, "My anvil needs a break! " +
                  "One upgrade a day keeps the warranty valid ^ ^");
        }
        //TODO mirshekar kamel mikone
        return new Result(true, "Upgrade complete!");
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

        return null;
    }
}
