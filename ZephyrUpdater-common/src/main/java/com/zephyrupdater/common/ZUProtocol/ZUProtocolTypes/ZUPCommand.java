package com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPStruct;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;

import java.nio.charset.StandardCharsets;
public class ZUPCommand extends ZUPStruct {
    public ZUCTypes cmdStructType; /* TODO */
    public String content;
    public ZUPCommand(JsonObject dataHeader){
        this.structType = ZUPTypes.COMMAND;
        this.dataSize = getValueFromJson(ZUPKeys.DATA_SIZE.getKey(), dataHeader, Long.class);
        this.content = getValueFromJson(ZUPKeys.CONTENT.getKey(), dataHeader, String.class);

        String cmdTypeStr = getValueFromJson(ZUPKeys.COMMAND.getKey(), dataHeader, String.class);
        for(ZUCTypes cmdType : ZUCTypes.values()){
            if(cmdType.toString().equals(cmdTypeStr)){
                this.cmdStructType = cmdType;
                break;
            }
        }

        if(cmdStructType == null){
            System.err.println("Unknown ZUCStruct type: " + cmdTypeStr);
            return;
        }


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
    public ZUPCommand(ZUCTypes cmdStructType, String content){
        this.structType = ZUPTypes.MESSAGE;
        this.cmdStructType = cmdStructType;

        this.content = message;
        this.dataSize = content.getBytes(StandardCharsets.UTF_8).length;
    }
    public ZUPCommand(ZUCTypes cmdStructType){
        this.structType = ZUPTypes.MESSAGE;
        this.cmdStructType = cmdStructType;

        this.content = "";
        this.dataSize = content.getBytes(StandardCharsets.UTF_8).length;

    }
}