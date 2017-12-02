package com.bnelson.triton.web;

import com.bnelson.triton.service.GameService;
import com.bnelson.triton.service.pojo.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by brnel on 11/3/2017.
 * TODO fill out class documentation
 */
@Controller
public class WebController {
    private final GameService gameService;

    @Autowired
    public WebController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String homepage(Model model) {
        model.addAttribute("title", "Home");
        model.addAttribute("classActiveHome", "active");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("classActiveLogin", "active");
        return "login";
    }

    @GetMapping("/create")
    public String nav(Model model) {
        model.addAttribute("title", "Create Server");
        model.addAttribute("classActiveCreate", "active");
        return "create";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("title", "Settings");
        model.addAttribute("classActiveSettings", "active");
        return "settings";
    }

    @GetMapping("/config/{gameName}/{serverName}")
    public ModelAndView configGame(@PathVariable("gameName") String gameName,
                                   @PathVariable("serverName") String serverName) {
        Game gameMetaData = gameService.getGame(gameName, serverName);
        ModelAndView modelAndView = new ModelAndView("/config");
        modelAndView.addObject("game", gameMetaData);
        modelAndView.addObject("gameName", gameName);
        modelAndView.addObject("serverName", serverName);
        return modelAndView;
    }

    @GetMapping("/log/{gameName}/{serverName}")
    public ModelAndView viewLog(@PathVariable("gameName") String gameName,
                                @PathVariable("serverName") String serverName) {
        ModelAndView modelAndView = new ModelAndView("viewLog");
        modelAndView.addObject("gameName", gameName);
        modelAndView.addObject("serverName", serverName);
        return modelAndView;
    }
}