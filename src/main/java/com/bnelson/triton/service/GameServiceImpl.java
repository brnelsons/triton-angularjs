package com.bnelson.triton.service;

import com.bnelson.triton.api.model.GameMetadata;
import com.bnelson.triton.domain.data.GameRepositoryImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameServiceImpl implements GameService{

    private final GameRepositoryImpl gameRepositoryImpl;

    @Autowired
    public GameServiceImpl(GameRepositoryImpl gameRepositoryImpl) {
        this.gameRepositoryImpl = gameRepositoryImpl;
    }

    @Override
    public boolean create(GameMetadata gameMetadata) {
        Preconditions.checkNotNull(gameMetadata);
        Preconditions.checkNotNull(gameMetadata.getGameName());
        Preconditions.checkNotNull(gameMetadata.getServerName());
        return gameRepositoryImpl.create(GameService.convert(gameMetadata));
    }

    @Override
    public boolean update(GameMetadata gameMetadata) {
        return gameRepositoryImpl.update(GameService.convert(gameMetadata));
    }

    @Override
    public boolean delete(GameMetadata gameMetadata) {
        return gameRepositoryImpl.delete(GameService.convert(gameMetadata));
    }

    @Override
    public GameMetadata getOne(String gameName, String serverName) {
        return GameService.convert(gameRepositoryImpl.getOne(gameName, serverName));
    }

    @Override
    public List<GameMetadata> getAll() {
        return gameRepositoryImpl.getAll()
                .stream()
                .map(GameService::convert)
                .collect(Collectors.toList());
    }
}
