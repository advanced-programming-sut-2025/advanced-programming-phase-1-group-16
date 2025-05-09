package com.group16.stardewvalley.model.Shops;

import java.util.ArrayList;
import java.util.List;

public abstract class Shop {
    private String shopName;
    private String shopkeeperName;
    private final List<Thing> goods;
    private final int START_TIME;
    private final int END_TIME;

    public Shop(String shopName,
                String shopkeeperName,
                int START_TIME,
                int END_TIME) {
        this.shopName = shopName;
        this.shopkeeperName = shopkeeperName;
        this.goods = new ArrayList<>();
        this.START_TIME = START_TIME;
        this.END_TIME = END_TIME;
    }
}
