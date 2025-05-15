package com.group16.stardewvalley.model.app;

import com.group16.stardewvalley.model.NPC.NPC;
import com.group16.stardewvalley.model.NPC.NPCType;
import com.group16.stardewvalley.model.map.Direction;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.shops.*;
import com.group16.stardewvalley.model.weather.WeatherCondition;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;

import com.group16.stardewvalley.model.time.TimeDate;
import java.util.ArrayList;
import java.util.List;


public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private int currentPlayerIndex; //hamoon turn
    private final Player creator;
    private Player loader = null;
    private int turnsPassedInRound;   // counts up to players size
    private int turnsPassed;              // total rounds played
    private final TimeDate timeDate;
    private final int MAX_FARMINGABILITY_LEVE = 4;
    private final int MAX_MININGABILITY_LEVELL = 4;
    private final int MAX_NATURETOURISMABILITY_LEVEL = 4;
    private final int MAX_FISHINGABILITY_LEVEL = 4;
    private final Blacksmith blacksmith;
    private final JojaMart jojaMart;
    private final PierresGeneralStore pierresGeneralStore;
    private final CarpentersShop carpentersShop;
    private final FishShop fishShop;
    private final MarniesRanch marniesRanch;
    private final TheStardropSaloon theStardropSaloon;
    private final List<NPC> NPCs;

    private Tile[][] map;
    private final int mapHeight = 200;
    private final int mapWidth = 300;

    private WeatherCondition weatherCondition;
    private WeatherCondition tomorrowWeatherCondition;

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }


    public Game(Player creator, ArrayList<Player> players) {
        this.timeDate = TimeDate.getInstance(this);
        this.creator = creator;
        this.players = players;
        this.turnsPassed = 0;
        this.blacksmith = new Blacksmith();
        this.jojaMart = new JojaMart();
        this.pierresGeneralStore = new PierresGeneralStore();
        this.carpentersShop = new CarpentersShop();
        this.fishShop = new FishShop();
        this.marniesRanch = new MarniesRanch();
        this.theStardropSaloon = new TheStardropSaloon();
        this.NPCs = new ArrayList<>();
        NPCs.add(new NPC(NPCType.Sebastian, new Pos())); //---> TODO اتنا
        NPCs.add(new NPC(NPCType.Abigail, new Pos()));
        NPCs.add(new NPC(NPCType.Harvey, new Pos()));
        NPCs.add(new NPC(NPCType.Leah, new Pos()));
        NPCs.add(new NPC(NPCType.Robin, new Pos()));
    }

    public NPC getNPCByName(String NPCName) {
        for (NPC npc : NPCs) {
            if (npc.getName().equalsIgnoreCase(NPCName)) {
                return npc;
            }
        }
        return null;
    }

    public TimeDate getTimeDate() {
        return timeDate;
    }

    public Blacksmith getBlacksmith() {
        return blacksmith;
    }

    public JojaMart getJojaMart() {
        return jojaMart;
    }

    public PierresGeneralStore getPierresGeneralStore() {
        return pierresGeneralStore;
    }

    public CarpentersShop getCarpentersShop() {
        return carpentersShop;
    }

    public FishShop getFishShop() {
        return fishShop;
    }

    public MarniesRanch getMarniesRanch() {
        return marniesRanch;
    }

    public TheStardropSaloon getTheStardropSaloon() {
        return theStardropSaloon;
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

        // اینجا چرا چیزی پرینت شده ؟؟ اصلا توی پلیر مگه یوزر هست به چه دردی میخوره ؟
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

    public boolean isAdjacent(Pos pos1, Pos pos2) {
        for (Direction dir : Direction.values()) {
            Pos adjacentPos = dir.applyToPosition(pos1);
            if (adjacentPos.equals(pos2)) {
                return true;
            }
        }
        return false;
    }

}