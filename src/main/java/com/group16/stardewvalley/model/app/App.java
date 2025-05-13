package com.group16.stardewvalley.model.app;

import com.group16.stardewvalley.model.menu.Menu;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.model.menu.Menu;

import java.util.ArrayList;

public class App {
    private static User loggedInUser;
    public static ArrayList<Game> games = new ArrayList<>();
    private static Game activeGame = null;

    public static ArrayList<User> users = new ArrayList<User>();
    private static Menu currentMenu = Menu.LoginMenu;



    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static ArrayList<Game> getGroups() {
        return games;
    }

    public static void setGroups(ArrayList<Game> games) {
        App.games = games;
    }

    public static void addUser(User user) {
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        App.users = users;
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

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static Game getActiveGame() {
        return activeGame;
    }

    public static void setActiveGame(Game activeGame) {
        App.activeGame = activeGame;
    }

}
