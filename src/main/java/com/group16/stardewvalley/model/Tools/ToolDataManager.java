package com.group16.stardewvalley.model.Tools;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ToolDataManager {
    private static final String TOOLS_JSON_PATH = "src/main/resources/tools/Tools.json";
    private static Map<String, Object> toolsData;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            toolsData = mapper.readValue(new File(TOOLS_JSON_PATH), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load tools data from JSON", e);
        }
    }

    public static int getEnergyConsumption(String toolName, String material) {
        try {
            Map<String, Object> toolMap = (Map<String, Object>) ((Map<String, Object>) toolsData.get("tools"))
                    .get(toolName.toLowerCase());

            if (toolMap == null) {
                throw new IllegalArgumentException("Tool not found: " + toolName);
            }

            // برای ابزارهایی مثل scythe که مصرف ثابت دارند
            if (toolMap.containsKey("consumption energy")) {
                return (int) toolMap.get("consumption energy");
            }

            // برای ابزارهای وابسته به متریال
            if (toolMap.containsKey("material")) {
                Map<String, Object> materials = (Map<String, Object>) toolMap.get("material");
                Map<String, Object> materialData = (Map<String, Object>) materials.get(material.toLowerCase());
                return (int) materialData.get("consumption energy");
            }

            return 0;
        } catch (Exception e) {
            throw new RuntimeException("Error getting energy consumption for " + toolName, e);
        }
    }

    public static Map<String, Object> getToolsData() {
        return toolsData;
    }
}