package com.bnelson.triton.service;

import com.bnelson.triton.api.model.GameMetadata;
import com.bnelson.triton.domain.model.GameModel;
import com.google.common.collect.Lists;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameServiceTest {

    @Test
    public void convert(){
        GameMetadata metadata = new GameMetadata.Builder("TestGame", "TestServer")
                .setImageUrl("asd")
                .setServerIpAddress("1.1.1.1")
                .setServerLink("www.server.net")
                .setCommands(Lists.newArrayList())
                .build();
        GameModel convert = GameService.convert(metadata);
        GameMetadata meta = GameService.convert(convert);
        assertEquals(metadata, meta);
    }

}