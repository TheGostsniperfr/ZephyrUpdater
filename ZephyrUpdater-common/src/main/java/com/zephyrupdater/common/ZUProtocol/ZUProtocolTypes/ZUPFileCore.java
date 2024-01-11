package com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPStructCore;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;

public class ZUPFileCore implements ZUPStructCore {
    public String fileName;
    private final long dataSize;
    public ZUPFileCore(JsonObject dataHeader){
        this.dataSize = CommonUtil.getValueFromJson(ZUPKeys.DATA_SIZE.getKey(), dataHeader, Long.class);
    }

    public ZUPFileCore(String filename, long size){
        this.fileName = filename;
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
    public Boolean getIsMultiChunks() {
        return true;
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
