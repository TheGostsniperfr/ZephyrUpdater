package com.zephyrupdater.common.ZUCommand;

import com.zephyrupdater.common.ZUCommand.ZUCList.*;

public enum ZUCTypes {
    LOGIN(ZUCLogin.class, "login"),
    DISCONNECTION(ZUCDisconnection.class, "exit"),
    MESSAGE(ZUCMessage.class, "msg"),
    CONNECT(ZUCConnect.class, "connect"),
    QUIT(ZUCQuit.class, "quit"),
    HELP(ZUCHelp.class, "help");
    private final Class <? extends ZUCStruct> associateClass;
    private final String cmdName;

    ZUCTypes(Class <? extends ZUCStruct> associateClass, String cmdName) {
        this.associateClass = associateClass;
        this.cmdName = cmdName;
    }

    public Class <? extends  ZUCStruct> getAssociateClass() { return this.associateClass; }
    public String getCmdName() { return this.cmdName; }
}
