package com.zephyrupdater.common.ZUCommandCore;

import com.google.gson.JsonObject;

public interface ZUCStructCore {
    ZUCTypes getStructType();
    static String getCmdName(){ return "Unregistered command name."; };

    JsonObject getJson();
}
