package com.group16.stardewvalley.view.menu;


import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.menu.GameMenuCommands;
import com.group16.stardewvalley.model.menu.LoginMenuCommands;
import com.group16.stardewvalley.model.menu.ProfileMenuCommands;
import com.group16.stardewvalley.model.user.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements GameMenuInterface {
    private final GameMenuController controller = new GameMenuController();
    private final MapController mapController = new MapController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        Matcher matcher = null;

        //new game
        if((matcher = GameMenuCommands.NewGame.getMatcher(input)) != null){ //after new game, player must choose farm and cant do anything else
            Result result = controller.newGame(matcher.group("usernames"));
            System.out.println(result);

            if(result.isSuccessful()){
                Matcher matcher2 = null;
                for (Player player : App.getActiveGame().getPlayers()) {
                    while (player.getFarm() == null) {
                        String input2 = scanner.nextLine();
                        if ((matcher2 = GameMenuCommands.ChooseMap.getMatcher(input2)) != null) {
                            System.out.println(controller.chooseFarm(player, matcher2.group("mapNumber")));
                        }
                        else System.out.println("now you must choose a map");
                    }
                }
                mapController.createMap();
            }

        } else if( (matcher = GameMenuCommands.LoadGame.getMatcher(input)) != null){
            System.out.println(controller.loadGame());
            //load game

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

        }else if(( matcher = ProfileMenuCommands.ExitMenu.getMatcher(input)) != null ) {
            //back to main menu
            System.out.println(controller.exitMenu());
        }else if((matcher = LoginMenuCommands.ShowCurrentMenu.getMatcher(input)) != null ) {
            System.out.println(controller.showCurrentMenu());

        }else if ((matcher = GameMenuCommands.Walk.getMatcher(input)) != null){
        Result result = mapController.askWalking(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
        System.out.println(result);
        if (result.isSuccessful()){
            String answer = scanner.nextLine();
            if (answer.equals("yes")) {
                System.out.println(mapController.walk(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            }
        }
    } else if ((matcher = GameMenuCommands.PrintMap.getMatcher(input)) != null){
            System.out.println(mapController.printMap(Integer.parseInt(matcher.group("x")),
                    Integer.parseInt(matcher.group("y")), Integer.parseInt(matcher.group("size"))));

    } else if ((matcher = GameMenuCommands.HelpReadingMap.getMatcher(input)) != null){
        System.out.println(mapController.helpReadingMap());
    }

        else{
            System.out.println("invalid command!");

        }
    }
}
