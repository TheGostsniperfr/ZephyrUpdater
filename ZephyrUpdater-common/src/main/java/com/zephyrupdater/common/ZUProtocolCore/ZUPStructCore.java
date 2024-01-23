package com.zephyrupdater.common.ZUProtocolCore;


import com.google.gson.JsonObject;

public interface ZUPStructCore {
    ZUPTypes getStructType();
    long getDataSize();
    JsonObject getJson();
}