package com.group16.stardewvalley.model.app;


import java.util.Scanner;
import com.group16.stardewvalley.view.menu.*;


public enum Menu {
    LoginMenu(new LoginMenu(), "Login MenuInterface"),
    ProfileMenu(new ProfileMenu(), "Profile MenuInterface"),
    GameMenu(new GameMenu(), "App MenuInterface"),
    MainMenu(new MainMenu(), "Main MenuInterface"),
    ExitMenu(new ExitMenu(), "Exit MenuInterface"),;

    private final MenuInterface menu;
    private final String name;

    Menu(MenuInterface menu, String name) {
        this.menu = menu;
        this.name = name;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }

    public Menu getMenu() {
        return menu;
    }

    public String getName() {
        return name;
    }
}
