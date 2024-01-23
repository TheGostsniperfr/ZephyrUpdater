package com.zephyrupdater.common.ZUCommandCore.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;

public class ZUCStopCore implements ZUCStructCore {

    @Override
    public ZUCTypes getStructType() {
        return ZUCTypes.STOP;
    }

    public static String getCmdName(){
        return "stop";
    }

    @Override
    public JsonObject getJson() {
        return new JsonObject();
    }

    public ZUCStopCore() {}
    public static void printHelp(){
        System.out.println("stop");
    }
}
