package com.bnelson.triton.domain.data;

import com.bnelson.triton.service.pojo.Game;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GameDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameDAO.class);
    private static final String BASE_PATH = "C:/Users/brnel/Documents/";//TODO inject this directory

    private List<String> getAllGameFiles() {
        //noinspection ConstantConditions
        String[] list = new File(BASE_PATH).list((dir, name) -> name.endsWith(".yml"));
        if (list == null) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(list);
    }

    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        for (String gameName : getAllGameFiles()) {
            games.add(getGameByFileName(gameName));
        }
        return games;
    }

    private Game getGameByFileName(String filename) {
        String fullPath = BASE_PATH + filename;
        try {
            return DaoUtil.YAML_FILE_MAPPER.readValue(new File(fullPath), Game.class);
        } catch (IOException e) {
            LOGGER.error("Was unable to parse file at {} into 'Game.class'", fullPath, e);
        }
        return null;
    }

    public boolean createGame(Game game) {
        return saveGame(game, false);
    }

    private boolean saveGame(Game game, boolean override) {
        String fileName = getAbsoluteFilePath(game.getGameName(), game.getServerName());
        if (!override && new File(fileName).exists()) {
            LOGGER.error("File {} already exists!", fileName);
            return false;
        }
        try {
            DaoUtil.YAML_FILE_MAPPER.writeValue(new FileWriter(fileName), game);
            return true;
        } catch (IOException e) {
            LOGGER.error("Was unable to write ", fileName, e);
        }
        return false;
    }

    private String getFileName(String gameName, String serverName) {
        return (gameName + "_" + serverName + ".yml").replace(" ", "_");
    }

    private String getAbsoluteFilePath(String gameName, String serverName) {
        return BASE_PATH + getFileName(gameName, serverName);
    }

    public Game getGameByName(String gameName, String serverName) {
        String fileName = getFileName(gameName, serverName);
        for (String s : getAllGameFiles()) {
            if (s.equals(fileName)) {
                return getGameByFileName(s);
            }
        }
        return null;
    }

    public boolean update(Game game) {
        return saveGame(game, true);
    }

    public boolean delete(Game game) {
        String path = getAbsoluteFilePath(game.getGameName(), game.getServerName());
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        LOGGER.error("Could not delete file {}", path);
        return false;
    }

    public static void main(String[] args) {
        GameDAO dao = new GameDAO();
        Game game = new Game();
        game.setGameName("7 Days to die");
        game.setServerName("Server 1");
        dao.saveGame(game, true);
    }
}