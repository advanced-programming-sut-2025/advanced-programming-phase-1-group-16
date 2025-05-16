package com.group16.stardewvalley.view;


import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.menu.Menu;

import java.util.Scanner;

public class AppView {


    public void run(){
        Scanner scanner = new Scanner(System.in);
        String input = "";
        do {
            App.getCurrentMenu().checkCommand(scanner);
            /*
            if(App.getActiveGame() != null){
                App.getActiveGame().nextTurn();
            }

             */
        } while (App.getCurrentMenu() != Menu.ExitMenu);
    }

}
