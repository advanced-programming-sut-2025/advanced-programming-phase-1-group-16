package com.group16.stardewvalley.model.menu;



import com.group16.stardewvalley.view.menu.GameMenuInterface;
import com.group16.stardewvalley.view.menu.*;

import java.util.Scanner;

public enum Menu {
    LoginMenu(new LoginMenu(), "Login Menu"),
    ProfileMenu(new ProfileMenu(), "Profile Menu"),
    GameMenu(new GameMenu(), "Game Menu"),
    MainMenu(new MainMenu(), "Main Menu"),
    HomeMenu(new HomeMenu(), "Home Menu"),
    ExitMenu(new ExitMenu(), "Exit Menu"),;

    private final GameMenuInterface menu;
    private final String name;

    Menu(GameMenuInterface menu, String name) {
        this.menu = menu;
        this.name = name;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }

    public GameMenuInterface getMenu() {
        return menu;
    }

    public String getName() {
        return name;
    }
}