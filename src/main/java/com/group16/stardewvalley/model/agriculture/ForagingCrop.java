package com.group16.stardewvalley.model.agriculture;

import com.group16.stardewvalley.model.items.Item;

public class ForagingCrop extends Item {
    private final CropType cropType;
    public ForagingCrop(String name, CropType cropType) {
        super(name);
        this.cropType = cropType;
    }

    public CropType getCropType() {
        return cropType;
    }
}