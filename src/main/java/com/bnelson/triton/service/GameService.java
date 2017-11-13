package com.bnelson.triton.service;

import com.bnelson.triton.business.GameBO;
import com.bnelson.triton.pojo.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Iterable<Game> getAll() {
        return gameBO.getAllGames();
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createGame(@RequestBody Game game) {
        return ResponseEntity.ok(gameBO.createGame(game));
    }

    @GetMapping("/config/{gameName}/{serverName}")
    public Game getGame(@PathVariable("gameName") String gameName,
                        @PathVariable("serverName") String serverName) {
        return gameBO.getGame(gameName, serverName);
    }

    @PutMapping("/config/{gameName}/{serverName}")
    public void updateGame(@PathVariable("gameName") String gameName,
                           @PathVariable("serverName") String serverName,
                           @RequestBody() Game game) {
//        return ResponseEntity.ok();
        gameBO.update(game);
    }
}
