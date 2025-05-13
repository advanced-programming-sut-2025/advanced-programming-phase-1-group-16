package com.group16.stardewvalley.view.menu;

import com.group16.stardewvalley.controller.menu.HomeMenuController;
import com.group16.stardewvalley.model.menu.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class HomeMenu implements GameMenuInterface{
    private final HomeMenuController controller = new HomeMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;

        if ((matcher = GameMenuCommands.Logout.getMatcher(input)) != null) {


        }else if ((matcher = GameMenuCommands.ChangeMenu.getMatcher(input)) != null) {

        }else if((matcher = GameMenuCommands.ShowCurrentMenu.getMatcher(input)) != null ) {


        }else if((matcher = GameMenuCommands.Exit.getMatcher(input)) != null){
        System.out.println(controller.exitMenu());

        }else{
            System.out.println("invalid command!");
        }
    }
}
