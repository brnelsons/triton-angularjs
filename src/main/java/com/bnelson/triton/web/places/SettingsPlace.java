package com.bnelson.triton.web.places;

import com.bnelson.triton.domain.model.BasicConfig;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class SettingsPlace extends GamePlace{

    private static final String ACTIVE = "classActiveSettings";

    public SettingsPlace(List<BasicConfig> settings) {
        super("settings", "Settings", ACTIVE);
        getModelAndView().addObject("allSettings", settings);
    }
}
