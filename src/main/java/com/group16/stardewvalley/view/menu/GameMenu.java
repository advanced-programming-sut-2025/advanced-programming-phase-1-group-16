package com.group16.stardewvalley.view.menu;


import com.group16.stardewvalley.controller.agriculture.AgricultureController;
import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.controller.menu.HomeMenuController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.menu.GameMenuCommands;
import com.group16.stardewvalley.model.user.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements MenuInterface {
    private final GameMenuController controller = new GameMenuController();
    private final MapController mapController = new MapController();
    private final AgricultureController agricultureController = new AgricultureController();
    private final HomeMenuController homeMenuController = new HomeMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        Matcher matcher = null;

        //new game
        if((matcher = GameMenuCommands.NewGame.getMatcher(input)) != null){ //after new game, player must choose farm and cant do anything else
            System.out.println(controller.newGame(matcher.group("usernames")));
            for (Player player : App.getActiveGame().getPlayers()) {
                while (player.getFarm() != null) {
                    String input2 = scanner.nextLine();
                    if ((matcher = GameMenuCommands.ChooseMap.getMatcher(input2)) != null) {
                        System.out.println(controller.chooseFarm(player, matcher.group("map_number")));
                    }
                    else System.out.println("now you must choose a map");
                }
            }
            mapController.createMap();

        }else if((matcher = GameMenuCommands.Exit.getMatcher(input)) != null){
            System.out.println(controller.exit());

        }else if( (matcher = GameMenuCommands.ForceTerminateVote.getMatcher(input)) != null){
            Map<Player, Boolean> votes = new HashMap<Player, Boolean>();
            votes.put(App.getActiveGame().getCurrentPlayer(), true);
            System.out.println("vote in turn! (true/false)");
            for (int i = 0; i < 3; i++) {
                //TODO next turn
                System.out.println("player " + (i + 2) + " please vote.");
                String input2 = scanner.nextLine();
                if (input2.equals("true")) {
                    votes.put(App.getActiveGame().getCurrentPlayer(), true);
                }else if (input2.equals("false")) {
                    votes.put(App.getActiveGame().getCurrentPlayer(), false);
                }
            }
            System.out.println(controller.forceTerminateGame(votes));

        }else if((matcher = GameMenuCommands.NextTurn.getMatcher(input)) != null){
            App.getActiveGame().nextTurn();

        } else if ((matcher = GameMenuCommands.Walk.getMatcher(input)) != null){
            Result result = mapController.askWalking(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
            System.out.println(result);
            if (result.isSuccessful()){
                String answer = scanner.nextLine();
                if (answer.equals("yes")) {
                    System.out.println(mapController.walk(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
                }
            }
        } else if ((matcher = GameMenuCommands.PrintMap.getMatcher(input)) != null){
            System.out.println(mapController.printMap(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")), Integer.parseInt(matcher.group("size"))));
        } else if ((matcher = GameMenuCommands.HelpReadingMap.getMatcher(input)) != null){
            System.out.println(mapController.helpReadingMap());
        } else if ((matcher = GameMenuCommands.CraftInfo.getMatcher(input)) != null){
            System.out.println(agricultureController.craftInfo(matcher.group("name")));
        } else if ((matcher = GameMenuCommands.PlantSeed.getMatcher(input)) != null){
            System.out.println(agricultureController.planting(matcher.group("seed"), matcher.group("dir")));
        } else if ((matcher = GameMenuCommands.ShowPlant.getMatcher(input)) != null){
            System.out.println(agricultureController.showPlant(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        } else if ((matcher = GameMenuCommands.Fertilize.getMatcher(input)) != null){
            System.out.println(agricultureController.fertilizePlant(matcher.group("fertilizer"), matcher.group("dir")));
        } else if ((matcher = GameMenuCommands.HowMuchWater.getMatcher(input)) != null){
            System.out.println(agricultureController.howMuchWater());
        }
        //TODO shayad be khata monjar she
        else if (App.getActiveGame() != null && mapController.isPlayerInCottage(App.getActiveGame().getCurrentPlayer())) {
            if ((matcher = GameMenuCommands.PutFood.getMatcher(input)) != null){
                System.out.println(homeMenuController.putItemInRefrigerator(matcher.group("food")));
            } else if ((matcher = GameMenuCommands.PickFood.getMatcher(input)) != null){
                System.out.println(homeMenuController.pickItemInRefrigerator(matcher.group("food")));
            }
        } else{
            System.out.println("invalid command!");

        }
    }
}