package com.bnelson.triton.domain.script;

import com.bnelson.triton.service.pojo.Game;
import com.bnelson.triton.service.pojo.GameCommand;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Component
public class ScriptJobManager {
    private Multimap<Game, UniqueCommand> gameCommandMap = ArrayListMultimap.create();

    public boolean runScript(Game game, GameCommand gameCommand){

        BatchScriptRunner runner = new BatchScriptRunner(gameCommand.getExe());
        OutputDelegate uniqueCommandOutputD = new OutputDelegate(10);
        uniqueCommandOutputD.addOutput(runner.run());
        gameCommandMap.put(game, new UniqueCommand(new Date(), gameCommand, uniqueCommandOutputD));

        return true;
    }

    public Collection<UniqueCommand> getAllJobsForGame(Game game){
        return gameCommandMap.get(game);
    }

}
