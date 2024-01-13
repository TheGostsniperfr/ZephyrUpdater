package com.zephyrupdater.common.ZUCommandCore;

public enum ZUCTypes {
    LOGIN("login"),
    DISCONNECTION("exit"),
    MESSAGE("msg"),
    CONNECT("connect"),
    STOP("stop"),
    HELP("help"),
    UPDATE("update");
    private final String cmdName;

    ZUCTypes(String cmdName) {
        this.cmdName = cmdName;
    }
    public String getCmdName() { return this.cmdName; }
}
