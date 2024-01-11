package com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCStructCore;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPStructCore;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;

import java.nio.charset.StandardCharsets;
public class ZUPCommandCore implements ZUPStructCore {
    public ZUCTypes cmdStructType;
    public String content;

    private final long dataSize;


    public ZUPCommandCore(JsonObject dataHeader){
        this.dataSize = CommonUtil.getValueFromJson(ZUPKeys.DATA_SIZE.getKey(), dataHeader, Long.class);
        this.content =  CommonUtil.getValueFromJson(ZUPKeys.CONTENT.getKey(), dataHeader, String.class);

        String cmdTypeStr = CommonUtil.getValueFromJson(ZUPKeys.COMMAND.getKey(), dataHeader, String.class);
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

        //check if we lost bytes during data transfer:
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
    public ZUPCommandCore(ZUCStructCore cmd){
        this.cmdStructType = cmd.structType;
        this.content = cmd.getJson();
        this.dataSize = content.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public ZUPTypes getStructType() {
        return ZUPTypes.COMMAND;
    }

    @Override
    public long getDataSize() {
        return this.dataSize;
    }

    @Override
    public Boolean getIsMultiChunks() {
        return false;
    }

    @Override
    public String getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ZUPKeys.COMMAND.getKey(), this.cmdStructType.toString());
        jsonObject.addProperty(ZUPKeys.STRUCT_TYPE.getKey(), ZUPTypes.COMMAND.toString());
        jsonObject.addProperty(ZUPKeys.CONTENT.getKey(), this.content);
        jsonObject.addProperty(ZUPKeys.DATA_SIZE.getKey(), this.content.getBytes(StandardCharsets.UTF_8).length);

        return jsonObject.toString();
    }
}