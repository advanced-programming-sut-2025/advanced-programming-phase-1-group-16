package com.group16.stardewvalley.model.app;

import com.group16.stardewvalley.model.Shops.Building;
import com.group16.stardewvalley.model.Shops.Shop;
import com.group16.stardewvalley.model.Weather.WeatherCondition;
import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.user.Player;

import com.group16.stardewvalley.model.time.TimeDate;
import java.util.ArrayList;


public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private int currentPlayerIndex; //hamoon turn
    private final Player creator;
    private Player loader = null;
    private int turnsPassedInRound;   // counts up to players size
    private int turnsPassed;              // total rounds played
    private TimeDate timeDate;
    private Tile[][] map;
    private final int mapHeight = 200;
    private final int mapWidth = 300;
    private WeatherCondition weatherCondition;
    private WeatherCondition tomorrowWeatherCondition;
    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }
    public ArrayList<Animal> gameAnimals = new ArrayList<>();
    public ArrayList<Building> buildings = new ArrayList<>();
    private final int MAX_FARMINGABILITY_LEVE = 4;
    private final int MAX_MININGABILITY_LEVELL = 4;
    private final int MAX_NATURETOURISMABILITY_LEVEL = 4;
    private final int MAX_FISHINGABILITY_LEVEL = 4;
    private final ArrayList<Shop> shops = new ArrayList<>();
    private final List<NPC> NPCs;

    public Game(Player creator, ArrayList<Player> players) {
        this.creator = creator;
        this.players = players;
        this.turnsPassed = 0;
        this.timeDate = new TimeDate(App.getActiveGame());
        this.shops.add(new Blacksmith());
        this.shops.add(new JojaMart());
        this.shops.add(new PierresGeneralStore());
        this.shops.add(new CarpentersShop());
        this.shops.add(new FishShop());
        this.shops.add(new MarniesRanch());
        this.shops.add(new TheStardropSaloon());
        this.NPCs = new ArrayList<>();
        NPCs.add(new NPC(NPCType.Sebastian));
        NPCs.add(new NPC(NPCType.Abigail));
        NPCs.add(new NPC(NPCType.Harvey));
        NPCs.add(new NPC(NPCType.Leah));
        NPCs.add(new NPC(NPCType.Robin));
    }

    public TimeDate getTimeDate() {
        return timeDate;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);    }


    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public Tile[][] getMap() {
        return map;
    }

    public void setMap(Tile[][] map) {
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
//        if (timeDate.getHour() == 8) {
//            onEightAM();
//        }

        System.out.println("its " + getCurrentPlayer().getUser().getUsername() + "  turn now.");
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


    public ArrayList<Animal> getGameAnimals() {
        return gameAnimals;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }
}
