package com.zephyrupdater.common.ZUCommandCore;

import java.lang.reflect.Method;

public enum ZUCTypes {
    LOGIN("login"),
    DISCONNECTION("exit"),
    MESSAGE("msg"),
    CONNECT("connect"),
    STOP("stop"),
    HELP("help"),
    UPDATE("update"),
    GET_FILE("getFile");

    private final String cmdName;

    ZUCTypes(String cmdName) {
        this.cmdName = cmdName;
    }
    public String getCmdAlias() { return this.cmdName; }

    public static ZUCTypes getZUCType(ZUCStructCore zucStructCore){
        try {
            Method method = zucStructCore.getClass().getMethod("getStructType");
            return (ZUCTypes) method.invoke(null);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        throw new IllegalArgumentException();
    }
}
