package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.items.Stone;
import com.group16.stardewvalley.model.items.Wood;

public class CarpentersShop extends Shop{
    private static CarpentersShop instance;
    public static CarpentersShop getInstance() {
        if (instance == null) {
            return instance;
        }
        return null;
    }
    public CarpentersShop() {
        super("Carpenter's Shop", "Robin", 9, 8);
        initializeItems();
    }

    public void initializeItems() {
        addItem(new Wood("Wood"), Integer.MAX_VALUE);
        addItem(new Stone("Stone"), Integer.MAX_VALUE);
        addItem(new Building("Barn"), 1);
        addItem(new Building("Big Barn"), 1);
        addItem(new Building("Deluxe Barn"), 1);
        addItem(new Building("Coop"), 1);
        addItem(new Building("Big Coop"), 1);
        addItem(new Building("Deluxe Coop"), 1);
        addItem(new Building("Well"), 1);
        addItem(new Building("Shipping Bin"), 1);

    }
}
