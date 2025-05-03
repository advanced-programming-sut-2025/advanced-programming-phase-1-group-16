package com.group16.stardewvalley.view.menu;

import com.group16.stardewvalley.controller.map.MapController;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.command.GameMenuCommands;
import com.group16.stardewvalley.model.user.Player;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements MenuInterface {
    private final GameMenuController controller = new GameMenuController();
    private final MapController mapController = new MapController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;

        if((matcher = GameMenuCommands.NewGame.getMatcher(input)) != null){
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



        } else {
            System.out.println("no matching game found");
        }
            //load game
            //exit
    }
}
