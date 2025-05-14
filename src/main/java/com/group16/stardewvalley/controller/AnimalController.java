package com.group16.stardewvalley.controller;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.user.Player;

import static com.group16.stardewvalley.model.map.Pos.isNear;

public class AnimalController {

    public Result pet(String name) {
        Game game = App.getActiveGame();
        Player player = game.getCurrentPlayer();

        for (Animal animal: game.getGameAnimals()){
            if(animal.getName().equals(name)){ //that animal exists

                if(isNear(player.getPosition(), animal.getAnimalPos())){    //check if player is in the 8 neighboring tiles of animal

                    animal.increaseFriendship(15);
                    return new Result(true, "done with pet");

                }
                return new Result(false, "go near that animal");
            }
        }
        return new Result(false, "no animal with that name");


    }


    public Result cheatSetAnimalFriendship(String name, int amount) {
        Animal animal = animalFromName(name);
        if(animal != null){
            animal.increaseFriendship(amount);
            return new Result(true, name + " friendship cheated to " + amount);
        }
        return new Result(false, "no animal with that name");
    }

    private Animal animalFromName(String name){
        Game game = App.getActiveGame();

        for(Animal animal : game.getGameAnimals()){
            if(animal.getName().equals(name)){
                return animal;
            }
        }
        return null;
    }


    public Result showAnimalInfo() {
        Game game = App.getActiveGame();

        StringBuilder sb = new StringBuilder();

        for (Animal animal : game.getGameAnimals()) {
            if (animal.getOwner().equals(game.getCurrentPlayer())) {
                sb.append("animal type: ").append(animal.getAnimalType().getName());
                sb.append("\nname: ").append(animal.getName());
                sb.append("\nFriendship score: ").append(animal.getFriendship());
                sb.append("\nPet: ").append(animal.getIsPet() ? "yes" : "no");
                sb.append("\nFed: ").append(animal.getIsFeed() ? "yes" : "no").append("\n");
            }
        }

        return new Result(true, sb.toString());
    }

    public Result ShepherdAnimals(String name, int x, int y) {
        Animal animal = animalFromName(name);
        if(animal == null){
            return new Result(false, "no animal with that name");
        }



    }

    public Result feedHay(String name) {

    }
}
