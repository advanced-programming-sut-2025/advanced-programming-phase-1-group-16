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

import static com.group16.stardewvalley.model.animal.AnimalType.animalTypeFromName;

public class MarniesRanch extends Shop {
    public MarniesRanch() {
        super("Marnie's Ranch", "Marnie", 9, 16);
    }

    private MarniesRanchAnimals animals;

    public Result buyAnimal(String animal, String name) {
        Player player = App.getActiveGame().getCurrentPlayer();
        Game game = App.getActiveGame();
        Map<Item, Integer> items  = player.getInventory().getItems();

        //make new animal
        Animal newAnimal = new Animal(animalTypeFromName(animal), name);


        for (Building building : game.getBuildings()){
            if(building.getBuildingType().equals(newAnimal.getAnimalType().))
        }

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
