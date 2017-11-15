package com.bnelson.triton.pojo;

import java.util.List;

public class Game {
    private String gameName;
    private String serverName;
    private String imageUrl;
    private String serverLink;
    private String serverIpAddress;
    private List<GameCommand> commands;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getServerLink() {
        return serverLink;
    }

    public void setServerLink(String serverUrl) {
        this.serverLink = serverUrl;
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }

    public Game setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
        return this;
    }

    public List<GameCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<GameCommand> commands) {
        this.commands = commands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (gameName != null ? !gameName.equals(game.gameName) : game.gameName != null) return false;
        if (serverName != null ? !serverName.equals(game.serverName) : game.serverName != null) return false;
        if (imageUrl != null ? !imageUrl.equals(game.imageUrl) : game.imageUrl != null) return false;
        if (serverLink != null ? !serverLink.equals(game.serverLink) : game.serverLink != null) return false;
        if (serverIpAddress != null ? !serverIpAddress.equals(game.serverIpAddress) : game.serverIpAddress != null)
            return false;
        return commands != null ? commands.equals(game.commands) : game.commands == null;
    }

    @Override
    public int hashCode() {
        int result = gameName != null ? gameName.hashCode() : 0;
        result = 31 * result + (serverName != null ? serverName.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (serverLink != null ? serverLink.hashCode() : 0);
        result = 31 * result + (serverIpAddress != null ? serverIpAddress.hashCode() : 0);
        result = 31 * result + (commands != null ? commands.hashCode() : 0);
        return result;
    }
}
