package com.group16.stardewvalley.model.Tools;

import java.util.Map;

public class Hoe extends Gadget {

    public Hoe(String name, String material) {
        this.name = name;
        this.material = material;
    }

    public int getPrice() {
        try {
            Map<String, Object> toolsMap = ToolDataManager.getToolsData();

            // Get tools object
            Map<?, ?> tools = (Map<?, ?>) toolsMap.get("tools");
            if (tools == null) {
                throw new RuntimeException("'tools' section not found in data");
            }

            // Get hoes object
            Map<?, ?> hoes = (Map<?, ?>) tools.get("hoes");
            if (hoes == null) {
                throw new RuntimeException("'hoes' section not found in tools data");
            }

            // Get material data
            Map<?, ?> materials = (Map<?, ?>) hoes.get("material");
            if (materials == null) {
                throw new RuntimeException("'material' section not found in hoes data");
            }

            // Get specific material data (case insensitive)
            String materialKey = this.material.toLowerCase();
            Map<?, ?> materialData = (Map<?, ?>) materials.get(materialKey);
            if (materialData == null) {
                // Fallback to base if specific material not found
                materialData = (Map<?, ?>) materials.get("base");
            }

            // Get cost (note the uppercase 'C' in "Cost")
            if (materialData != null && materialData.containsKey("Cost")) {
                return ((Number) materialData.get("Cost")).intValue();
            }

            return 0; // Default price if not found
        } catch (Exception e) {
            throw new RuntimeException("Error getting price for hoe with material " + this.material, e);
        }
    }

    public int getEnoughLevel() {
        // In your JSON, enoughLevel is only defined for fishing pole
        // You might want to add it for hoes too or return a default value
        return 0; // Default level
    }

    public int getConsumptionEnergy() {
        try {
            Map<String, Object> toolsMap = ToolDataManager.getToolsData();

            Map<?, ?> tools = (Map<?, ?>) toolsMap.get("tools");
            Map<?, ?> hoes = (Map<?, ?>) tools.get("hoes");
            Map<?, ?> materials = (Map<?, ?>) hoes.get("material");

            String materialKey = this.material.toLowerCase();
            Map<?, ?> materialData = (Map<?, ?>) materials.get(materialKey);
            if (materialData == null) {
                materialData = (Map<?, ?>) materials.get("base");
            }

            if (materialData != null && materialData.containsKey("consumption energy")) {
                return ((Number) materialData.get("consumption energy")).intValue();
            }

            return 5; // Default energy consumption
        } catch (Exception e) {
            throw new RuntimeException("Error getting energy consumption for hoe with material " + this.material, e);
        }
    }
}