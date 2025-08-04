package com.group16.stardewvalley;

import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.user.User;

import java.util.ArrayList;

public class App {
    public static ArrayList<Game> games = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();

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

}
