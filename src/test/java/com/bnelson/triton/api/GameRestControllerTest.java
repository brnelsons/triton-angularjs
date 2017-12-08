package com.bnelson.triton.api;

import com.bnelson.triton.api.model.GameMetadata;
import com.bnelson.triton.service.GameService;
import com.bnelson.triton.service.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@WebMvcTest(GameRestController.class)
public class GameRestControllerTest {

    private static final GameMetadata GAME_1 = new GameMetadata.Builder("TestGame", "TestServer").build();
    private static final GameMetadata GAME_2 = new GameMetadata.Builder("TestGame", "TestServer").build();

    private static final List<GameMetadata> GAMES = Lists.newArrayList(
            GAME_1,GAME_2
    );

    @MockBean
    GameService gameService;

    @MockBean
    JobService jobService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setup(){

        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        when(gameService.getAll()).thenReturn(GAMES);
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get("/api/v1/game/"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(GAMES)));
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(post("/api/v1/game/create", objectMapper.writeValueAsString(GAME_1)))
                .andExpect(status().isOk());
        verify(gameService, times(1)).create(GAME_1);
    }

}