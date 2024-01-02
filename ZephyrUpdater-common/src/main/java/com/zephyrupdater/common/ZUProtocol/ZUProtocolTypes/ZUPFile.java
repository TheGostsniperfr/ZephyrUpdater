package com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPStruct;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;

import java.nio.charset.StandardCharsets;

public class ZUPFile extends ZUPStruct {
    public String fileName;

    public ZUPFile(JsonObject dataHeader){
        this.structType = ZUPTypes.MESSAGE;
        this.dataSize = getValueFromJson(ZUPKeys.DATA_SIZE.getKey(), dataHeader, Long.class);
        this.isMultiChunks = true;
    }
    public ZUPFile(String message){
        this.structType = ZUPTypes.MESSAGE;
        this.dataSize = message.getBytes(StandardCharsets.UTF_8).length;
        this.isMultiChunks = true;
    }
}
