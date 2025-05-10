package com.group16.stardewvalley.model.Shops;

import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.Items.Stone;
import com.group16.stardewvalley.model.Items.Wood;

public class CarpentersShop extends Shop{
    public CarpentersShop() {
        super("Carpenter's Shop", "Robin", 9, 8);
    }

    public void initializeItems() {
        addItem(new Wood("Wood"), Integer.MAX_VALUE);
        addItem(new Stone("Stone"), Integer.MAX_VALUE);
    }

    private int barn;
    private int bigBarn;
    private int deluxeBarn;
    private int coop;
    private int
}
