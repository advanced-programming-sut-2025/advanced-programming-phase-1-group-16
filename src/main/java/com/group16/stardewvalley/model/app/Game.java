package com.group16.stardewvalley.model.app;

import com.group16.stardewvalley.model.user.Player;
import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private Player creator;
    private Player loader = null;
    private GameState gameState = GameState.WAITING_FOR_NEW_GAME;




    public Game(Player creator, ArrayList<Player> players) {
        this.creator = creator;
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {  //next turn
        this.currentPlayer = currentPlayer;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Player getCreator() {
        return creator;
    }

    public void setCreator(Player creator) {
        this.creator = creator;
    }

    public Player getLoader() {
        return loader;
    }

    public void setLoader(Player loader) {
        this.loader = loader;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
