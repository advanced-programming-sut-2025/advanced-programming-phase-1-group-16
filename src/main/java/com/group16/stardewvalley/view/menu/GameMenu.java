package com.group16.stardewvalley.view.menu;

import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.GameState;
import com.group16.stardewvalley.model.command.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements MenuInterface {
    private final GameMenuController controller = new GameMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;
        if (App.getActiveGame() == null || App.getActiveGame().getGameState() == GameState.WAITING_FOR_NEW_GAME) {
            if((matcher = GameMenuCommands.NewGame.getMatcher(input)) != null){
                System.out.println(controller.newGame(matcher.group("usernames")));
                //new game
            } else {
                System.out.println("no matching game found");
            }
        }
        if (App.getActiveGame() != null && App.getActiveGame().getGameState() == GameState.WAITING_FOR_MAP_SELECTION) {
            if ((matcher = GameMenuCommands.ChooseMap.getMatcher(input)) != null){
                System.out.println(controller.chooseMap(App.getLoggedInUser().getUsername() ,matcher.group("map_number")));
            }
            else {
                System.out.println("now you must choose a map");
            }
        }
            //load game
            //exit
        else{
            System.out.println("invalid command!");

        }
    }
}
