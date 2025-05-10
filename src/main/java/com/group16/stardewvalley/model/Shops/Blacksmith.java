package com.group16.stardewvalley.model.Shops;

import com.group16.stardewvalley.model.Items.Ore;
import com.group16.stardewvalley.model.Items.OreType;
import com.group16.stardewvalley.model.Shops.UpgradeType;

import java.util.EnumMap;

public class Blacksmith extends Shop{
    private final EnumMap<UpgradeType, Integer> upgrades = new EnumMap<>(UpgradeType.class);

    public Blacksmith() {
        super("Blacksmith", "Clint", 9, 16);
        initializeUpgrades();
        initializeItems();
    }

    private void initializeUpgrades() {
        for (UpgradeType type : UpgradeType.values()) {
            upgrades.put(type, 0); // مقدار اولیه
        }
    }

    public boolean applyUpgrade(UpgradeType type) {
        int current = upgrades.get(type);
        if (current < type.getMaxUpgrades()) {
            upgrades.put(type, current + 1);
            return true;
        }
        return false;
    }

    public void resetDailyUpgrades() {
        upgrades.replaceAll((k, v) -> 0); // ریست روزانه
    }


    public void initializeItems() {
        addItem(new Ore("Coal", OreType.COAL), OreType.COAL.getDailyLimit());
        addItem(new Ore("Copper Ore", OreType.COPPER_ORE), OreType.COPPER_ORE.getDailyLimit());
        addItem(new Ore("Iron Ore", OreType.IRON_ORE), OreType.IRON_ORE.getDailyLimit());
        addItem(new Ore("Gold Ore", OreType.GOLD_ORE), OreType.GOLD_ORE.getDailyLimit());
    }

}