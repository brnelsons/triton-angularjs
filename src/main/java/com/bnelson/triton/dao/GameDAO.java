package com.bnelson.triton.dao;

import com.bnelson.triton.pojo.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class GameDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameDAO.class);
    private static final String basePath = "C:/Users/brnel/Documents/";//TODO inject this directory

    public List<String> getAll(){
        //noinspection ConstantConditions
        String[] list = new File(basePath).list((dir, name) -> name.endsWith(".yml"));
        if(list == null){
            return Lists.newArrayList();
        }
        return Lists.newArrayList(list);
    }

    public Game getGameByFileName(String filename) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        String fullPath = basePath + filename;
        try {
            return objectMapper.readValue(new File(fullPath), Game.class);
        } catch (IOException e) {
            LOGGER.error("Was unable to parse file at {} into 'Game.class'", fullPath, e);
        }
        return null;
    }

    public boolean saveGame(Game game){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        String fileName = basePath + game.getGameName() + "_" + game.getServerName() + ".yml";
        if(new File(fileName).exists()){
            LOGGER.error("File {} already exists!", fileName);
            return false;
        }
        try{
            mapper.writeValue(new FileWriter(fileName), game);
            return true;
        } catch (IOException e) {
            LOGGER.error("Was unable to write ", fileName, e);
        }
        return false;
    }

    public static void main(String[] args) {
        GameDAO dao = new GameDAO();
        Game game = new Game();
        game.setGameName("7 Days to die");
        game.setServerName("Server 1");
        dao.saveGame(game);
    }

}
