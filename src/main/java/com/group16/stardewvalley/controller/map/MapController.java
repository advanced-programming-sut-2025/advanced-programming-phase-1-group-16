package com.group16.stardewvalley.controller.map;

import com.group16.stardewvalley.model.NPC.NPC;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Mineral;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.items.Stone;
import com.group16.stardewvalley.model.map.*;
import com.group16.stardewvalley.model.shops.Shop;
import com.group16.stardewvalley.model.user.Player;

import java.util.*;

public class MapController {
    public void createMap() {
        Game game = App.getActiveGame();
        Tile[][] map = new Tile[game.getMapHeight()][game.getMapWidth()];
        for (int i = 0; i < game.getMapHeight(); i++) {
            for (int j = 0; j < game.getMapWidth(); j++) {
                map[i][j] = new Tile(TileType.Ground);
            }
        }
        //مشخص کردن نقطه شروع مزرعه
        Pos[] positions = {
                new Pos(0,0),
                new Pos(game.getMapWidth() - 80, 2),
                new Pos(40, game.getMapHeight() - 70),
                new Pos(game.getMapWidth() - 80, game.getMapHeight() - 70)
        };
        int index = 0;
        for (Player player : game.getPlayers()) {
            player.getFarm().setStartPosition(positions[index]);
            int x, y;
            Random r = new Random();
            do {
                x = r.nextInt(player.getFarm().getType().getWidth());
                y = r.nextInt(player.getFarm().getType().getHeight());
            } while (player.getFarm().getType().getTiles()[y][x] != TileType.Ground);
            player.setPosition(new Pos(player.getFarm().getStartPosition().getX() + x,  player.getFarm().getStartPosition().getY() + y));
            index++;
        }

        for (Player player : game.getPlayers()) {
            for (int i = 0; i < player.getFarm().getType().getHeight()-1; i++) {
                for (int j = 0; j < player.getFarm().getType().getWidth()-1; j++) {
                    map[i + player.getFarm().getStartPosition().getY()][j + player.getFarm().getStartPosition().getX()] =
                            new Tile(player.getFarm().getType().getTiles()[i][j]);
                    map[i + player.getFarm().getStartPosition().getY()][j + player.getFarm().getStartPosition().getX()].setLocation(Location.Farm);
                }
            }
        }

        for (Shop shop : App.getActiveGame().getShops()) {
            PlaceType placeType = shop.getPlaceType();
            for (int i = 0; i < placeType.getHeight()-1; i++) {
                for (int j = 0; j < placeType.getWidth()-1; j++) {
                    map[i + placeType.getStartPosition().getY()][j + placeType.getStartPosition().getX()] =
                            new Tile(placeType.getTiles()[i][j]);
                    map[i + placeType.getStartPosition().getY()][j + placeType.getStartPosition().getX()].setLocation(getLocationByName(shop.getShopName()));
                }
            }
        }
        for (NPC npc : App.getActiveGame().getNPCs()) {
            PlaceType placeType = npc.getNpcType().getPlaceType();
            for (int i = 0; i < placeType.getHeight()-1; i++) {
                for (int j = 0; j < placeType.getWidth()-1; j++) {
                    map[i + placeType.getStartPosition().getY()][j + placeType.getStartPosition().getX()] =
                            new Tile(placeType.getTiles()[i][j]);
                    map[i + placeType.getStartPosition().getY()][j + placeType.getStartPosition().getX()].setLocation(Location.NPCFarm);
                }
            }
        }
        game.setMap(map);
    }

    private Location getLocationByName(String name) {
        return switch (name) {
            case "Blacksmith" -> Location.Blacksmith;
            case "Carpenter's Shop" -> Location.CarpentersShop;
            case "Fish Shop" -> Location.FishShop;
            case "JojaMart" -> Location.JojaMart;
            case "Marnie's Ranch" -> Location.MarniesRanch;
            case "Pierr's General Store" -> Location.PierresGeneralStore;
            case "The Stardrop Saloon" -> Location.TheStardropSaloon;
            default -> null;
        };
    }

    public Result askWalking(int x, int y) {
        Pos dest = new Pos(x, y);
        Player player = App.getActiveGame().getCurrentPlayer();

        if (isInOtherPlayersFarm(dest, player, App.getActiveGame().getPlayers())) {
            return new Result(false, "Destination is inside another player's farm.");
        }

        PathInfo pathInfo = calculatePathInfo(player.getPosition(), dest);
        if (!pathInfo.isValid()) {
            return new Result(false, pathInfo.message());
        }

        return new Result(true, "It takes you " + pathInfo.energyCost() + " energy. Do you want to go there?");
    }

    public Result walk(int x, int y) {
        Pos dest = new Pos(x, y);
        Player player = App.getActiveGame().getCurrentPlayer();

        PathInfo pathInfo = calculatePathInfo(player.getPosition(), dest);
        if (!pathInfo.isValid()) {
            return new Result(false, pathInfo.message());
        }
        if (player.getEnergy() < pathInfo.energyCost()) {
            player.faint();
            return new Result(false, "You fainted");
        }

        player.setPosition(dest);
        player.setEnergy(player.getEnergy() - pathInfo.energyCost());

        return new Result(true, "Moved to <" + dest.getX() + "," + dest.getY() + ">");
    }

    private boolean isValidPos(Pos pos, int width, int height) {
        int x = pos.getX(), y = pos.getY();
        return x >= 0 && y >= 0 && x < height && y < width;
    }

    private PathInfo calculatePathInfo(Pos start, Pos dest) {
        Tile[][] map = App.getActiveGame().getMap(); // فرض بر این است که map حالا از نوع Tile[][] است
        int height = map.length;
        int width = map[0].length;

        if (!isValidPos(dest, width, height) || map[dest.getX()][dest.getY()].getType() != TileType.Ground) {
            return PathInfo.invalid("Invalid destination.");
        }

        List<Pos> path = findShortestPath(map, start, dest);
        if (path == null) {
            return PathInfo.invalid("No path exists.");
        }

        int steps = path.size() - 1;
        int energyCost = steps / 20;

        return PathInfo.valid(path, energyCost);
    }

    private List<Pos> findShortestPath(Tile[][] map, Pos start, Pos dest) {
        int height = map.length;
        int width = map[0].length;

        boolean[][] visited = new boolean[height][width];
        Pos[][] parent = new Pos[height][width];
        Queue<Pos> queue = new LinkedList<>();
        queue.add(start);
        visited[start.getX()][start.getY()] = true;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            Pos curr = queue.poll();
            if (curr.equals(dest)) {
                return reconstructPath(parent, dest, start);
            }

            for (int i = 0; i < 4; i++) {
                int nx = curr.getX() + dx[i];
                int ny = curr.getY() + dy[i];
                Pos next = new Pos(nx, ny);

                if (isValidPos(next, width, height)
                        && !visited[nx][ny]
                        && map[nx][ny].getType() == TileType.Ground) {
                    visited[nx][ny] = true;
                    parent[nx][ny] = curr;
                    queue.add(next);
                }
            }
        }

        return null;
    }


    private List<Pos> reconstructPath(Pos[][] parent, Pos end, Pos start) {
        List<Pos> path = new ArrayList<>();
        for (Pos at = end; at != null && !at.equals(start); at = parent[at.getX()][at.getY()]) {
            path.add(at);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }


    private boolean isInOtherPlayersFarm(Pos dest, Player currentPlayer, List<Player> players) {
        for (Player player : players) {
            if (player == currentPlayer) continue;
            Pos start = player.getFarm().getStartPosition();
            int farmX = start.getX();
            int farmY = start.getY();
            int farmWidth = player.getFarm().getType().getWidth();
            int farmHeight = player.getFarm().getType().getHeight();

            if (dest.getX() >= farmX && dest.getX() < farmX + farmWidth &&
                    dest.getY() >= farmY && dest.getY() < farmY + farmHeight) {
                return true;
            }
        }
        return false;
    }

    public Result printMap(int x, int y, int size) {
        Tile[][] map = App.getActiveGame().getMap();
        int height = map.length;
        int width = map[0].length;

        StringBuilder builder = new StringBuilder();

        for (int i = y; i < y + size; i++) {
            for (int j = x; j < x + size; j++) {
                if (i < 0 || j < 0 || i >= height || j >= width) {
                    builder.append(" ");
                    continue;
                }

                boolean playerDrawn = false;
                for (Player player : App.getActiveGame().getPlayers()) {
                    if (player.getPosition().getY() == i && player.getPosition().getX() == j) {
                        builder.append("\u001B[31m@\u001B[0m");
                        playerDrawn = true;
                        break; // چون پلیر پیدا شد، بقیه پلیرها مهم نیستن
                    }
                }

                if (playerDrawn) continue; // اگر پلیر چاپ شد، بقیه‌ی شرط‌ها رو چک نکن

                if (map[i][j].getLocation().equals(Location.Farm)){
                    if (map[i][j].getTree() != null) {
                        builder.append(TileType.Tree.getColorCode()).append(TileType.Tree.getSymbol()).append("\033[0m");
                    } else if (map[i][j].getCrop() != null) {
                        builder.append(TileType.Forage.getColorCode()).append(TileType.Forage.getSymbol()).append("\033[0m");
                    } else if (map[i][j].getItem() != null && map[i][j].getItem() instanceof Stone) {
                        builder.append(TileType.Stone.getColorCode()).append(TileType.Stone.getSymbol()).append("\033[0m");
                    } else if (map[i][j].getItem() != null && map[i][j].getItem() instanceof Mineral) {
                        builder.append(TileType.MineralForage.getColorCode()).append(TileType.MineralForage.getSymbol()).append("\033[0m");
                    } else{
                        builder.append(map[i][j].getType().getColorCode()).append(map[i][j].getType().getSymbol()).append("\033[0m");
                    }
                }
                else if (map[i][j].getLocation().equals(Location.Game)) {
                    builder.append(map[i][j].getType().getSymbol());
                }
                else {
                    builder.append(map[i][j].getType().getColorCode()).append(map[i][j].getType().getSymbol()).append("\033[0m");
                }

            }
            builder.append("\n");
        }

        return new Result(true, builder.toString());
    }

    public Result helpReadingMap() {
        StringBuilder builder = new StringBuilder();
        for (TileType tileType : TileType.values()) {
            builder.append(tileType.name()).append("  :  ").append(tileType.getSymbol()).append("\n");
        }
        return new Result(true, builder.toString());
    }

    public static boolean isPlayerInFarm(Player currentPlayer) {
        Pos start = currentPlayer.getFarm().getStartPosition();
        Pos playerPos = currentPlayer.getPosition();
        return playerPos.getX() > start.getX() &&
                playerPos.getX() > start.getX() + currentPlayer.getFarm().getType().getWidth() &&
                playerPos.getY() > start.getY() &&
                playerPos.getY() > start.getY() + currentPlayer.getFarm().getType().getHeight();
    }

    public static boolean isPlayerInCottage(Player currentPlayer) {
        Pos pos = currentPlayer.getPosition();
        return App.getActiveGame().getMap()[pos.getY()][pos.getX()].getType() == TileType.Cottage;
    }



}
