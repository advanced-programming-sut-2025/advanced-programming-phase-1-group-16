package com.group16.stardewvalley.model.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.user.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class SaveData {


    private static final String USERS_FILE = "users.json";
    private static final String GAMES_FILE = "games.json";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void saveAllData() {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(App.users, writer);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(GAMES_FILE)) {
            gson.toJson(App.games, writer);
        } catch (IOException e) {
            System.out.println("Error saving games: " + e.getMessage());
        }
    }

    public static void loadAllData() {
        try (FileReader reader = new FileReader(USERS_FILE)) {
            App.users = gson.fromJson(reader, App.users.getClass());
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }

        try (FileReader reader = new FileReader(GAMES_FILE)) {
            App.games = gson.fromJson(reader, App.games.getClass());
        } catch (IOException e) {
            System.out.println("Error loading games: " + e.getMessage());
        }
    }
}
