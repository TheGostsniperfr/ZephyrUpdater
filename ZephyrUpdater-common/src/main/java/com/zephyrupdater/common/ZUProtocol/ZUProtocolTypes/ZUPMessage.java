package com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPStruct;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;

import java.nio.charset.StandardCharsets;

public class ZUPMessage extends ZUPStruct {
    public String content;
    public ZUPMessage(JsonObject dataHeader){
        this.structType = ZUPTypes.MESSAGE;
        this.dataSize = getValueFromJson(ZUPKeys.DATA_SIZE.getKey(), dataHeader, Long.class);
        this.content = getValueFromJson(ZUPKeys.CONTENT.getKey(), dataHeader, String.class);

        //check if we lost byte during data transfer:
        if(dataSize != content.getBytes(StandardCharsets.UTF_8).length){
            System.err.println(
                    "Error: size not match between: "
                    + ZUPKeys.DATA_SIZE.getKey()
                    + " key and: "
                    + ZUPKeys.CONTENT.getKey()
                    + " key."
            );
        }
    }
    public ZUPMessage(String message){
        this.structType = ZUPTypes.MESSAGE;
        this.dataSize = message.getBytes(StandardCharsets.UTF_8).length;
        this.content = message;
    }
}
