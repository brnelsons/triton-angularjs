package com.bnelson.triton.service;

import com.bnelson.triton.api.model.GameMetadata;
import com.bnelson.triton.api.model.UniqueCommandMetadata;
import com.bnelson.triton.domain.data.GameRepositoryImpl;
import com.bnelson.triton.domain.model.GameCommand;
import com.bnelson.triton.domain.model.GameModel;
import com.bnelson.triton.domain.script.ScriptJobManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy KK:mm a");

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
                return scriptJobManager.runScript(gameModel, gameCommand);
            }
        }
        return false;
    }

    @Override
    public Collection<UniqueCommandMetadata> getRunning(GameMetadata gameMetadata) {
        return scriptJobManager.getAllJobsForGame(GameService.convert(gameMetadata))
                .stream()
                .map(uniqueCommand -> new UniqueCommandMetadata(
                        formatter.format(uniqueCommand.getCommandTime().toInstant()),
                        uniqueCommand.getCommand(),
                        uniqueCommand.getOutputDelegate().read()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<UniqueCommandMetadata> getAll(GameMetadata gameMetadata) {
        return null;
    }
}
