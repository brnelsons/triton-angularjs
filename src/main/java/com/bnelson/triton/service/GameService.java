package com.bnelson.triton.service;

import com.bnelson.triton.api.model.GameMetadata;
import com.bnelson.triton.domain.model.GameModel;

import java.util.List;

public interface GameService {

    boolean create(GameMetadata gameMetadata);

    List<GameMetadata> getAll();

    GameMetadata getOne(String gameName, String serverName);

    boolean update(GameMetadata gameMetadata);

    boolean delete(GameMetadata gameMetadata);

    static GameModel convert(GameMetadata gameMetadata) {
        return new GameModel.Builder(gameMetadata.getGameName(), gameMetadata.getServerName())
                .setImageUrl(gameMetadata.getImageUrl())
                .setServerIpAddress(gameMetadata.getServerIpAddress())
                .setServerLink(gameMetadata.getServerLink())
                .setCommands(gameMetadata.getCommands())
                .build();
    }

    static GameMetadata convert(GameModel model) {
        return new GameMetadata.Builder(model.getGameName(), model.getServerName())
                .setImageUrl(model.getImageUrl())
                .setServerIpAddress(model.getServerIpAddress())
                .setServerLink(model.getServerLink())
                .setCommands(model.getCommands())
                .build();
    }
}
