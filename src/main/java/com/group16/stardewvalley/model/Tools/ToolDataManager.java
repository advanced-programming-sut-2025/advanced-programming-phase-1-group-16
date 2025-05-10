package com.group16.stardewvalley.model.Tools;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ToolDataManager {
    private static final String TOOLS_JSON_PATH = "src/main/resources/Tools/Tools.json";
    private static Map<String, Object> toolsData;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = ToolDataManager.class.getResourceAsStream(TOOLS_JSON_PATH);
            if (inputStream == null) {
                throw new RuntimeException("File not found in classpath: " + TOOLS_JSON_PATH);
            }
            toolsData = mapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to load tools data from JSON", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static int getEnergyConsumption(String toolName, String material) {
        try {
            Map<String, Object> tools = (Map<String, Object>) toolsData.get("tools");
            Map<String, Object> toolMap = (Map<String, Object>) tools.get(toolName.toLowerCase());

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