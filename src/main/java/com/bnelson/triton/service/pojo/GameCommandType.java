package com.bnelson.triton.service.pojo;

public enum GameCommandType {
    START("Start"),
    STOP("Stop"),
    KILL("Kill"),
    CUSTOM("Custom");

    private final String displayName;

    GameCommandType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
