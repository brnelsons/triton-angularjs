package com.bnelson.triton.web.places;

public class HomePlace extends GamePlace{

    private static final String ACTIVE_HOME = "classActiveHome";

    public HomePlace() {
        super("index", "Home", ACTIVE_HOME);
    }
}
