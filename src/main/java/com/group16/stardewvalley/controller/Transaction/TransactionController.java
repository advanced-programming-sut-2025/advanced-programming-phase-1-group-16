package com.group16.stardewvalley.controller.Transaction;

import com.group16.stardewvalley.model.Shops.Shop;
import com.group16.stardewvalley.model.Items.Item;
import com.group16.stardewvalley.model.app.*;
import com.group16.stardewvalley.model.user.*;
import com.group16.stardewvalley.model.Result;

import java.util.List;
import java.util.regex.*;

public class TransactionController {
    private final Game game;

    public TransactionController(Game game) {
        this.game = game;
    }

    public Result showAllProducts() {
        // در اینجا مهم نیست که محصولی موجود باشد یا نباشد
        Player currentPlayer = game.getCurrentPlayer();
        Shop shop = currentPlayer.whereAmI();

        if (shop == null) {
            return new Result(false, "You are not in any shop (~_^)")
        }

        List<Item> goods = shop.getGoods();
        StringBuilder result = getAllGoodsInfo(goods);
        return new Result(true, result.toString());
    }

    public StringBuilder getAllGoodsInfo(List<Item> goods) {
        StringBuilder sb = new StringBuilder();
        for (Item item : goods) {
            sb.append(item.getName())
                    .append(" - ")
                    .append(item.getPrice())
                    .append("g\n");
        }
        return sb;
    }

    public Result showAvailableProducts() {
// فقط محصولات موجود
        // نمیدونم محصولات چطور ناموجودند تعداد باید داشته باشند؟
    }

    public Result purchase(Matcher matcher) {
        matcher.group("");
        String productName = matcher.group("productName");
        String countStr = matcher.group("count");
    }

}
