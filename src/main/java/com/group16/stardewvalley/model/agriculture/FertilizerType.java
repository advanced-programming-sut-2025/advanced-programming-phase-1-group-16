package com.group16.stardewvalley.model.agriculture;

public enum FertilizerType {
    NONE(false, 0), // بدون کود
    SPEED_GRO(true, -1), // یک روز رشد کمتر
    DELUXE_RETAINING_SOIL(false, 0); // آبیاری لازم نیست

    private final boolean affectsGrowth;
    private final int growthModifierInDays; // عدد منفی یعنی رشد سریع‌تر

    FertilizerType(boolean affectsGrowth, int growthModifierInDays) {
        this.affectsGrowth = affectsGrowth;
        this.growthModifierInDays = growthModifierInDays;
    }

    public boolean affectsGrowth() {
        return affectsGrowth;
    }

    public int getGrowthModifierInDays() {
        return growthModifierInDays;
    }

    public boolean skipsWatering() {
        return this == DELUXE_RETAINING_SOIL;
    }
}

