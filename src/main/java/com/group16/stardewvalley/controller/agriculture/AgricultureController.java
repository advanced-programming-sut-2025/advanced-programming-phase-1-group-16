package com.group16.stardewvalley.controller.agriculture;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.CropType;


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

    private CropType findCropTypeByName(String name) {
        for (CropType cropType : CropType.values()) {
            if (cropType.getName().equals(name)) {
                return cropType;
            }
        }
        return null;
    }
}
