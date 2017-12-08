package com.bnelson.triton.domain.script;

import com.bnelson.triton.domain.model.GameModel;
import com.bnelson.triton.domain.model.GameCommand;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Component
public class ScriptJobManager {
    private Multimap<GameModel, UniqueCommand> gameCommandMap = ArrayListMultimap.create();

    public boolean runScript(GameModel gameModel, GameCommand gameCommand){

        BatchScriptRunner runner = new BatchScriptRunner(gameCommand.getExe());
        OutputDelegate uniqueCommandOutputD = new OutputDelegate(10);
        uniqueCommandOutputD.addOutput(runner.run());
        gameCommandMap.put(gameModel, new UniqueCommand(new Date(), gameCommand, uniqueCommandOutputD));

        return true;
    }

    public Collection<UniqueCommand> getAllJobsForGame(GameModel gameModel){
        return gameCommandMap.get(gameModel);
    }

}
