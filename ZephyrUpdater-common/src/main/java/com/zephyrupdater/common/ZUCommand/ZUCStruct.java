package com.zephyrupdater.common.ZUCommand;

import com.google.gson.Gson;

public class ZUCStruct {
    public ZUCTypes structType;
    public String getJson(){
        return new Gson().toJson(this);
    }
}
