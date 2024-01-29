package com.zephyrupdater.client.networkClient.ZUProtocol;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.networkClient.ZUProtocol.ZUProtocolTypes.ZUPCommand;
import com.zephyrupdater.client.networkClient.ZUProtocol.ZUProtocolTypes.ZUPFile;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUProtocolCore.ZUPKeys;
import com.zephyrupdater.common.ZUProtocolCore.ZUPStructCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPTypes;

public interface ZUPStruct extends ZUPStructCore {
    void execute();

    static ZUPStruct getStructFromDataHeader(JsonObject dataHeader){

        ZUPTypes dataType = null;

        String dataStrType = CommonUtil.getValueFromJson(
                ZUPKeys.STRUCT_TYPE.getKey(),
                dataHeader,
                String.class
        );

        for (ZUPTypes cType : ZUPTypes.values()) {
            if (cType.toString().equals(dataStrType)) {
                dataType = cType;
                break;
            }
        }

        if (dataType == null) {
            System.err.println("Unknown ZUPStruct type: " + dataStrType);
            return null;
        }
        switch (dataType) {
            case COMMAND:
                return new ZUPCommand(dataHeader);
            case FILE:
                return new ZUPFile(dataHeader);
            default:
                throw new IllegalArgumentException();
        }
    }
}
