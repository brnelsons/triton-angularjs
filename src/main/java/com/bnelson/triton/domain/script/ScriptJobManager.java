package com.bnelson.triton.domain.script;

import com.bnelson.triton.domain.model.GameModel;
import com.bnelson.triton.common.model.GameCommand;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class ScriptJobManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptJobManager.class);
    private Multimap<GameModel, UniqueCommand> gameCommandMap = ArrayListMultimap.create();

    public String runScript(GameModel gameModel, GameCommand gameCommand, long timeoutMillis) {
        BatchScriptRunner runner = new BatchScriptRunner(gameCommand.getExe());
        OutputDelegate uniqueCommandOutputD = new OutputDelegate(10);
        uniqueCommandOutputD.addOutput(runner.run());
        gameCommandMap.put(gameModel, new UniqueCommand(new Date(), gameCommand, uniqueCommandOutputD));

        try {
            TimeUnit.MILLISECONDS.sleep(timeoutMillis);
        } catch (InterruptedException e) {
            LOGGER.error("Could not hold thread to wait for job completion.");
        }
        return uniqueCommandOutputD.read();
    }

    public boolean runScriptAsync(GameModel gameModel, GameCommand gameCommand) {
        BatchScriptRunner runner = new BatchScriptRunner(gameCommand.getExe());
        OutputDelegate uniqueCommandOutputD = new OutputDelegate(10);
        uniqueCommandOutputD.addOutput(runner.run());
        gameCommandMap.put(gameModel, new UniqueCommand(new Date(), gameCommand, uniqueCommandOutputD));
        return true;
    }

    public Collection<UniqueCommand> getAllJobsForGame(GameModel gameModel) {
        return gameCommandMap.get(gameModel);
    }

}
