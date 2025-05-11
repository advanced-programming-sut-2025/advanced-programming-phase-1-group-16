package com.group16.stardewvalley.controller.agriculture;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.*;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileType;


public class AgricultureController {
    public Result craftInfo(String name) {
        CropType crop = findCropTypeByName(name);
        if (crop == null) {
            return new Result(false, "Crop not found");
        }
        StringBuilder result = new StringBuilder();
        result.append("Name: ").append(crop.getName())
                .append("\nSource:").append(crop.getSource().getName())
                .append("\nStages:");
        for (int stage : crop.getStages()) {
            result.append(stage).append("-");
        }
        result.deleteCharAt(result.length() - 1);
        result.append("\nTotal Harvest Time: ").append(crop.getHarvestTime())
                .append("\nOne Time: ").append(crop.isOneTime())
                .append("\nRegrowth Time: ").append(crop.getRegrowthTime())
                .append("\nBase Sell Price: ").append(crop.getBaseSellPrice())
                .append("\nIs Edible: ").append(crop.isEdible())
                .append("\nBase Energy: ").append(crop.getEnergy())
                .append("\nBase Health: ").append(crop.getBaseHealth())
                .append("\nSeason: ").append(crop.getSeason())
                .append("\nCan Become Giant: ").append(crop.canBecomeGiant());
        return new Result(true, result.toString());
    }

    public Result planting(String seedName, String direction) {
        SeedType seedType = findSeedTypeByName(seedName);
        if (seedType == null) {
            return new Result(false, "Seed not found");
        }
        int dirX , dirY;
        switch (direction) {
            case "down":
                dirY = -1;
                dirX = 0;
                break;
            case "up":
                dirY = 1;
                dirX = 0;
                break;
            case "left":
                dirX = -1;
                dirY = 0;
                break;
            case "right":
                dirX = 1;
                dirY = 0;
                break;
            case "up left":
                dirX = -1;
                dirY = 1;
                break;
            case "up right":
                dirX = 1;
                dirY = 1;
                break;
            case "down left":
                dirX = -1;
                dirY = -1;
                break;
            case "down right":
                dirX = 1;
                dirY = -1;
                break;
            default:
                return new Result(false, "Invalid direction");
        }
        Pos playerPos = App.getActiveGame().getCurrentPlayer().getPosition();
        if (playerPos.getX() + dirX < 0 || playerPos.getY() + dirY < 0 || playerPos.getX() + dirX > App.getActiveGame().getMapWidth() || playerPos.getY() + dirY > App.getActiveGame().getMapHeight()) {
            return new Result(false, "Invalid direction");
        }
        Tile targetTile = App.getActiveGame().getMap()[playerPos.getY() + dirY][playerPos.getX() + dirX];
        if (!targetTile.getType().equals(TileType.Plowed)) {
            return new Result(false, "Shokhm nazadi dadash!");
        }
        switch (seedType.getType()) {
            case "TREE":
                TreeType treeType = findTreeTypeBySeed(seedType);
                if (treeType == null) {
                    return new Result(false, "Tree type not found");
                }
                Tree tree = new Tree(treeType);
                targetTile.setTree(tree);
                break;
            case "CROP":
                CropType cropType = findCropTypeBySeed(seedType);
                if (cropType == null) {
                    return new Result(false, "Crop type not found");
                }
                Crop crop = new Crop(cropType);
                targetTile.setCrop(crop);
                break;
            default:
                return new Result(false, "Invalid seed");
                break;
        }

    }

    private CropType findCropTypeBySeed(SeedType seedType) {
        for (CropType crop : CropType.values()) {
            if (crop.getSource().equals(seedType)) {
                return crop;
            }
        }
        return null;
    }

    private TreeType findTreeTypeBySeed(SeedType seedType) {
        for (TreeType tree : TreeType.values()) {
            if (tree.getSeed().equals(seedType)) {
                return tree;
            }
        }
        return null;
    }

    private CropType findCropTypeByName(String name) {
        for (CropType cropType : CropType.values()) {
            if (cropType.getName().equals(name)) {
                return cropType;
            }
        }
        return null;
    }

    private SeedType findSeedTypeByName(String name) {
        for (SeedType seedType : SeedType.values()) {
            if (seedType.getName().equals(name)) {
                return seedType;
            }
        }
        return null;
    }
}
