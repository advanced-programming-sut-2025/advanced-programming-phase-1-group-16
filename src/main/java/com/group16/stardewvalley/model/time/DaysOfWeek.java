package com.group16.stardewvalley.model.time;

public enum DaysOfWeek {
    Saturday(0, "Saturday"),
    Sunday(1, "Sunday"),
    Monday(2, "Monday"),
    Tuesday(3, "Tuesday"),
    Wednesday(4, "Wednesday"),
    Thursday(5, "Thursday"),
    Friday(6, "Friday");

    private int index;
    private String name;

    DaysOfWeek(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
