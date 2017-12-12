package com.bnelson.triton.api.model;

public class LinkMetadata {
    public static final String home = "/";
    public static final String login = "/login";
    public static final String create = "/create";
    public static final String settings = "/settings";

    public String getHome() {
        return home;
    }

    public String getLogin() {
        return login;
    }

    public String getCreate() {
        return create;
    }

    public String getSettings() {
        return settings;
    }
}
