package com.group16.stardewvalley.model.Shops;

import java.util.EnumMap;

public class JojaMart extends Shop{
    private static JojaMart instance;
    private final EnumMap<SeedType, Integer> springSock = new EnumMap<>(SeedType.class);
    private final EnumMap<SeedType, Integer> summerStock = new EnumMap<>(SeedType.class);
    private final EnumMap<SeedType, Integer> fallStock = new EnumMap<>(SeedType.class);
    private final EnumMap<SeedType, Integer> winterStock = new EnumMap<>(SeedType.class);

    public JojaMart() {
        super("JojaMart", "Morris", 9, 23);
        initializeItems();
    }

    public void initializeItems() {

    }

}
