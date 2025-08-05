package com.group16.stardewvalley.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.group16.stardewvalley.data.UserDataSQL;
import com.group16.stardewvalley.model.app.Game;


public class User {
    //register data
    private String username;
    private String password;
    private String nickName;
    private String email;
    private final String gender;

    private int gamePlayed;

    private boolean logged_in_flag;
    private SecurityQuestions userSecurityQuestion;
    private String securityAnswer;

    boolean hasActiveGame;
    private Game currentGame;

//    public User(String username, String password, String nickName, String email, String gender) {
//        this.username = username;
//        this.password = password;
//        this.nickName = nickName;
//        this.email = email;
//        this.gender = gender;
//        this.logged_in_flag = false;
//        this.hasActiveGame = false;
//    }


    // Default constructor needed for Jackson
    public User() {
        // Initialize defaults if needed
        this.gender = "";
    }

    @JsonCreator
    public User(@JsonProperty("username") String username,
                @JsonProperty("password") String password,
                @JsonProperty("nickName") String nickName,
                @JsonProperty("email") String email,
                @JsonProperty("gender") String gender) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.gender = gender;
        this.logged_in_flag = false;
        this.hasActiveGame = false;
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

    @JsonIgnore
    public Game getCurrentGame() {
        return currentGame;
    }

    public void setUsername(String username) {
        UserDataSQL.getInstance().updateUsername(this.username, username);

        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
        UserDataSQL.getInstance().updatePassword(username, this.password);
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        UserDataSQL.getInstance().updateNickname(username, nickName);
    }

    public void setEmail(String email) {
        this.email = email;
        UserDataSQL.getInstance().updateEmail(username, email);
    }

    public void setUserSecurityQuestion(SecurityQuestions userSecurityQuestion) {
        this.userSecurityQuestion = userSecurityQuestion;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    @JsonIgnore
    public void setCurrentGroup(Game currentGame) {
        this.currentGame = currentGame;
    }

    public boolean isLogged_in_flag() {
        return logged_in_flag;
    }

    public void setLogged_in_flag(boolean logged_in_flag) {
        this.logged_in_flag = logged_in_flag;
    }

    public int getGamePlayed() {
        return gamePlayed;
    }

    public boolean getHasActiveGame() {
        return hasActiveGame;
    }

    public void setHasActiveGame(boolean hasActiveGame) {
        this.hasActiveGame = hasActiveGame;
    }

    public void setGamePlayed(int gamePlayed) {
        this.gamePlayed = gamePlayed;
    }

}
