// PlayerDTO.java
package com.group16.stardewvalley.model.DTO;

import com.group16.stardewvalley.model.map.Location;

public class PlayerDTO {
    private String username;
    private String heroName; // از user یا hero
    private int x;
    private int y;
    private String characterPath;


    public PlayerDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCharacterPath() {
        return characterPath;
    }

    public void setCharacterPath(String characterPath) {
        this.characterPath = characterPath;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }
}
