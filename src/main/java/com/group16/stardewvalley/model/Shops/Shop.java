package com.group16.stardewvalley.model.Shops;

import com.group16.stardewvalley.model.Things.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Shop {
    private String shopName;
    private String shopkeeperName;
    private final Map<Item, Integer> dailyLimit = new HashMap<>();
    private final Map<Item, Integer> soldToday = new HashMap<>();
    private final int START_TIME;
    private final int END_TIME;

    public Shop(String shopName,
                String shopkeeperName,
                int START_TIME,
                int END_TIME) {
        this.shopName = shopName;
        this.shopkeeperName = shopkeeperName;
        this.START_TIME = START_TIME;
        this.END_TIME = END_TIME;
    }

    public String getShopName() {
        return shopName;
    }

    public boolean canSell(Item item) {
        if (!dailyLimit.containsKey(item)) {
            return false;
        }

        int limit = dailyLimit.get(item);
        int sold = soldToday.get(item);

        return sold < limit;
    }

    public Set<Item> getAvailableItems() {
        return dailyLimit.keySet();
    }

//    public void sellItem(Item item) {
//        // ایا باید به موجودی فروشگاه ها اضافه شود یا اصلا لازم نداریم چنین چیزی
//        if (canSell(item)) {
//            soldToday.get(item)--;
//        }
//    }

}
