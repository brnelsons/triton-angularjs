package com.bnelson.triton.api;

import com.bnelson.triton.api.model.UniqueCommandRpc;
import com.bnelson.triton.domain.script.ScriptJobManager;
import com.bnelson.triton.domain.script.UniqueCommand;
import com.bnelson.triton.service.GameService;
import com.bnelson.triton.service.pojo.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by brnel on 11/3/2017.
 * TODO fill out class documentation
 */
@RestController
@RequestMapping("/api/v1/game")
public class GameRestController {

    private final GameService gameService;

    @Autowired
    public GameRestController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public Iterable<Game> getAll() {
        return gameService.getAllGames();
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createGame(@RequestBody Game game) {
        return ResponseEntity.ok(gameService.createGame(game));
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateGame(@RequestBody Game game) {
        return ResponseEntity.ok(gameService.update(game));
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteGame(@RequestBody Game game) {
        return ResponseEntity.ok(gameService.delete(game));
    }

    @PostMapping("/{gameName}/{serverName}/runCommand/{commandName}/")
    public ResponseEntity<Boolean> runGameCommand(@PathVariable("gameName") String gameName,
                                                  @PathVariable("serverName") String serverName,
                                                  @PathVariable("commandName") String commandName) {
        return ResponseEntity.ok(gameService.runCommand(gameName, serverName, commandName));
    }

    @GetMapping("/{gameName}/{serverName}/jobs")
    public Collection<UniqueCommandRpc> getAllRunningCommands(@PathVariable("gameName") String gameName,
                                                              @PathVariable("serverName") String serverName){
        return gameService.getAllRunningCommands(gameService.getGame(gameName, serverName));
    }

//    @GetMapping("/jobs/finished/")
//    public Map<String, String> getAllFinishedCommands(){
//        return gameService.getAllFinishedCommands();
//    }

    @GetMapping("/{gameName}/{serverName}/")
    public Game getGame(@PathVariable("gameName") String gameName,
                        @PathVariable("serverName") String serverName) {
        return gameService.getGame(gameName, serverName);
    }
}
