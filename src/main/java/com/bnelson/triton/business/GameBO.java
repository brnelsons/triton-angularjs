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

    public boolean addGame(Game game){
        Preconditions.checkNotNull(game);
        Preconditions.checkNotNull(game.getGameName());
        Preconditions.checkNotNull(game.getServerName());
        return gameDAO.saveGame(game);
    }

    public List<GameMetaData> getAllGameMetaData() {
        List<GameMetaData> metaData = new ArrayList<>();
        for (String s : gameDAO.getAll()) {
            Game game = gameDAO.getGameByFileName(s);
            if (game != null) {
                metaData.add(convertToMetaData(game));
            }
        }
        return metaData;
    }

    private GameMetaData convertToMetaData(@Nonnull Game game) {
        GameMetaData gameMetaData = new GameMetaData();
        gameMetaData.setName(game.getGameName());
        if(game.getCommands() != null) {
            gameMetaData.setCommands(convertCommands(game.getCommands()));
        }
        gameMetaData.setImageUrl(game.getImageUrl());
        return gameMetaData;
    }

    private List<String> convertCommands(List<GameCommand> commands) {
        return Lists.transform(commands, new Function<GameCommand, String>() {
            @Nullable
            @Override
            public String apply(@Nullable GameCommand input) {
                if (input == null) {
                    return null;
                }
                return input.getName();
            }
        });
    }

}
