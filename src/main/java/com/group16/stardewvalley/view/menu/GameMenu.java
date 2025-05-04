package com.group16.stardewvalley.view.menu;


import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.command.GameMenuCommands;
import com.group16.stardewvalley.model.command.LoginMenuCommands;
import com.group16.stardewvalley.model.command.Menu;
import com.group16.stardewvalley.model.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements GameMenuInterface {
    private final GameMenuController controller = new GameMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        Matcher matcher = null;
        if((matcher = GameMenuCommands.NewGame.getMatcher(input)) != null){
            System.out.println(controller.newGame(matcher.group("password")));
            //new game
        }else if ((matcher = GameMenuCommands.ChooseMap.getMatcher(input)) != null){
            System.out.println(controller.chooseFarm(App.getLoggedInUser().getUsername() ,matcher.group("map_number")));

            if(controller.chooseFarm(App.getLoggedInUser().getUsername(), matcher.group("map_number")).isSuccessful()){
                //players can  choose their farm turn based.
                for (int i = 0; i < 3; i++) {
                    System.out.println("player " + (i + 2) + " please choose your farm.");
                    String input2 = scanner.nextLine();
                    Matcher matcher2 = GameMenuCommands.ChooseMap.getMatcher(input2);
                    if (matcher2 != null) {
                        System.out.println(controller.chooseFarm(App.getActiveGame().getPlayers().get(i+1).getUsername(), matcher2.group("map_number")));
                    }
                }

            }
            //choose map
        } else if( (matcher = GameMenuCommands.LoadGame.getMatcher(input)) != null){
            System.out.println(controller.loadGame());
            //load game

        }else if((matcher = GameMenuCommands.Exit.getMatcher(input)) != null){
            System.out.println(controller.exit());

        }else if( (matcher = GameMenuCommands.ForceTerminateVote.getMatcher(input)) != null){
            Map<User, Boolean> votes = new HashMap<User, Boolean>();
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

        }else{
            System.out.println("invalid command!");

        }
    }
}
