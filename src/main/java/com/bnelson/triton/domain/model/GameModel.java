package com.bnelson.triton.domain.model;

import com.bnelson.triton.common.model.GameCommand;

import java.util.List;

public class GameModel {
    private String gameName;
    private String serverName;
    private String imageUrl;
    private String serverLink;
    private String serverIpAddress;
    private List<GameCommand> commands;

    public GameModel() {
    }

    public GameModel(Builder builder) {
        this.gameName = builder.gameName;
        this.serverName = builder.serverName;
        this.imageUrl = builder.imageUrl;
        this.serverLink = builder.serverLink;
        this.serverIpAddress = builder.serverIpAddress;
        this.commands = builder.commands;
    }

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

    public void setServerLink(String serverLink) {
        this.serverLink = serverLink;
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }

    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
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

        GameModel gameModel = (GameModel) o;

        if (gameName != null ? !gameName.equals(gameModel.gameName) : gameModel.gameName != null) return false;
        if (serverName != null ? !serverName.equals(gameModel.serverName) : gameModel.serverName != null) return false;
        if (imageUrl != null ? !imageUrl.equals(gameModel.imageUrl) : gameModel.imageUrl != null) return false;
        if (serverLink != null ? !serverLink.equals(gameModel.serverLink) : gameModel.serverLink != null) return false;
        if (serverIpAddress != null ? !serverIpAddress.equals(gameModel.serverIpAddress) : gameModel.serverIpAddress != null)
            return false;
        return commands != null ? commands.equals(gameModel.commands) : gameModel.commands == null;
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

    public static class Builder{
        private final String gameName;
        private final String serverName;
        private String imageUrl;
        private String serverLink;
        private String serverIpAddress;
        private List<GameCommand> commands;

        public Builder(String gameName,
                       String serverName){
            this.gameName = gameName;
            this.serverName = serverName;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setServerLink(String serverLink) {
            this.serverLink = serverLink;
            return this;
        }

        public Builder setServerIpAddress(String serverIpAddress) {
            this.serverIpAddress = serverIpAddress;
            return this;
        }

        public Builder setCommands(List<GameCommand> commands) {
            this.commands = commands;
            return this;
        }

        public GameModel build(){
            return new GameModel(this);
        }
    }
}
