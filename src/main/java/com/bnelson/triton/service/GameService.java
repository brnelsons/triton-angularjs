package com.bnelson.triton.service;

import com.bnelson.triton.api.model.UniqueCommandRpc;
import com.bnelson.triton.domain.data.GameDAO;
import com.bnelson.triton.domain.script.UniqueCommand;
import com.bnelson.triton.service.pojo.Game;
import com.bnelson.triton.service.pojo.GameCommand;
import com.bnelson.triton.domain.script.ScriptJobManager;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GameService {

    private final GameDAO gameDAO;
    private final ScriptJobManager scriptJobManager;

    @Autowired
    public GameService(GameDAO gameDAO, ScriptJobManager scriptJobManager) {
        this.gameDAO = gameDAO;
        this.scriptJobManager = scriptJobManager;
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

    public boolean delete(Game game) {
        return gameDAO.delete(game);
    }

    public Collection<UniqueCommandRpc> getAllRunningCommands(Game game){
        return scriptJobManager.getAllJobsForGame(game)
                .stream()
                .map(uniqueCommand -> new UniqueCommandRpc(
                        uniqueCommand.getCommandTime(),
                        uniqueCommand.getCommand(),
                        uniqueCommand.getOutputDelegate().read()
                ))
                .collect(Collectors.toList());
    }

    public boolean runCommand(String gameName, String serverName, String commandName) {
        Game game = gameDAO.getGameByName(gameName, serverName);
        for (GameCommand gameCommand : game.getCommands()) {
            if(commandName.equals(gameCommand.getName())) {
                return scriptJobManager.runScript(game, gameCommand);
            }
        }
        return false;
    }
}
