package com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPManager;
import com.zephyrupdater.common.ZUProtocol.ZUPStruct;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;

import java.nio.charset.StandardCharsets;

public class ZUPFile extends ZUPStruct {
    public String fileName;

    public ZUPFile(JsonObject dataHeader){
        this.structType = ZUPTypes.FILE;
        this.dataSize = CommonUtil.getValueFromJson(ZUPKeys.DATA_SIZE.getKey(), dataHeader, Long.class);
        this.isMultiChunks = true;
    }

    public ZUPFile(String filename, long size){
        this.structType = ZUPTypes.FILE;
        this.fileName = filename;
        this.dataSize = size;
        this.isMultiChunks = true;
    }

    @Override
    public String getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ZUPKeys.STRUCT_TYPE.getKey(), ZUPTypes.FILE.toString());
        jsonObject.addProperty(ZUPKeys.FILE_NAME.getKey(), this.fileName);
        jsonObject.addProperty(ZUPKeys.DATA_SIZE.getKey(), -1); // TODO

        return jsonObject.toString();
    }
}
