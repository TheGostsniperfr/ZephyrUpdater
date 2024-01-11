package com.zephyrupdater.common.ZUCommand;

import com.zephyrupdater.common.ZUCommand.ZUCList.*;

public enum ZUCTypes {
    LOGIN("login"),
    DISCONNECTION("exit"),
    MESSAGE("msg"),
    CONNECT("connect"),
    QUIT("quit"),
    HELP("help");
    private final String cmdName;

    ZUCTypes(String cmdName) {
        this.cmdName = cmdName;
    }
    public String getCmdName() { return this.cmdName; }
}
