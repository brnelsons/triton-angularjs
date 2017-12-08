package com.bnelson.triton.service;

import com.bnelson.triton.api.model.GameMetadata;
import com.bnelson.triton.api.model.UniqueCommandMetadata;

import java.util.Collection;

public interface JobService {

    boolean run(String gameName, String serverName, String commandName);

    Collection<UniqueCommandMetadata> getRunning(GameMetadata gameMetadata);

    Collection<UniqueCommandMetadata> getAll(GameMetadata gameMetadata);
}
