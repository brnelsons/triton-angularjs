package com.bnelson.triton.web.places;

public class CreatePlace extends GamePlace{

    private static final String ACTIVE_CREATE = "classActiveCreate";

    public CreatePlace() {
        super("create", "Create Server", ACTIVE_CREATE);
    }
}
