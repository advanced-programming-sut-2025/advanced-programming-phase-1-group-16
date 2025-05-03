package com.group16.stardewvalley.view.menu;


import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.command.GameMenuCommands;

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
            System.out.println(controller.chooseMap(App.getLoggedInUser().getUsername() ,matcher.group("map_number")));
            //choose map
        } else if(){
            //load game
        }else if(){
            //exit
        }else{
            System.out.println("invalid command!");

        }
    }
}
