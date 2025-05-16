package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.animal.AnimalType;
import com.group16.stardewvalley.model.items.Hay;
import com.group16.stardewvalley.model.tools.MilkPail;
import com.group16.stardewvalley.model.tools.Shear;

public class MarniesRanch extends Shop{
    private static MarniesRanch instance;

    public MarniesRanch() {
        super("Marnie's Ranch", "Marnie", 9, 16);
        initializeItems();
    }

    public static MarniesRanch getInstance() {
        if (instance == null) {
            instance = new MarniesRanch();
        }
        return instance;
    }

    public void initializeItems() {
        addItem(new Hay("Hay"), Integer.MAX_VALUE);
        addItem(new MilkPail("Milk pail"), 1);
        addItem(new Shear("Shear"), Integer.MAX_VALUE);
        addItem(new Animal(AnimalType.HEN, "Hen", null, BuildingType.Coop), 2);
        addItem(new Animal(AnimalType.COW, "Cow", null, BuildingType.Barn), 2);
        addItem(new Animal(AnimalType.GOAT, "Goat", null, BuildingType.Big_Barn), 2);
        addItem(new Animal(AnimalType.DUCK, "Duck", null, BuildingType.Big_Coop), 2);
        addItem(new Animal(AnimalType.SHEEP, "Sheep", null, BuildingType.Deluxe_Barn), 2);
        addItem(new Animal(AnimalType.RABBIT, "Rabbit", null, BuildingType.Deluxe_Coop), 2);
        addItem(new Animal(AnimalType.DINOSAUR, "Dinasaur", null, BuildingType.Big_Coop), 2);
        addItem(new Animal(AnimalType.PIG, "Pig", null, BuildingType.Deluxe_Barn), 2);
    }


}
