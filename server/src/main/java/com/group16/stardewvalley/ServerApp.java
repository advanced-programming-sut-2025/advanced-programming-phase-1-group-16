package com.group16.stardewvalley;

import com.group16.stardewvalley.app.ClientConnectionThread;
import com.group16.stardewvalley.app.GameDelta;
import com.group16.stardewvalley.app.ListenerThread;
import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.agriculture.Crop;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.tools.Gadget;
import com.group16.stardewvalley.model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerApp {
    public static ArrayList<Game> games = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static final int TIMEOUT_MILLIS = 500;
    private static final ArrayList<ClientConnectionThread> connections = new ArrayList<>();
    private static boolean exitFlag = false;
    private static ListenerThread listenerThread;
    private static final Map<Game, GameDelta> pendingDeltas = new HashMap<>();

    public static void recordPlayerMove(Game game, String username, Pos pos) {
        GameDelta delta = pendingDeltas.computeIfAbsent(game, g -> new GameDelta());
        GameDelta.PlayerUpdate update = new GameDelta.PlayerUpdate();
        update.username = username;
        update.position = pos;
        delta.playerUpdates.add(update);
    }

    public static void recordTileChange(Game game, int tileX, int tileY, Tile tile) {
        GameDelta delta = pendingDeltas.computeIfAbsent(game, g -> new GameDelta());
        GameDelta.TileUpdate update = new GameDelta.TileUpdate();

        update.tileX = tileX;
        update.tileY = tileY;

        // Crop info
        if (tile.getCrop() != null) {
            update.cropType = tile.getCrop().getCropType().name();
            update.growthStage = tile.getCrop().getFinalStage();
        } else {
            update.cropType = null;
            update.growthStage = 0;
        }

        // Tree info
        if (tile.getTree() != null) {
            update.treeType = tile.getTree().getTreeType().name();
            update.treeStage = tile.getTree().getStage();
        } else {
            update.treeType = null;
            update.treeStage = 0;
        }

        // Item info
        if (tile.getItem() != null) {
            update.itemName = tile.getItem().getName();
        } else {
            update.itemName = null;
        }

        // Other tile states
        update.tileType = tile.getType().name();
        update.isFertilized = tile.isFertilized();
        update.fertilizerType = tile.getFertilizerType() != null ? tile.getFertilizerType().name() : null;
        update.hasWater = tile.isHasWater();
        update.isBurned = tile.isBurned();
        update.isPlowed = tile.isPlowed();

        delta.tileUpdates.add(update);
    }


    public static void recordInventoryChange(Game game, String username, Inventory inventory) {
        GameDelta delta = pendingDeltas.computeIfAbsent(game, g -> new GameDelta());
        GameDelta.InventoryUpdate update = new GameDelta.InventoryUpdate();

        update.username = username;

        // Tools
        Map<String, Integer> toolMap = new HashMap<>();
        for (Map.Entry<Gadget, Integer> entry : inventory.getTools().entrySet()) {
            toolMap.put(entry.getKey().getName(), entry.getValue());
        }
        update.tools = toolMap;

        // Items
        Map<String, Integer> itemMap = new HashMap<>();
        for (Map.Entry<Item, Integer> entry : inventory.getItems().entrySet()) {
            itemMap.put(entry.getKey().getName(), entry.getValue());
        }
        update.items = itemMap;

        // Crops
        Map<String, Integer> cropMap = new HashMap<>();
        for (Map.Entry<Crop, Integer> entry : inventory.getCrops().entrySet()) {
            cropMap.put(entry.getKey().getCropType().getName(), entry.getValue());
        }
        update.crops = cropMap;

        // Crafting recipes
        update.craftingRecipes = inventory.getCraftingRecipes()
            .stream()
            .map(CraftingRecipes::name)
            .collect(Collectors.toList());

        // Backpack info
        update.backPackType = inventory.getBackPackType().name();
        update.capacity = inventory.getCapacity();

        delta.inventoryUpdates.add(update);
    }


    public static void broadcastDeltas() {
        synchronized (games) {
            for (Game game : games) {
                GameDelta delta = pendingDeltas.remove(game);
                if (delta == null) continue; // nothing changed

                String json = JSONUtils.toJson(delta);

                HashMap<String, Object> body = new HashMap<>();
                body.put("delta", json);
                Message deltaMsg = new Message(body, Message.Type.GAME_STATE_UPDATE);

                synchronized (connections) {
                    for (ClientConnectionThread connection : connections) {
                        User user = connection.getConnectedUser();
                        if (user != null && game.isPlayerInGame(user)) {
                            connection.sendMessage(deltaMsg);
                        }
                    }
                }
            }
        }
    }

    public static ClientConnectionThread getConnectionByIpPort(String ip, int port) {
        synchronized (connections) {
            for (ClientConnectionThread connection : connections) {
                if (connection.getOtherSidePort() == port && ip.equals(connection.getOtherSideIP())) {
                    return connection;
                }
            }
        }
        return null;
    }

    public static Game getActiveGameByUser(User user) {
        for (Game game : games) {
            for (User user1 : users) {
                if (user.getUsername().equals(user1.getUsername())) {
                    return game;
                }
            }
        }
        return null;
    }

    public static boolean isEnded() {
        return exitFlag;
    }

    public static void setListenerThread(ListenerThread listenerThread) {
        ServerApp.listenerThread = listenerThread;
    }

    public static List<ClientConnectionThread> getConnections() {
        synchronized (connections) {
            return List.copyOf(ServerApp.connections);
        }
    }

    public static void startListening() {
        if (listenerThread != null && !listenerThread.isAlive()) {
            listenerThread.start();
        } else {
            throw new IllegalStateException("Listener thread is already running or not set.");
        }
    }

    public static void endAll() {
        exitFlag = true;
        synchronized (connections) {
            for (ClientConnectionThread connection : connections) {
                connection.end();
            }
            connections.clear();
        }
    }

    public static void removeClientConnection(ClientConnectionThread clientConnectionThread) {
        if (clientConnectionThread != null) {
            synchronized (connections) {
                connections.remove(clientConnectionThread);
            }
            clientConnectionThread.end();
        }
    }

    public static void addClientConnection(ClientConnectionThread clientConnectionThread) {
        if (clientConnectionThread != null) {
            synchronized (connections) {
                if (!connections.contains(clientConnectionThread)) {
                    connections.add(clientConnectionThread);
                }
            }
        }
    }

    public static void addGame(Game game) {
        synchronized (games) {
            games.add(game);
        }
    }

    public static void removeGame(Game game) {
        synchronized (games) {
            games.remove(game);
        }
    }

    public static ArrayList<Game> getGroups() {
        return games;
    }

    public static void setGroups(ArrayList<Game> games) {
        ServerApp.games = games;
    }

    public static void addUser(User user) {
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        ServerApp.users = users;
    }

}
