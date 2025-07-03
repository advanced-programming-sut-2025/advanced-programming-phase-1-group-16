package com.group16.stardewvalley.model.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.model.app.App;

import java.io.File;
import java.util.ArrayList;

public class UserSaveManager {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String FILE_PATH = "users.json";

    public static void saveUsers() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), App.getUsers());
            System.out.println("Users saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadUsers() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                ArrayList<User> users = mapper.readValue(file,
                        mapper.getTypeFactory().constructCollectionType(ArrayList.class, User.class));
                App.setUsers(users);
                System.out.println("Users loaded successfully.");
            } else {
                System.out.println("No saved users found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isUsernameTaken(String username) {
        return App.getUsers().stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    public static void addUserAndSave(User user) {
        App.getUsers().add(user);
        saveUsers();  // reuse save logic
    }

}

