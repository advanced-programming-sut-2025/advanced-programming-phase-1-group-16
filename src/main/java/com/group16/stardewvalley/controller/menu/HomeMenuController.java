package com.group16.stardewvalley.controller.menu;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.menu.Menu;

public class HomeMenuController {


    public Result exitMenu(){
        App.setCurrentMenu(Menu.GameMenu);
        return new Result(true, "you are in the game menu!");
    }
}
