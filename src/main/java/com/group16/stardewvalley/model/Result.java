package com.group16.stardewvalley.model;

public class Result {

    private final boolean success;
    private final String mesage;

    public Result(boolean success, String mesage) {
        this.success = success;
        this.mesage = mesage;
    }

    @Override
    public String toString() {
        return mesage;
    }

    public boolean isSuccess() {
        return success;
    }
}
