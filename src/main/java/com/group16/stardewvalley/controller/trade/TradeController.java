package com.group16.stardewvalley.controller.trade;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.user.Player;

import java.util.regex.Matcher;

public class TradeController {
    private final Game game;
    public TradeController() {
        this.game = App.getActiveGame();
    }

    public Result startTrade() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Player player : game.getPlayers()) {
            sb.append(i);
            sb.append("-");
            sb.append(player.getName());
            sb.append("\n");
        }

        return new Result(true, sb.toString());
    }

    public Result trade(Matcher matcher) {
        Player currentPlayer = game.getCurrentPlayer();
        String username = matcher.group("username");
        String type = matcher.group("type");
        String itemName = matcher.group("item");
        String amountStr = matcher.group("amount");
        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            return new Result(false, "amount is invalid");
        }

        // بازیکن مورد نظر وجود نداشت
        if (game.getPlayerByUsername(username) == null) {
            return new Result(false, "")
        }

        // اصلا این کالا را نداشته باشد
        if (currentPlayer.getInventory().getItemByName(itemName) == null) {
            return new Result(false, "")
        }
        Item targetItem = currentPlayer.getInventory().getItemByName(itemName);

        // تعداد کافی از این کالا نداشته باشد
        if ()

        if (type.equalsIgnoreCase("offer")) {
            String priceStr = matcher.group("price");
            int price;
            String targetItemName = matcher.group("targetItemName");
            String targetAmountStr = matcher.group("targetAmount");
            int targetAmount;
            try {
                price = Integer.parseInt(priceStr);
                targetAmount = Integer.parseInt(priceStr);
            } catch (NumberFormatException e) {
                return new Result(false, "price or targetAmount is invalid ");
            }
        } else {

        }
    }


}
