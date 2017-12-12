package com.bnelson.triton.domain.model;

public enum GameCommandType {
    START("Start", true),
    STOP("Stop", true),
    KILL("Kill", true),
    IS_RUNNING("Is Running", false),// this can be used to verify whether the server is running
    CUSTOM("Custom", true);

    private final String displayName;
    private final boolean shouldShowButton;

    GameCommandType(String displayName, boolean shouldShowButton) {
        this.displayName = displayName;
        this.shouldShowButton = shouldShowButton;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isShouldShowButton() {
        return shouldShowButton;
    }
}
