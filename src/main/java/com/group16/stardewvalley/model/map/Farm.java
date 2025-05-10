package com.group16.stardewvalley.model.map;


public class Farm {
    private FarmType type;
    private Pos startPosition;

    public Farm(FarmType type) {
        this.type = type;
    }

    public FarmType getType() {
        return type;
    }

    public void setType(FarmType type) {
        this.type = type;
    }

    public Pos getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Pos startPosition) {
        this.startPosition = startPosition;
    }
}