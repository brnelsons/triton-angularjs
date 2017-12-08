package com.bnelson.triton.api.model;

import com.bnelson.triton.domain.model.GameCommand;
import com.google.common.base.Objects;

import java.util.List;

public class GameMetadata{

    private String gameName;
    private String serverName;
    private String imageUrl;
    private String serverLink;
    private String serverIpAddress;
    private List<GameCommand> commands;

    public GameMetadata() { }

    private GameMetadata(Builder builder) {
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

    public String getServerName() {
        return serverName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getServerLink() {
        return serverLink;
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }

    public List<GameCommand> getCommands() {
        return commands;
    }

    public static class Builder {
        private final String gameName;
        private final String serverName;
        private String imageUrl;
        private String serverLink;
        private String serverIpAddress;
        private List<GameCommand> commands;

        public Builder(String gameName,
                       String serverName) {
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

        public GameMetadata build() {
            return new GameMetadata(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameMetadata gameMetadata = (GameMetadata) o;
        return Objects.equal(gameName, gameMetadata.gameName) &&
                Objects.equal(serverName, gameMetadata.serverName) &&
                Objects.equal(imageUrl, gameMetadata.imageUrl) &&
                Objects.equal(serverLink, gameMetadata.serverLink) &&
                Objects.equal(serverIpAddress, gameMetadata.serverIpAddress) &&
                Objects.equal(commands, gameMetadata.commands);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gameName, serverName, imageUrl, serverLink, serverIpAddress, commands);
    }

    @Override
    public String toString() {
        return "GameMetadata{" +
                "gameName='" + gameName + '\'' +
                ", serverName='" + serverName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", serverLink='" + serverLink + '\'' +
                ", serverIpAddress='" + serverIpAddress + '\'' +
                ", commands=" + commands +
                '}';
    }
}
