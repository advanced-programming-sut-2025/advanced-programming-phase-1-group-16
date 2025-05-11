package com.group16.stardewvalley.model.user;

import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Farm;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String nickName;
    private String email;
    private final String gender;
    private ArrayList<Game> playedGames;

    private int money;
    private int gamePlayed;

    private boolean logged_in_flag;
    private SecurityQuestions userSecurityQuestion;
    private String securityAnswer;

    boolean activeGame;
    private Game currentGame;

    public User(String username, String password, String nickName, String email, String gender) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.gender = gender;
        this.logged_in_flag = false;
        this.activeGame = false;
        this.playedGames = new ArrayList<>();
    }

    public ArrayList<Game> getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(ArrayList<Game> playedGames) {
        this.playedGames = playedGames;
    }

    public void addPlayedGame(Game game) {
        playedGames.add(game);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public SecurityQuestions getUserSecurityQuestion() {
        return userSecurityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public Game getCurrentGroup() {
        return currentGame;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserSecurityQuestion(SecurityQuestions userSecurityQuestion) {
        this.userSecurityQuestion = userSecurityQuestion;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public void setCurrentGroup(Game currentGame) {
        this.currentGame = currentGame;
    }


    public boolean isLogged_in_flag() {
        return logged_in_flag;
    }

    public void setLogged_in_flag(boolean logged_in_flag) {
        this.logged_in_flag = logged_in_flag;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getGamePlayed() {
        return gamePlayed;
    }

    public boolean isActiveGame() {
        return activeGame;
    }

    public void setActiveGame(boolean activeGame) {
        this.activeGame = activeGame;
    }

    public void setGamePlayed(int gamePlayed) {
        this.gamePlayed = gamePlayed;
    }

    public static User  getUserByUsername(String username) {
        for (User user : App.getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // Username not found
    }

}