package com.bnelson.triton.api.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class GameLinkMetadata extends LinkMetadata{
    private static final String GAME_NAME_PLACEHOLDER = "{gameName}";
    private static final String SERVER_NAME_PLACEHOLDER = "{serverName}";

    public static final String GAME_NAME = "gameName";
    public static final String SERVER_NAME = "serverName";
    public static final String CONFIG = "/"+GAME_NAME_PLACEHOLDER+"/"+SERVER_NAME_PLACEHOLDER+"/config";
    public static final String LOG = "/"+GAME_NAME_PLACEHOLDER+"/"+SERVER_NAME_PLACEHOLDER+"/log";

    private final String config;
    private final String log;

    public GameLinkMetadata(String gameName, String serverName){
        this.config = replace(CONFIG, gameName, serverName);
        this.log = replace(LOG, gameName, serverName);
    }

    public String getConfig() {
        return config;
    }

    public String getLog() {
        return log;
    }

    private static String replace(String baseLink, String gameName, String serverName){
        return baseLink.replace(GAME_NAME_PLACEHOLDER, gameName).replace(SERVER_NAME_PLACEHOLDER, serverName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameLinkMetadata that = (GameLinkMetadata) o;
        return Objects.equal(config, that.config) &&
                Objects.equal(log, that.log);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(config, log);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("config", config)
                .add("log", log)
                .toString();
    }
}
