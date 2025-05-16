package com.group16.stardewvalley.model.shops;

public class FishShop extends Shop{

    private static FishShop instance;

    public FishShop() {
        super("Fish Shop", "Willy", 9, 17);
        initializeItems();
    }

    public static FishShop getInstance() {
        if (instance == null) {
            instance = new FishShop();
        }
        return instance;
    }

    public void initializeItems() {

    }
}
