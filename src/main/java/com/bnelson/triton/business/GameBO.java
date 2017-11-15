package com.bnelson.triton.business;

import com.bnelson.triton.dao.CommandRunner;
import com.bnelson.triton.dao.GameDAO;
import com.bnelson.triton.pojo.Game;
import com.bnelson.triton.pojo.GameCommand;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameBO {

    private final GameDAO gameDAO;
    private final CommandRunner commandRunner;

    @Autowired
    public GameBO(GameDAO gameDAO, CommandRunner commandRunner) {
        this.gameDAO = gameDAO;
        this.commandRunner = commandRunner;
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

    public List<String> getAllRunningCommands(){
        return commandRunner.getAllRunningJobs();
    }

    public boolean runCommand(String gameName, String serverName, String commandName) {
        Game game = gameDAO.getGameByName(gameName, serverName);
        for (GameCommand gameCommand : game.getCommands()) {
            if(commandName.equals(gameCommand.getName())) {
                return commandRunner.addJob(game, gameCommand);
            }
        }
        return false;
    }
}
