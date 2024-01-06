package com.zephyrupdater.common.ZUCommand;

import com.google.gson.Gson;

public abstract class ZUCStruct {
    public ZUCTypes structType;

    public static String getCmdName(){ return "Unregistered command name."; };

    public abstract String getJson();
}
