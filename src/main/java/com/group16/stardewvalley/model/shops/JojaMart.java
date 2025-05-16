package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.agriculture.Seeds;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.time.Season;
import com.group16.stardewvalley.model.agriculture.*;
import java.util.Set;
import java.util.HashSet;

public class JojaMart extends Shop {
    private static JojaMart instance;

    private JojaMart() {
        super("JojaMart", "Morris", 9, 23);
        initializeItems();
    }

    public static JojaMart getInstance() {
        if (instance == null) {
            instance = new JojaMart();
        }
        return instance;
    }

    public void initializeItems() {
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

    @Override
    public Set<Item> getAvailableItems() {
        Set<Item> availableItems = new HashSet<>();
        Season currentSeason = App.getActiveGame().getTimeDate().getSeason();

        // فقط دانه‌های فصلی + آیتم‌های دائمی
        dailyLimit.keySet().stream()
                .filter(item -> !(item instanceof Seed) ||
                        ((Seed)item).isAvailableInSeason(currentSeason))
                .forEach(availableItems::add);

        return availableItems;
    }
}