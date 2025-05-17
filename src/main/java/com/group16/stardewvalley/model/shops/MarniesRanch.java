package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.animal.AnimalType;
import com.group16.stardewvalley.model.items.Hay;
import com.group16.stardewvalley.model.tools.MilkPail;
import com.group16.stardewvalley.model.tools.Shear;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.animal.AnimalType;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.items.Stone;
import com.group16.stardewvalley.model.map.PlaceType;
import com.group16.stardewvalley.model.user.Player;

import java.awt.geom.RectangularShape;
import java.util.Map;
import java.util.Objects;

import static com.group16.stardewvalley.model.shops.MarniesRanchAnimals.sellAnimalFromName;
import static com.group16.stardewvalley.model.animal.AnimalType.animalTypeFromName;

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

    public Result resetDailySold() {
        animals.setDailySold(0);
        return new Result(true, "daily sold set to zero");
    }

    public Result buyAnimal(String animal, String name) {
        Game game = App.getActiveGame();

        if(animalTypeFromName(animal) == null){
            return new Result(false, "no animal with that name");
        }

        //make new animal
        Animal newAnimal = new Animal( sellAnimalFromName(animal), animalTypeFromName(animal), name, game.getCurrentPlayer()    );


        for (Building building : game.getBuildings()){
            for(BuildingType requiredBuilding : newAnimal.getFromShopType().getBuildingRequired()){
                if(building.getBuildingType().equals(requiredBuilding)){ //check if a suitable building for that animal exist.

                    if(building.getCapacity() < requiredBuilding.getAnimalLimit()){ //چک کن قفس جا داره یا نه
                        //decrease money
                        game.getCurrentPlayer().decreaseCoin(newAnimal.getAnimalType().getPrice());
                        //add animal to game animal
                        game.getGameAnimals().add(newAnimal);
                        //add to list of this building
                        building.addAnimal(newAnimal);
                        //increase building capacity
                        building.increaseCapacity();
                        //set animal position as building start position
                        newAnimal.setAnimalPos(building.getStartPosition());

                    }

                }
            }
        }
        return new Result(false, " no suitable building for animal found");

    }


}
}
