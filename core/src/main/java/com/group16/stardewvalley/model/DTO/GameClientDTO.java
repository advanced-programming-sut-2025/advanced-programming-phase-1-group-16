// GameClientDTO.java
package com.group16.stardewvalley.model.DTO;

import com.group16.stardewvalley.model.time.Season;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.weather.WeatherCondition;

import java.util.List;

public class GameClientDTO {
    private List<PlayerDTO> players;
    private String creatorUsername;
    private TileDTO[][] map;

    public TileDTO[][] getMap() {
        return map;
    }

    public void setMap(TileDTO[][] map) {
        this.map = map;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }
}
