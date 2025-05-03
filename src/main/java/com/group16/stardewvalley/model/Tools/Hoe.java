package com.group16.stardewvalley.model.Tools;

import java.util.Map;

public class Hoe extends Gadget {

    public Hoe(String nam, String material) {
        this.name = nam;
        this.material = material;
    }


    public int getPrice() {
        try {
            // دسترسی به داده‌های ابزارها از ToolDataManager
            Map<String, Object> toolsMap = ToolDataManager.getToolsData();
            Map<String, Object> hoesMap = (Map<String, Object>) toolsMap.get("tools").get("hoes");

            // بررسی وجود قیمت مستقیم برای hoe
            if (hoesMap.containsKey("price")) {
                return (int) hoesMap.get("price");
            }

            // اگر قیمت وابسته به متریال است
            if (hoesMap.containsKey("material")) {
                Map<String, Object> materials = (Map<String, Object>) hoesMap.get("material");
                Map<String, Object> materialData = (Map<String, Object>) materials.get(this.material.toLowerCase());

                if (materialData != null && materialData.containsKey("price")) {
                    return (int) materialData.get("price");
                }
            }

            return 0; // قیمت پیش‌فرض اگر پیدا نشد
        } catch (Exception e) {
            throw new RuntimeException("Error getting price for hoe with material " + this.material, e);
        }

    public int getEnoughLevel() {

    }

    public void getConsumptionEnergy() {

    }


}
