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
    private static final String basePath = "/home/bnelson/Documents/";//TODO inject this directory

    public List<String> getAll(){
        //noinspection ConstantConditions
        return Lists.newArrayList(new File(basePath).list((dir, name) -> name.endsWith(".yml")));
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

    public void saveGame(Game game){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        String fileName = basePath + game.getGameName() + "_" + game.getServerName() + ".yml";
        try{
            mapper.writeValue(new FileWriter(fileName), game);
        } catch (IOException e) {
            LOGGER.error("Was unable to write ", fileName, e);
        }
    }

    public static void main(String[] args) {
        GameDAO dao = new GameDAO();
        System.out.println(dao.getAll());
    }

}
