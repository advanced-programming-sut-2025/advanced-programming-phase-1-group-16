// PlayerDTO.java
package com.group16.stardewvalley.model.DTO;

public class PlayerDTO {
    private String username;
    private String heroName; // از user یا hero
    private int x;
    private int y;
    private String characterPath;
    private int farmX;
    private int farmY;
    private String farmType;


    public PlayerDTO() {
    }

    public String getFarmType() {
        return farmType;
    }

    public void setFarmType(String farmType) {
        this.farmType = farmType;
    }

    public int getFarmX() {
        return farmX;
    }

    public void setFarmX(int farmX) {
        this.farmX = farmX;
    }

    public int getFarmY() {
        return farmY;
    }

    public void setFarmY(int farmY) {
        this.farmY = farmY;
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
