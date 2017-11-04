package com.bnelson.triton.controller;

import com.bnelson.triton.pojo.GameMetaData;
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
public class GameRestController {

    @GetMapping("/")
    public Iterable<GameMetaData> getAll(){
        ArrayList<GameMetaData> games = new ArrayList<>();
        games.add(new GameMetaData("7 Days to Die", null, "off"));
        games.add(new GameMetaData("Empyrion", null, "off"));
        return games;
    }


}
