package com.bnelson.triton.web.places;

import com.bnelson.triton.api.model.GameMetadata;
import com.bnelson.triton.api.model.UniqueCommandMetadata;

import java.util.Collection;

public class LogPlace extends GamePlace{

    private static final String ACTIVE = "classActiveLog";
    private static final String JOBS = "jobs";

    public LogPlace(GameMetadata gameMetadata, Collection<UniqueCommandMetadata> commands) {
        super("viewLog", "View Logs", ACTIVE, gameMetadata);
        getModelAndView().addObject(JOBS, commands);
    }
}
