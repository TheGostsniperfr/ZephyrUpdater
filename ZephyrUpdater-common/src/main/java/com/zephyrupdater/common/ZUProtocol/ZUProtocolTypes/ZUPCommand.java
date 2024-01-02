package com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPStruct;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;

import java.nio.charset.StandardCharsets;
public class ZUPCommand extends ZUPStruct {
    public String command; /* TODO */
    public String content;
    public ZUPCommand(JsonObject dataHeader){
        this.structType = ZUPTypes.MESSAGE;
        this.command = "";
        this.dataSize = getValueFromJson(ZUPKeys.DATA_SIZE.getKey(), dataHeader, Long.class);
        this.content = getValueFromJson(ZUPKeys.CONTENT.getKey(), dataHeader, String.class);

        //check if we lost byte during data transfer:
        if(dataSize != -1 && dataSize != content.getBytes(StandardCharsets.UTF_8).length){
            System.err.println(
                    "Error: size not match between: "
                            + ZUPKeys.DATA_SIZE.getKey()
                            + " key and: "
                            + ZUPKeys.CONTENT.getKey()
                            + " key."
            );
        }
    }
    public ZUPCommand(String command, String message){
        this.structType = ZUPTypes.MESSAGE;
        this.dataSize = message.getBytes(StandardCharsets.UTF_8).length;
        this.content = message;
        this.command = command;
    }
    public ZUPCommand(String command){
        this.structType = ZUPTypes.MESSAGE;
        this.dataSize = -1;
        this.content = "";
        this.command = command;
    }
}