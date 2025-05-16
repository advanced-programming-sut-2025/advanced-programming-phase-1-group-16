package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.agriculture.Seed;
import com.group16.stardewvalley.model.agriculture.Seeds;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.items.Flower;
import com.group16.stardewvalley.model.items.MarriageRing;
import com.group16.stardewvalley.model.time.Season;

public class PierresGeneralStore extends Shop{
    private static PierresGeneralStore instance;

    public PierresGeneralStore() {
        super("Pierr's General Store", "Pierre", 9, 17);
        initializeItems();
    }

    public static PierresGeneralStore getInstance() {
        if (instance == null) {
            instance = new PierresGeneralStore();
        }
        return instance;
    }

    public void initializeItems() {
        addItem(new Food("Rice"), Integer.MAX_VALUE);
        addItem(new Food("Wheat Flour"), Integer.MAX_VALUE);
        addItem(new Flower("Flower"), 2);
        addItem(new MarriageRing("Marriage Ring"), 2);
        addItem(new Building());

        // اضافه کردن دانه‌های فصلی در زمان اولیه‌سازی
        Season currentSeason = App.getActiveGame().getTimeDate().getSeason();
        for (Seed seed : Seeds.getAllSeeds()) {
            if (seed.isAvailableInSeason(currentSeason)) {
                addItem(seed, seed.getDailyLimit());
            }
        }

        // اضافه کردن سایر آیتم‌های دائمی
        addItem(Seeds.JOJA_COLA, Seeds.JOJA_COLA.getDailyLimit());
        addItem(Seeds.GRASS_STARTER, Seeds.GRASS_STARTER.getDailyLimit());

    }

}
