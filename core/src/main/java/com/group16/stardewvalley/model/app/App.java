package com.group16.stardewvalley.model.app;

import com.group16.stardewvalley.model.menu.Menu;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.model.menu.Menu;

import java.util.ArrayList;

public class App {
    private static User loggedInUser;
    private static Player currentPlayer;
    private static Game activeGame = null;
    private static Menu currentMenu = Menu.LoginMenu;

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        App.currentPlayer = currentPlayer;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void addUser(User user) {
    }

    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }

    public static Game getActiveGame() {
        return activeGame;
    }

    public static void setActiveGame(Game activeGame) {
        App.activeGame = activeGame;
    }

    public static void logout(){
        loggedInUser = null;
        currentPlayer = null;
    }
}
