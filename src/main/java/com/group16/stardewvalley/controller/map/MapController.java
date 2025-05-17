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
            player.setPosition(dest);
            return new Result(false, "Moved to <" + dest.getX() + "," + dest.getY() + "> but \033[31mYou fainted\033[0m");
        }
        player.setPosition(dest);
        player.decreaseEnergy(pathInfo.energyCost());
        return new Result(true, "Moved to <" + dest.getX() + "," + dest.getY() + ">");
    }

    private boolean isValidPos(Pos pos, int width, int height) {
        int x = pos.getX(), y = pos.getY();
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    private PathInfo calculatePathInfo(Pos start, Pos dest) {
        Tile[][] map = App.getActiveGame().getMap();
        int height = map.length;
        int width = map[0].length;

        if (!isValidPos(dest, width, height) || !map[dest.getX()][dest.getY()].isTileEmpty()) {
            return PathInfo.invalid("Invalid destination.");
        }

        List<Pos> path = findShortestPath(map, start, dest);
        if (path == null) {
            return PathInfo.invalid("No path exists.");
        }

        int energyCost = 0;
        Pos prev = start;
        Direction lastDirection = null;

        for (int i = 1; i < path.size(); i++) {
            Pos curr = path.get(i);
            Tile tile = map[curr.getX()][curr.getY()];

            // Base cost per step
            int stepCost = 1;

            // Additional cost based on tile type
            switch (tile.getType()) {
                case Plowed -> stepCost += 2;
                case Quarry -> stepCost += 1;
                case  GreenHouse -> stepCost -= 1;
                default -> {} // no change for Ground or others
            }

            // Add extra cost for direction change
            Direction currentDirection = getDirection(prev, curr);
            if (lastDirection != null && currentDirection != lastDirection) {
                stepCost += 1; // turning costs more energy
            }

            energyCost += stepCost;
            lastDirection = currentDirection;
            prev = curr;
        }
        energyCost /= 20;

        return PathInfo.valid(path, energyCost);
    }

    private Direction getDirection(Pos from, Pos to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        if (dx == 1) return Direction.RIGHT;
        if (dx == -1) return Direction.LEFT;
        if (dy == 1) return Direction.DOWN;
        if (dy == -1) return Direction.UP;
        return null;
    }

    // Enum for direction
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private List<Pos> findShortestPath(Tile[][] map, Pos start, Pos dest) {
        int height = map.length;
        int width = map[0].length;

        boolean[][] visited = new boolean[height][width];
        Pos[][] parent = new Pos[height][width];
        Queue<Pos> queue = new LinkedList<>();
        queue.add(start);
        visited[start.getY()][start.getX()] = true;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            Pos curr = queue.poll();
            if (curr.isEqual(dest)) {
                return reconstructPath(parent, dest, start);
            }

            for (int i = 0; i < 4; i++) {
                int nx = curr.getX() + dx[i];
                int ny = curr.getY() + dy[i];
                Pos next = new Pos(nx, ny);

                if (isValidPos(next, width, height)
                        && !visited[ny][nx]
                        && map[ny][nx].isTileEmpty()) {
                    visited[ny][nx] = true;
                    parent[ny][nx] = curr;
                    queue.add(next);
                }
            }
        }

        return null;
    }


    private List<Pos> reconstructPath(Pos[][] parent, Pos end, Pos start) {
        List<Pos> path = new ArrayList<>();
        for (Pos at = end; at != null && !at.equals(start); at = parent[at.getY()][at.getX()]) {
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

        for (int i = y; i < Math.min(y + size, height); i++) {
            for (int j = x; j < Math.min(x + size, width); j++) {
                if (i < 0 || j < 0 || i >= height || j >= width) {
                    continue;
                }

                boolean playerDrawn = false;
                for (Player player : App.getActiveGame().getPlayers()) {
                    if (player.getPosition().getY() == i && player.getPosition().getX() == j) {
                        if (player.isFainted()) {
                            builder.append("\u001B[31mx\u001B[0m");
                        } else {
                            builder.append("\u001B[31m@\u001B[0m");
                        }
                        playerDrawn = true;
                        break; // چون پلیر پیدا شد، بقیه پلیرها مهم نیستن
                    }
                }

                if (playerDrawn) continue; // اگر پلیر چاپ شد، بقیه‌ی شرط‌ها رو چک نکن

                if (map[i][j].isBurned()) {
                    builder.append("B");
                    continue;
                }

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
