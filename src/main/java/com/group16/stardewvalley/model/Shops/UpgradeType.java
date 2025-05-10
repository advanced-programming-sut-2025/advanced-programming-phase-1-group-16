package com.group16.stardewvalley.model.Shops;

public enum UpgradeType {
    COPPER_TOOL,
    STEEL_TOOL,
    GOLD_TOOL,
    IRIDIUM_TOOL,
    COPPER_TRASHCAN,
    STEEL_TRASHCAN,
    GOLD_TRASHCAN,
    IRIDIUM_TRASHCAN;

    private final int maxUpgrades; // اگر محدودیت متفاوت باشد

    UpgradeType() {
        this.maxUpgrades = 1; // مقدار پیش‌فرض
    }
}