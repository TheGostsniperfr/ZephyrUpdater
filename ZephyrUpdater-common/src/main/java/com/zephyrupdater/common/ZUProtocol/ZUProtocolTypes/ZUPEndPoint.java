package com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPStruct;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;

import java.nio.charset.StandardCharsets;

public class ZUPEndPoint extends ZUPStruct {
    //End point is to mark the end of a multi chunks data (ex: file)

    public static final String endPointFlag = "@!@__END_POINT__@!@";
    public ZUPEndPoint(){
        this.structType = ZUPTypes.END_POINT;
        this.dataSize = -1;
    }
}
