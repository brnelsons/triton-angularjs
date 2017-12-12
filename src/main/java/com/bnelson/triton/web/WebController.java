package com.bnelson.triton.web;

import com.bnelson.triton.api.model.GameLinkMetadata;
import com.bnelson.triton.api.model.GameMetadata;
import com.bnelson.triton.api.model.LinkMetadata;
import com.bnelson.triton.api.model.UniqueCommandMetadata;
import com.bnelson.triton.service.GameService;
import com.bnelson.triton.service.JobService;
import com.bnelson.triton.web.places.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

/**
 * Created by brnel on 11/3/2017.
 * TODO fill out class documentation
 */
@Controller
public class WebController {

    private final GameService gameService;
    private final JobService jobService;

    @Autowired
    public WebController(GameService gameService, JobService jobService) {
        this.gameService = gameService;
        this.jobService = jobService;
    }

    @GetMapping(LinkMetadata.home)
    public ModelAndView homepage() {
        return new HomePlace().getModelAndView();
    }

    @GetMapping(LinkMetadata.login)
    public ModelAndView login() {
        return new LoginPlace().getModelAndView();
    }

    @GetMapping(LinkMetadata.create)
    public ModelAndView nav() {
        return new CreatePlace().getModelAndView();
    }

    @GetMapping(LinkMetadata.settings)
    public ModelAndView settings() {
        return new SettingsPlace().getModelAndView();
    }

    @GetMapping(GameLinkMetadata.CONFIG)
    public ModelAndView configGame(@PathVariable(GameLinkMetadata.GAME_NAME) String gameName,
                                   @PathVariable(GameLinkMetadata.SERVER_NAME) String serverName) {
        GameMetadata gameMetadata = gameService.getOne(gameName, serverName);
        return new ConfigPlace(gameMetadata).getModelAndView();
    }

    @GetMapping(GameLinkMetadata.LOG)
    public ModelAndView viewLog(@PathVariable(GameLinkMetadata.GAME_NAME) String gameName,
                                @PathVariable(GameLinkMetadata.SERVER_NAME) String serverName) {
        GameMetadata gameMetadata = gameService.getOne(gameName, serverName);
        Collection<UniqueCommandMetadata> allJobs = jobService.getAll(gameMetadata);
        return new LogPlace(gameMetadata, allJobs).getModelAndView();
    }
}
