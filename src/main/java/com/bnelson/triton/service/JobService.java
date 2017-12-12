package com.bnelson.triton.service;

import com.bnelson.triton.api.model.GameMetadata;
import com.bnelson.triton.api.model.ServerStatus;
import com.bnelson.triton.api.model.UniqueCommandMetadata;
import com.bnelson.triton.domain.script.UniqueCommand;
import org.springframework.format.datetime.DateFormatter;

import java.util.Collection;
import java.util.Locale;

public interface JobService {

    DateFormatter DATE_FORMATTER = new DateFormatter("MM/dd/yyyy KK:mm:ss.SSS a");

    boolean run(String gameName, String serverName, String commandName);

    ServerStatus isRunning(String gameName, String serverName);

    Collection<UniqueCommandMetadata> getRunning(GameMetadata gameMetadata);

    Collection<UniqueCommandMetadata> getAll(GameMetadata gameMetadata);

    static UniqueCommandMetadata convert(UniqueCommand command){
        return new UniqueCommandMetadata(
                DATE_FORMATTER.print(command.getTime(), Locale.ENGLISH),
                command.getCommand(),
                command.getOutputDelegate().read()
        );
    }
}
