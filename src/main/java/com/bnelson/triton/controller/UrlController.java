package com.bnelson.triton.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by brnel on 11/3/2017.
 * TODO fill out class documentation
 */
@Controller
public class UrlController {

    @GetMapping("/")
    public String homepage(){
        return "index";
    }
}
