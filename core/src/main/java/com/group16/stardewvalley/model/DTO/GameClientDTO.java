// GameClientDTO.java
package com.group16.stardewvalley.model.DTO;

import com.group16.stardewvalley.model.time.Season;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.weather.WeatherCondition;

import java.util.List;

public class GameClientDTO {
    private List<PlayerDTO> players;
    private int currentPlayerIndex;
    private TimeDate timeDate;
    private WeatherCondition weatherCondition;
    private WeatherCondition tomorrowWeatherCondition;
    private Season season;
    private List<NPCDTO> npcs;

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public TimeDate getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(TimeDate timeDate) {
        this.timeDate = timeDate;
    }

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(WeatherCondition weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public WeatherCondition getTomorrowWeatherCondition() {
        return tomorrowWeatherCondition;
    }

    public void setTomorrowWeatherCondition(WeatherCondition tomorrowWeatherCondition) {
        this.tomorrowWeatherCondition = tomorrowWeatherCondition;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public List<NPCDTO> getNpcs() {
        return npcs;
    }

    public void setNpcs(List<NPCDTO> npcs) {
        this.npcs = npcs;
    }
}
