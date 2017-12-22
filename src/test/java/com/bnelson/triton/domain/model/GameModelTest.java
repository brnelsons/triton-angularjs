package com.bnelson.triton.domain.model;

import com.bnelson.triton.common.model.GameCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Lists;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameModelTest {

    private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    @Test
    public void serialize() throws JsonProcessingException {
        GameCommand startCommand = new GameCommand();
        startCommand.setName("Start Server");
        startCommand.setType(GameCommandType.START);
        startCommand.setExe("echo \"hello world\"");
        GameModel model = new GameModel.Builder("7 Days to Die", "Private 1")
                .setImageUrl("http://cdn.edgecast.steamstatic.com/steam/apps/251570/header.jpg?t=1502044027")
                .setServerLink("localhost:8081")
                .setServerIpAddress(null)
                .setCommands(Lists.newArrayList(
                        startCommand
                ))
                .build();
        String yaml = MAPPER.writeValueAsString(model);
        assertEquals(
                "---\n" +
                        "gameName: \"7 Days to Die\"\n" +
                        "serverName: \"Private 1\"\n" +
                        "imageUrl: \"http://cdn.edgecast.steamstatic.com/steam/apps/251570/header.jpg?t=1502044027\"\n" +
                        "serverLink: \"localhost:8081\"\n" +
                        "serverIpAddress: null\n" +
                        "commands:\n" +
                        "- name: \"Start Server\"\n" +
                        "  type: \"START\"\n" +
                        "  exe: \"echo \\\"hello world\\\"\"\n" +
                        "  resultComparatorType: null\n" +
                        "  expectedResult: null\n" +
                        "  shouldShowButton: true\n",
                yaml);
    }

    @Test
    public void serialize2() throws JsonProcessingException {
        GameModel model = new GameModel.Builder("7 Days to Die", "Private 1")
                .setImageUrl("http://cdn.edgecast.steamstatic.com/steam/apps/251570/header.jpg?t=1502044027")
                .setServerLink("localhost:8081")
                .setServerIpAddress(null)
                .build();
        String yaml = MAPPER.writeValueAsString(model);
        assertEquals(
                "---\n" +
                        "gameName: \"7 Days to Die\"\n" +
                        "serverName: \"Private 1\"\n" +
                        "imageUrl: \"http://cdn.edgecast.steamstatic.com/steam/apps/251570/header.jpg?t=1502044027\"\n" +
                        "serverLink: \"localhost:8081\"\n" +
                        "serverIpAddress: null\n" +
                        "commands: null\n",
                yaml);
    }

    @Test
    public void serialize3() throws JsonProcessingException {
        GameModel model = new GameModel.Builder("7 Days to Die", "Private 1")
                .setServerLink("localhost:8081")
                .setServerIpAddress(null)
                .build();
        String yaml = MAPPER.writeValueAsString(model);
        assertEquals(
                "---\n" +
                        "gameName: \"7 Days to Die\"\n" +
                        "serverName: \"Private 1\"\n" +
                        "imageUrl: null\n" +
                        "serverLink: \"localhost:8081\"\n" +
                        "serverIpAddress: null\n" +
                        "commands: null\n",
                yaml);
    }
}