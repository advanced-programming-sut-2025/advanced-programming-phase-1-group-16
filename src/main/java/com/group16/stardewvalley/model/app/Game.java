package com.group16.stardewvalley.model.app;

import com.group16.stardewvalley.model.map.FarmType;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.model.time.TimeDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private ArrayList<User> players = new ArrayList<User>();
    private int currentPlayerIndex; //hamoon turn

    private Map<User, FarmType> UsersFarm = new HashMap<User, FarmType>();
    private User creator;
    private User loader = null;

    private int turnsPassedInRound;   // counts up to 4
    private int currentEnergy;
    private int turnsPassed;              // total rounds played
    private TimeDate timeDate;

    public Game(User creator, ArrayList<User> players) {
        this.creator = creator;
        this.players = players;
        this.UsersFarm = new HashMap<>();
        this.turnsPassed = 0;
        this.currentEnergy = 50;
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public User getCurrentPlayer() {
        return players.get(currentPlayerIndex);    }


    public void setPlayers(ArrayList<User> players) {
        this.players = players;
    }

    public User getCreator() {
        return creator;
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

    public void addUserFarm (User user, FarmType type){
        UsersFarm.put(user, type);
    }

    public boolean useEnergy(int amount) {
        if (amount > currentEnergy) {
            System.out.println("خطا: انرژی کافی نیست.");
            return false;
        }
        currentEnergy -= amount;
        return true;
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentEnergy = 50;

        turnsPassedInRound++;

        // After all players have played → pass 1 hour
        if (turnsPassedInRound >= players.size()) {
            turnsPassedInRound = 0;
            timeDate.advanceOneHour();  // ✅ advance time here
        }

        System.out.println("its " + getCurrentPlayer().getUsername() + "  turn.");
    }
}
