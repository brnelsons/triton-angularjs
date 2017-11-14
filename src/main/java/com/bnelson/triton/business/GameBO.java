package com.bnelson.triton.business;

import com.bnelson.triton.dao.GameDAO;
import com.bnelson.triton.pojo.Game;
import com.bnelson.triton.pojo.GameCommand;
import com.bnelson.triton.pojo.GameMetaData;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Component
public class GameBO {

    private final GameDAO gameDAO;

    @Autowired
    public GameBO(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public boolean createGame(Game game){
        Preconditions.checkNotNull(game);
        Preconditions.checkNotNull(game.getGameName());
        Preconditions.checkNotNull(game.getServerName());
        return gameDAO.createGame(game);
    }

    public List<Game> getAllGames(){
        return gameDAO.getAllGames();
    }

    public Game getGame(String gameName, String serverName) {
        return gameDAO.getGameByName(gameName, serverName);
    }

    public boolean update(Game game) {
        return gameDAO.update(game);
    }
}
