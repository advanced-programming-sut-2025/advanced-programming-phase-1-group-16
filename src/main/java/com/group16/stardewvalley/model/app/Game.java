package com.group16.stardewvalley.model.app;

import com.group16.stardewvalley.model.map.FarmType;
import com.group16.stardewvalley.model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private ArrayList<User> players = new ArrayList<User>();
    private Map<User, FarmType> UsersFarm = new HashMap<User, FarmType>();
    private User currentPlayer;
    private User creator;
    private User loader = null;



    public Game(User creator, ArrayList<User> players) {
        this.creator = creator;
        this.players = players;
        this.UsersFarm = new HashMap<>();
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public User getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(User currentPlayer) {  //next turn
        this.currentPlayer = currentPlayer;
    }

    public void setPlayers(ArrayList<User> players) {
        this.players = players;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getLoader() {
        return loader;
    }

    public void setLoader(User loader) {
        this.loader = loader;
    }

    public Map<User, FarmType> getUsersFarm() {
        return UsersFarm;
    }

    public void setUsersFarm(Map<User, FarmType> usersFarm) {
        UsersFarm = usersFarm;
    }

    public void addUserFarm (User user, FarmType type){
        UsersFarm.put(user, type);
    }
}
