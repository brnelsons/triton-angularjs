package com.bnelson.triton.service;

import com.bnelson.triton.api.model.GameMetadata;
import com.bnelson.triton.api.model.ServerStatus;
import com.bnelson.triton.api.model.UniqueCommandMetadata;
import com.bnelson.triton.common.model.ComparatorType;
import com.bnelson.triton.domain.data.GameRepositoryImpl;
import com.bnelson.triton.common.model.GameCommand;
import com.bnelson.triton.domain.model.GameCommandType;
import com.bnelson.triton.domain.model.GameModel;
import com.bnelson.triton.domain.script.ScriptJobManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final ScriptJobManager scriptJobManager;
    private final GameRepositoryImpl gameRepositoryImpl;

    @Autowired
    public JobServiceImpl(ScriptJobManager scriptJobManager,
                          GameRepositoryImpl gameRepositoryImpl) {
        this.scriptJobManager = scriptJobManager;
        this.gameRepositoryImpl = gameRepositoryImpl;
    }

    @Override
    public boolean run(String gameName, String serverName, String commandName) {
        GameModel gameModel = gameRepositoryImpl.getOne(gameName, serverName);
        for (GameCommand gameCommand : gameModel.getCommands()) {
            if (commandName.equals(gameCommand.getName())) {
                return scriptJobManager.runScriptAsync(gameModel, gameCommand);
            }
        }
        return false;
    }

    @Override
    public Collection<UniqueCommandMetadata> getRunning(GameMetadata gameMetadata) {
        return getAll(gameMetadata);
    }

    @Override
    public Collection<UniqueCommandMetadata> getAll(GameMetadata gameMetadata) {
        return scriptJobManager.getAllJobsForGame(GameService.convert(gameMetadata))
                .stream()
                .map(JobService::convert)
                .collect(Collectors.toList());
    }

    @Override
    public ServerStatus isRunning(String gameName, String serverName) {
        GameModel gameModel = gameRepositoryImpl.getOne(gameName, serverName);
        for (GameCommand gameCommand : gameModel.getCommands()) {
            if (GameCommandType.IS_RUNNING.equals(gameCommand.getType())) {
                return compareResults(
                        scriptJobManager.runScript(gameModel, gameCommand, 500),
                        gameCommand.getExpectedResult(),
                        gameCommand.getResultComparatorType());
            }
        }
        return ServerStatus.UNKNOWN;
    }

    private ServerStatus compareResults(String result, String expectedResult, ComparatorType resultComparatorType) {
        String cleanResult = result.replace("\n", "");
        switch (resultComparatorType) {
            case EQUALS:
                if (cleanResult.equals(expectedResult)) {
                    return ServerStatus.RUNNING;
                } else {
                    return ServerStatus.STOPPED;
                }
            case CONTAINS:
                if (cleanResult.contains(expectedResult)) {
                    return ServerStatus.RUNNING;
                } else {
                    return ServerStatus.STOPPED;
                }
            default:
                return ServerStatus.UNKNOWN;
        }
    }
}
