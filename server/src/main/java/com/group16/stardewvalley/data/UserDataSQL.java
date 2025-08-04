package com.group16.stardewvalley.data;


import com.group16.stardewvalley.model.user.SecurityQuestions;
import com.group16.stardewvalley.model.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDataSQL {
    private static UserDataSQL instance;
    private final static String DB_URL = "jdbc:sqlite:data/users.db";

    private UserDataSQL() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT NOT NULL UNIQUE," +
            "password TEXT NOT NULL," +
            "selectedQuestion TEXT," +
            "answer TEXT," +
            "nickname TEXT," +
            "email TEXT," +
            "gender TEXT," +
            ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Can't create a table for SQLite: " + e.getMessage());
            e.printStackTrace();
        }

        // Add missing columns manually (optional safety)
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("ALTER TABLE users ADD COLUMN kills INTEGER DEFAULT 0");
        } catch (SQLException ignored) {}
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("ALTER TABLE users ADD COLUMN timeAlive REAL DEFAULT 0.0");
        } catch (SQLException ignored) {}
    }


    public static UserDataSQL getInstance() {
        if (instance == null)
            instance = new UserDataSQL();
        return instance;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (username, password, selectedQuestion, answer, score, kills, timeAlive) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getUserSecurityQuestion().name());
            pstmt.setString(4, user.getSecurityAnswer());
            pstmt.setString(5, user.getNickName());
            pstmt.setString(6, user.getEmail());
            pstmt.setString(7, user.getGender());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Can't save user: " + e.getMessage());
            e.printStackTrace();
        }

        List<User> allUsers = getAllUsers();
        UserJsonUtil.saveUsersToJson(allUsers, "data/users.json");
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {

            while (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("nickname"),
                    rs.getString("email"),
                    rs.getString("gender")
                );
                user.setUserSecurityQuestion(SecurityQuestions.valueOf(rs.getString("selectedQuestion")));
                user.setSecurityAnswer(rs.getString("answer"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String password = rs.getString("password");
                String nickname = rs.getString("nickname");
                String email = rs.getString("email");
                String gender = rs.getString("gender");

                User user = new User(username, password, nickname, email, gender);

                String questionStr = rs.getString("selectedQuestion");
                if (questionStr != null) {
                    user.setUserSecurityQuestion(SecurityQuestions.valueOf(questionStr));
                }
                user.setSecurityAnswer(rs.getString("answer"));

                return user;
            }

        } catch (SQLException e) {
            System.err.println("There was a problem while trying to get user: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    public void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println("Can't delete user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateUsername(String oldUsername, String newUsername) {
        String sql = "UPDATE users SET username = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUsername);
            pstmt.setString(2, oldUsername);
            pstmt.executeUpdate();
            List<User> allUsers = getAllUsers();
            UserJsonUtil.saveUsersToJson(allUsers, "data/users.json");
        }
        catch (SQLException e) {
            System.err.println("Can't update username: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updatePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            List<User> allUsers = getAllUsers();
            UserJsonUtil.saveUsersToJson(allUsers, "data/users.json");
        }
        catch (SQLException e) {
            System.err.println("Can't update password: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateNickname(String username, String newNickname) {
        String sql = "UPDATE users SET nickname = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newNickname);
            pstmt.setString(2, username);
            pstmt.executeUpdate();

            List<User> allUsers = getAllUsers();
            UserJsonUtil.saveUsersToJson(allUsers, "data/users.json");
        }
        catch (SQLException e) {
            System.err.println("Can't update nickname: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateEmail(String username, String newEmail) {
        String sql = "UPDATE users SET email = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newEmail);
            pstmt.setString(2, username);
            pstmt.executeUpdate();

            List<User> allUsers = getAllUsers();
            UserJsonUtil.saveUsersToJson(allUsers, "data/users.json");
        }
        catch (SQLException e) {
            System.err.println("Can't update email: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
