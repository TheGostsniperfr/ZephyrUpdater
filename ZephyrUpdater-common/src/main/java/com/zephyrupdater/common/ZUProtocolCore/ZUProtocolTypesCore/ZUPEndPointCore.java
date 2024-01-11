package com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUProtocolCore.ZUPKeys;
import com.zephyrupdater.common.ZUProtocolCore.ZUPStructCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPTypes;

public class ZUPEndPointCore implements ZUPStructCore {
    //End point is to mark the end of a multi chunks data (ex: file)
    private final long dataSize;
    public static final String endPointFlag = CommonUtil.getFormatCmd("END_POINT");
    public static final byte[] endPointFlagByte = endPointFlag.getBytes();
    public ZUPEndPointCore(){
        this.dataSize = -1;
    }
    @Override
    public ZUPTypes getStructType() {
        return ZUPTypes.END_POINT;
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
        jsonObject.addProperty(ZUPKeys.STRUCT_TYPE.getKey(), ZUPTypes.END_POINT.toString());

        return jsonObject.toString();
    }
}
