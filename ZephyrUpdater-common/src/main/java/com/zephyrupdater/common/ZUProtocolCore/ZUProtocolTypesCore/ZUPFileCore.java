package com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUProtocolCore.ZUPKeys;
import com.zephyrupdater.common.ZUProtocolCore.ZUPStructCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPTypes;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZUPFileCore implements ZUPStructCore {
    public Path filePath;
    private final long dataSize;
    public ZUPFileCore(JsonObject dataHeader){
        this.dataSize = CommonUtil.getValueFromJson(ZUPKeys.DATA_SIZE.getKey(), dataHeader, Long.class);
        this.filePath = Paths.get(CommonUtil.getValueFromJson(ZUPKeys.FILE_PATH.getKey(), dataHeader, String.class));
    }

    public ZUPFileCore(String filePath, long size){
        this.filePath = Paths.get(filePath);
        this.dataSize = size;
    }

    @Override
    public ZUPTypes getStructType() {
        return ZUPTypes.FILE;
    }

    @Override
    public long getDataSize() {
        return this.dataSize;
    }

    @Override
    public String getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ZUPKeys.STRUCT_TYPE.getKey(), ZUPTypes.FILE.toString());
        jsonObject.addProperty(ZUPKeys.FILE_PATH.getKey(), this.filePath.toString());
        jsonObject.addProperty(ZUPKeys.DATA_SIZE.getKey(), this.dataSize);

        return jsonObject.toString();
    }
}
