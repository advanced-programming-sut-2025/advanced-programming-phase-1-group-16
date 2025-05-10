package com.group16.stardewvalley.model.Seeds;

import com.group16.stardewvalley.model.time.Season;

public enum SeedType {
    Parsnip_Seeds("Pasrsnip Seeds", )


    private final String seedName;
    private final Season season;
    private final int price;
    private final int dailyLimit;

    public SeedType(String seedName,Season season, int price, int dailyLimit) {
        this.seedName = seedName;
        this.season = season;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }
}
