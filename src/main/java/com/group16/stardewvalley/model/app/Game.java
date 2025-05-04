package com.group16.stardewvalley.model.app;

import com.group16.stardewvalley.model.map.FarmType;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.model.time.TimeDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private int currentPlayerIndex; //hamoon turn

    private final Player creator;
    private Player loader = null;

    private int turnsPassedInRound;   // counts up to players size
    private int turnsPassed;              // total rounds played
    private TimeDate timeDate;


    private TileType[][] map;
    private int height;
    private int width;




    public Game(Player creator, ArrayList<Player> players) {
        this.creator = creator;
        this.players = players;
        this.turnsPassed = 0;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);    }


    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }



    public TileType[][] getMap() {
        return map;
    }

    public void setMap(TileType[][] map) {
        this.map = map;
    }


    public Player getCreator() {
        return creator;
    }


    public Player getLoader() {
        return loader;
    }

    public void setLoader(Player loader) {
        this.loader = loader;
    }



    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

        turnsPassedInRound++;

        // After all players have played → pass 1 hour
        if (turnsPassedInRound >= players.size()) {
            turnsPassedInRound = 0;
            timeDate.advanceOneHour();
        }

        System.out.println("its " + getCurrentPlayer().getUser().getUsername() + "  turn now.");
    }
}
