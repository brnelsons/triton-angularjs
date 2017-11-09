package com.bnelson.triton.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by brnel on 11/3/2017.
 * TODO fill out class documentation
 */
@Controller
public class WebController {

    @GetMapping("/")
    public String homepage(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/add")
    public String nav(){
        return "add";
    }

    @GetMapping("/config/{gameName}/{serverName}")
    public String nav(@PathVariable("gameName") String gameName,
                      @PathVariable("serverName") String serverName){
        return "config";
    }
}
