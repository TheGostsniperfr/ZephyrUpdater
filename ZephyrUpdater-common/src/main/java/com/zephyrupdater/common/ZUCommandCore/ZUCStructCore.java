package com.zephyrupdater.common.ZUCommandCore;

public interface ZUCStructCore {
    ZUCTypes getStructType();

    static String getCmdName(){ return "Unregistered command name."; };

    String getJson();
}
