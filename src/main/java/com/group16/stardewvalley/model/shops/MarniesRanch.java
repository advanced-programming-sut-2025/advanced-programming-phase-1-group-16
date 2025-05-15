package com.group16.stardewvalley.model.Shops;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.animal.AnimalType;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.items.Stone;
import com.group16.stardewvalley.model.user.Player;

import java.awt.geom.RectangularShape;
import java.util.Map;
import java.util.Objects;

import static com.group16.stardewvalley.model.Shops.MarniesRanchAnimals.sellAnimalFromName;
import static com.group16.stardewvalley.model.animal.AnimalType.animalTypeFromName;

public class MarniesRanch extends Shop {
    public MarniesRanch() {
        super("Marnie's Ranch", "Marnie", 9, 16);
    }

    private MarniesRanchAnimals animals;

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



    public Result showAllProducts() {
        StringBuilder output = new StringBuilder();
        for (MarniesRanchAnimals value : MarniesRanchAnimals.values()) {
            output.append(value.toString()).append(" ").append(value.getPrice()).append(" g\n");
        }
        return new Result(true,  output.toString());
    }

    public Result showAvailableProducts() {
        StringBuilder output = new StringBuilder();
        for (MarniesRanchAnimals value : MarniesRanchAnimals.values()) {
            if (!Objects.equals(value.getDailyLimit(), value.getDailySold())) {
                output.append(value.toString()).append(" ").append(value.getPrice()).append(" g\n");
            }
        }
        return new Result(true, output.toString());
    }


//    public static model.structure.stores.MarnieShopAnimal getFromName(String name){
//        for (model.structure.stores.MarnieShopAnimal value : model.structure.stores.MarnieShopAnimal.values()) {
//            if (value.name.equalsIgnoreCase(name)){
//                return value;
//            }
//        }
//        return null;
//    }

    public Result resetDailySold() {
        animals.setDailySold(0);
        return new Result(true, "daily sold set to zero");
    }
}
