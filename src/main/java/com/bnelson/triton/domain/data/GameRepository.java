package com.bnelson.triton.domain.data;

import com.bnelson.triton.domain.model.GameModel;

import java.util.List;

public interface GameRepository {
    List<GameModel> getAll();

    boolean create(GameModel gameModel);

    boolean update(GameModel gameModel);

    GameModel getOne(String gameName, String serverName);

    boolean delete(GameModel gameModel);
}
