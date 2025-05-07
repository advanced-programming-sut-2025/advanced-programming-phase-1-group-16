package com.group16.stardewvalley.controller.menu;


import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.menu.Menu;
import com.group16.stardewvalley.model.user.Result;

public class MainMenuController {


    public Result changeMenu(String menuName){
        return switch (menuName) {
            case "Profile Menu" -> {
                App.setCurrentMenu(Menu.ProfileMenu);
                yield new Result(true, "you are in the profile menu!");
            }
            case "Game Menu" -> {
                App.setCurrentMenu(Menu.GameMenu);
                yield new Result(true, "you are in the game menu!");
            }
            case "Avatar Menu" -> {
                //TODO
                yield new Result(true, "you are in the avatar menu!");
            }
            default -> new Result(false, "wrong menu name!");
        };
    }

    public Result logout(){
        App.setLoggedInUser(null);
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "you are in login menu now!");
    }
}
