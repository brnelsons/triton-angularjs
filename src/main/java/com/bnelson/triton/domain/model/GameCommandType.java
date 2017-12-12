package com.bnelson.triton.domain.model;

public enum GameCommandType {
    START("Start"),
    STOP("Stop"),
    KILL("Kill"),
    IS_RUNNING("Is Running"),// this can be used to verify whether the server is running
    CUSTOM("Custom");

    private final String displayName;

    GameCommandType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
