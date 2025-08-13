package com.group16.stardewvalley.model.app;

import com.group16.stardewvalley.model.DTO.GameClientDTO;
import com.group16.stardewvalley.model.DTO.NPCDTO;
import com.group16.stardewvalley.model.DTO.PlayerDTO;
import com.group16.stardewvalley.model.NPC.NPC;
import com.group16.stardewvalley.model.NPC.NPCType;
import com.group16.stardewvalley.model.map.Direction;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.shops.*;
import com.group16.stardewvalley.model.time.Season;
import com.group16.stardewvalley.model.user.User;
import com.group16.stardewvalley.model.weather.WeatherCondition;
import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.time.TimeDate;

import java.util.*;

public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private Tile[][] map;
    private final int mapHeight = 200;
    private final int mapWidth = 300;
    private int currentPlayerIndex; //hamoon turn
    private final Player creator;
    private Player loader = null;
    private int turnsPassedInRound;   // counts up to players size
    private int turnsPassed;              // total rounds played
    private TimeDate timeDate;
    private final int MAX_FARMINGABILITY_LEVE = 4;
    private final int MAX_MININGABILITY_LEVELL = 4;
    private final int MAX_NATURETOURISMABILITY_LEVEL = 4;
    private final int MAX_FISHINGABILITY_LEVEL = 4;
    private final ArrayList<Shop> shops = new ArrayList<>();
    private final List<NPC> NPCs;
    public ArrayList<Building> buildings = new ArrayList<>();
    private WeatherCondition weatherCondition;
    private WeatherCondition tomorrowWeatherCondition;
    public ArrayList<Animal> gameAnimals = new ArrayList<>();

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }

    public Game(Player creator, ArrayList<Player> players) {
        this.creator = creator;
        this.players = players;
        this.turnsPassed = 0;
        this.timeDate = new TimeDate();
        App.setActiveGame(this);
        this.shops.add(Blacksmith.getInstance());
        this.shops.add(JojaMart.getInstance());
        this.shops.add(PierresGeneralStore.getInstance());
        this.shops.add(CarpentersShop.getInstance());
        this.shops.add(FishShop.getInstance());
        this.shops.add(MarniesRanch.getInstance());
        this.shops.add(TheStardropSaloon.getInstance());
        this.weatherCondition = WeatherCondition.SUNNY;
        this.tomorrowWeatherCondition = WeatherCondition.SUNNY;
        this.NPCs = new ArrayList<>();

        NPCs.add(new NPC(NPCType.Sebastian));
        NPCs.add(new NPC(NPCType.Abigail));
        NPCs.add(new NPC(NPCType.Harvey));
        NPCs.add(new NPC(NPCType.Leah));
        NPCs.add(new NPC(NPCType.Robin));
    }

    //pregame:
    private final Map<User, String> farmSelections = new HashMap<>();
    private final Set<User> readyUsers = new HashSet<>();

    public synchronized void setFarmSelection(User user, String farm) {
        farmSelections.put(user, farm);
    }

    public synchronized void setReady(User user) {
        readyUsers.add(user);
    }

    public synchronized boolean allReady() {
        List<User> gameUsers = players.stream()
            .map(Player::getUser)
            .toList();

        return readyUsers.containsAll(gameUsers) &&
            farmSelections.keySet().containsAll(gameUsers);
    }

    public synchronized Map<User, String> getSelections() {
        return new HashMap<>(farmSelections);
    }

    public Player getPlayerByUser(User user) {
        for (Player player : players) {
            if (player.getUser().getUsername().equals(user.getUsername())) {
                return player;
            }
        }
        return null;
    }



    public List<NPC> getNPCs() {
        return NPCs;
    }

    public ArrayList<Shop> getShops() {
        return shops;
    }

    public NPC getNPCByName(String NPCName) {
        for (NPC npc : NPCs) {
            if (npc.getName().equalsIgnoreCase(NPCName)) {
                return npc;
            }
        }
        return null;
    }

    public Season getSeason(){
        return timeDate.getSeason();
    }

    public Blacksmith getBlacksmith() {
        for (Shop shop : shops) {
            if (shop instanceof Blacksmith) {
                return (Blacksmith) shop;
            }
        }
        return null;
    }

    public JojaMart getJojaMart() {
        for (Shop shop : shops) {
            if (shop instanceof JojaMart) {
                return (JojaMart) shop;
            }
        }
        return null;
    }

    public PierresGeneralStore getPierresGeneralStore() {
        for (Shop shop : shops) {
            if (shop instanceof PierresGeneralStore) {
                return (PierresGeneralStore) shop;
            }
        }
        return null;
    }

    public CarpentersShop getCarpentersShop() {
        for (Shop shop : shops) {
            if (shop instanceof CarpentersShop) {
                return (CarpentersShop) shop;
            }
        }
        return null;
    }

    public FishShop getFishShop() {
        for (Shop shop : shops) {
            if (shop instanceof FishShop) {
                return (FishShop) shop;
            }
        }
        return null;
    }

    public MarniesRanch getMarniesRanch() {
        for (Shop shop : shops) {
            if (shop instanceof MarniesRanch) {
                return (MarniesRanch) shop;
            }
        }
        return null;
    }

    public TheStardropSaloon getTheStardropSaloon() {
        for (Shop shop : shops) {
            if (shop instanceof TheStardropSaloon) {
                return (TheStardropSaloon) shop;
            }
        }
        return null;
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

    public Player getPlayerByUsername(String username) {
        for (Player player : players) {
            if (player.getUsername().equalsIgnoreCase(username)) {
                return player;
            }
        }
        return null;
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
        if (players.stream().allMatch(Player::isFainted)) {
            timeDate.advanceOneDay();
            return;
        }
        int index = (currentPlayerIndex + 1) % players.size();
        int passTurn = 1;
        while (getPlayers().get(index).isFainted()) {
            index = (index + 1) % players.size();
            passTurn++;
        }
        currentPlayerIndex = index;

        turnsPassedInRound += passTurn;

        // After all players have played → pass 1 hour
        if (turnsPassedInRound >= players.size()) {
            turnsPassedInRound = 0;
            timeDate.advanceOneHour();
        }
//        if (timeDate.getHour() == 8) {
//            onEightAM();
//        }

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


    public ArrayList<Animal> getGameAnimals() {
        return gameAnimals;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public boolean isAdjacent(Pos pos1, Pos pos2) {
        int dx = Math.abs(pos1.getX() - pos2.getX());
        int dy = Math.abs(pos1.getY() - pos2.getY());
        return (dx <= 1 && dy <= 1) && !(dx == 0 && dy == 0);
    }

    public GameClientDTO toClientDTO() {
        GameClientDTO dto = new GameClientDTO();

        // بازیکنان
        List<PlayerDTO> playerDTOs = new ArrayList<>();
        for (Player p : players) {
            PlayerDTO pd = new PlayerDTO();
            pd.setUsername(p.getUser().getUsername());
            pd.setFainted(p.isFainted());
            pd.setX(p.getPosition().getX());
            pd.setY(p.getPosition().getY());
            playerDTOs.add(pd);
        }
        dto.setPlayers(playerDTOs);

        // بقیه اطلاعات
        dto.setCurrentPlayerIndex(currentPlayerIndex);
        dto.setTimeDate(timeDate);
        dto.setWeatherCondition(weatherCondition);
        dto.setTomorrowWeatherCondition(tomorrowWeatherCondition);
        dto.setSeason(timeDate.getSeason());

        // NPCها
        List<NPCDTO> npcDTOs = new ArrayList<>();
        for (NPC npc : NPCs) {
            NPCDTO nd = new NPCDTO();
            nd.setName(npc.getName());
            //nd.setX(npc.getPosition().getX());
            //nd.setY(npc.getPosition().getY());
            npcDTOs.add(nd);
        }
        dto.setNpcs(npcDTOs);

        return dto;
    }


}
