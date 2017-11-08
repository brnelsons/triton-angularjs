package com.bnelson.triton.service;

import com.bnelson.triton.business.GameBO;
import com.bnelson.triton.pojo.GameMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by brnel on 11/3/2017.
 * TODO fill out class documentation
 */
@RestController
@RequestMapping("/api/v1/game")
public class GameService {

    private final GameBO gameBO;

    @Autowired
    public GameService(GameBO gameBO) {
        this.gameBO = gameBO;
    }

    @GetMapping("/")
    public Iterable<GameMetaData> getAll(){
        return gameBO.getAllGameMetaData();
    }


}
