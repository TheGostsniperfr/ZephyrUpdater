package com.zephyrupdater.common.ZUCommand;

public abstract class ZUCStructCore {
    public ZUCTypes structType;

    public static String getCmdName(){ return "Unregistered command name."; };

    public abstract String getJson();
}
