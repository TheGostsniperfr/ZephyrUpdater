package com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;
import com.zephyrupdater.common.ZUProtocolCore.ZUPKeys;
import com.zephyrupdater.common.ZUProtocolCore.ZUPStructCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPTypes;
public class ZUPCommandCore implements ZUPStructCore {
    public ZUCTypes cmdStructType;
    public JsonObject content;
    private final long dataSize;


    public ZUPCommandCore(JsonObject dataHeader){
        this.dataSize = CommonUtil.getValueFromJson(ZUPKeys.DATA_SIZE.getKey(), dataHeader, Long.class);
        this.content =  dataHeader.get(ZUPKeys.CONTENT.getKey()).getAsJsonObject();

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
        if(dataSize != -1 && dataSize != content.size()){
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
        this.cmdStructType = cmd.getStructType();
        this.content = cmd.getJson();
        this.dataSize = content.size();
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
    public JsonObject getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ZUPKeys.COMMAND.getKey(), this.cmdStructType.toString());
        jsonObject.addProperty(ZUPKeys.STRUCT_TYPE.getKey(), ZUPTypes.COMMAND.toString());
        jsonObject.add(ZUPKeys.CONTENT.getKey(), this.content);
        jsonObject.addProperty(ZUPKeys.DATA_SIZE.getKey(), this.content.size());

        return jsonObject;
    }
}