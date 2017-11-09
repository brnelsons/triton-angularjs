package com.bnelson.triton.service;

import com.bnelson.triton.business.GameBO;
import com.bnelson.triton.pojo.Game;
import com.bnelson.triton.pojo.GameMetaData;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
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

    @PostMapping("/add")
    public ResponseEntity<Boolean> addGame(@RequestBody Game game){
        return ResponseEntity.ok(gameBO.addGame(game));
    }
}
