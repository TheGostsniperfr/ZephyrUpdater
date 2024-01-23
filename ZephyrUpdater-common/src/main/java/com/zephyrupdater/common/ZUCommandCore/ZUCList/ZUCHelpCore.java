package com.zephyrupdater.common.ZUCommandCore.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;

public class ZUCHelpCore implements ZUCStructCore {
    public ZUCHelpCore() {}
    public static void printHelp() {
        int n = 0;
        for(ZUCTypes zucTypes : ZUCTypes.values()){
            System.out.println(n++ + ": " + zucTypes.getCmdName());
        }
    }

    @Override
    public ZUCTypes getStructType() {
        return ZUCTypes.HELP;
    }

    public static String getCmdName() {
        return "help";
    }

    @Override
    public JsonObject getJson() {
        return new JsonObject();
    }
}