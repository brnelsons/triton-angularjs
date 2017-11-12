package com.bnelson.triton.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by brnel on 11/3/2017.
 * TODO fill out class documentation
 */
@Controller
public class WebController {

    @GetMapping("/")
    public String homepage(Model model){
        model.addAttribute("classActiveHome", "active");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("classActiveLogin", "active");
        return "login";
    }

    @GetMapping("/add")
    public String nav(Model model){
        model.addAttribute("classActiveAdd", "active");
        return "add";
    }

    @GetMapping("/settings")
    public String settings(Model model){
        model.addAttribute("classActiveSettings", "active");
        return "settings";
    }
}
