package com.group16.stardewvalley.controller.map;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.PathInfo;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.user.Player;

import java.util.*;

public class MapController {
    Game game = App.getActiveGame();

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
                new Pos(70, game.getMapHeight() - 70),
                new Pos(game.getMapWidth() - 80, game.getMapHeight() - 70)
        };
        int index = 0;
        for (Player player : game.getPlayers()) {
            player.getFarm().setStartPosition(positions[index]);
            index++;
        }
        for (Player player : game.getPlayers()) {
            for (int i = 0; i < player.getFarm().getType().getHeight()-1; i++) {
                for (int j = 0; j < player.getFarm().getType().getWidth()-1; j++) {
                    map[i + player.getFarm().getStartPosition().getY()][j + player.getFarm().getStartPosition().getX()] =
                            new Tile(player.getFarm().getType().getTiles()[i][j]);
                }
            }
        }
        game.setMap(map);
    }

    public Result askWalking(int x, int y) {
        Pos dest = new Pos(x, y);
        Player player = game.getCurrentPlayer();

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
        Player player = game.getCurrentPlayer();

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
        Tile[][] map = game.getMap(); // فرض بر این است که map حالا از نوع Tile[][] است
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
                    builder.append(" "); // خارج از محدوده
                } else {
                    builder.append(map[i][j].getType().getColorCode()).append(map[i][j].getType().getSymbol()).append("\u001B[0m");
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



}
