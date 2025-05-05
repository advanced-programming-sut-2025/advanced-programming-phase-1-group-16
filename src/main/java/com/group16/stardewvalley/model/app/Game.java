package com.group16.stardewvalley.model.app;

import com.group16.stardewvalley.model.Weather.WeatherCondition;
import com.group16.stardewvalley.model.map.Map;
import com.group16.stardewvalley.model.map.Position;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.user.;
import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private TileType[][] map;
    private final int mapHeight = 200;
    private final int mapWidth = 300;
    private Player currentPlayer;
    private Player creator;
    private Player loader = null;
    private GameState gameState = GameState.WAITING_FOR_NEW_GAME;
    private WeatherCondition weatherCondition;
    private WeatherCondition tomorrowWeatherCondition;

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }


    public Game(Player creator, ArrayList<Player> players) {
        this.creator = creator;
        this.players = players;
    }

    public void setWeatherCondition(WeatherCondition weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public void setTomorrowWeatherCondition(WeatherCondition weatherCondition) {
        this.tomorrowWeatherCondition = weatherCondition;
    }

    public WeatherCondition getTomorrowWeatherCondition() {
        return tomorrowWeatherCondition;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public TileType[][] getMap() {
        return map;
    }

    public void setMap(TileType[][] map) {
        this.map = map;
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


    public void nextTurn() {
    }

    public Position choosePoorForFirelight() {
        // رندوم باید یکی انتخاب بشه برای این کار
    }
}