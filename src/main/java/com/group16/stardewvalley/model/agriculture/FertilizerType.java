package com.group16.stardewvalley.model.agriculture;

public enum FertilizerType {
    BASIC_FERTILIZER("Basic Fertilizer", "Increases soil quality, improving chance to grow quality crops.", 0.0, 0.2, false),
    QUALITY_FERTILIZER("Quality Fertilizer", "Greatly improves soil quality, increasing chance to grow higher-quality crops.", 0.0, 0.4, false),
    DELUXE_FERTILIZER("Deluxe Fertilizer", "Best fertilizer for quality crops.", 0.0, 0.6, false),

    SPEED_GRO("Speed-Gro", "Stimulates leaf production, increasing growth rate by at least 10%.", 0.1, 0.0, false),
    DELUXE_SPEED_GRO("Deluxe Speed-Gro", "Stimulates leaf production, increasing growth rate by at least 25%.", 0.25, 0.0, false),
    HYPER_SPEED_GRO("Hyper Speed-Gro", "Greatly stimulates growth, increasing rate by at least 33%.", 0.33, 0.0, false),

    GIANT_FERTILIZER("Giant Fertilizer", "Increases chance of growing giant crops.", 0.0, 0.0, true);

    private final String displayName;
    private final String description;
    private final double growthBoost; // درصد افزایش سرعت رشد
    private final double qualityBoost; // درصد افزایش کیفیت
    private final boolean boostsGiantChance;

    FertilizerType(String displayName, String description, double growthBoost, double qualityBoost, boolean boostsGiantChance) {
        this.displayName = displayName;
        this.description = description;
        this.growthBoost = growthBoost;
        this.qualityBoost = qualityBoost;
        this.boostsGiantChance = boostsGiantChance;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public double getGrowthBoost() {
        return growthBoost;
    }

    public double getQualityBoost() {
        return qualityBoost;
    }

    public boolean boostsGiantChance() {
        return boostsGiantChance;
    }
}
