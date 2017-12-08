package com.bnelson.triton.api;

import com.bnelson.triton.api.model.GameMetadata;
import com.bnelson.triton.api.model.UniqueCommandMetadata;
import com.bnelson.triton.service.GameService;
import com.bnelson.triton.service.JobService;
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
    private final JobService jobService;

    @Autowired
    public GameRestController(GameService gameService,
                              JobService jobService) {
        this.gameService = gameService;
        this.jobService = jobService;
    }

    @GetMapping("/")
    public Iterable<GameMetadata> getAll() {
        return gameService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createGame(@RequestBody GameMetadata game) {
        return ResponseEntity.ok(gameService.create(game));
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateGame(@RequestBody GameMetadata game) {
        return ResponseEntity.ok(gameService.update(game));
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteGame(@RequestBody GameMetadata game) {
        return ResponseEntity.ok(gameService.delete(game));
    }

    @PostMapping("/{gameName}/{serverName}/runCommand/{commandName}/")
    public ResponseEntity<Boolean> runGameCommand(@PathVariable("gameName") String gameName,
                                                  @PathVariable("serverName") String serverName,
                                                  @PathVariable("commandName") String commandName) {
        return ResponseEntity.ok(jobService.run(gameName, serverName, commandName));
    }

    @GetMapping("/{gameName}/{serverName}/jobs")
    public Collection<UniqueCommandMetadata> getAllRunningCommands(@PathVariable("gameName") String gameName,
                                                                   @PathVariable("serverName") String serverName){
        return jobService.getAll(gameService.getOne(gameName, serverName));
    }

    @GetMapping("/{gameName}/{serverName}/")
    public GameMetadata getGame(@PathVariable("gameName") String gameName,
                             @PathVariable("serverName") String serverName) {
        return gameService.getOne(gameName, serverName);
    }
}
