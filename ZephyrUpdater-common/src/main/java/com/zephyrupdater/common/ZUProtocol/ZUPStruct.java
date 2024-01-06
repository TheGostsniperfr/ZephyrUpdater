package com.zephyrupdater.common.ZUProtocol;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class ZUPStruct {
    public ZUPTypes structType;
    public long dataSize;
    public Boolean isMultiChunks = false;

    public abstract String getJson();
}