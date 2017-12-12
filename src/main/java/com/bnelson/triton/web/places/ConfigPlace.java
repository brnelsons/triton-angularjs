package com.bnelson.triton.web.places;

import com.bnelson.triton.api.model.GameMetadata;

public class ConfigPlace extends GamePlace{

    private static final String ACTIVE = "classActiveConfig";

    public ConfigPlace(GameMetadata gameMetadata) {
        super("config", "Config Server", ACTIVE, gameMetadata);
    }
}
