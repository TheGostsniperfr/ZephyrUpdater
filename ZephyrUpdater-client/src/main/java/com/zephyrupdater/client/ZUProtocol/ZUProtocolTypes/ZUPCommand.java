package com.zephyrupdater.client.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.client.ZUCommand.ZUCList.ZUCDisconnection;
import com.zephyrupdater.client.ZUCommand.ZUCList.ZUCMessage;
import com.zephyrupdater.client.ZUCommand.ZUCStruct;
import com.zephyrupdater.client.ZUProtocol.ZUPStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;


public class ZUPCommand extends ZUPCommandCore implements ZUPStruct {
    public ZUPCommand(JsonObject dataHeader) {
        super(dataHeader);
    }

    @Override
    public void execute() {
        ZUCTypes zucTypes = this.cmdStructType;
        JsonObject data = JsonParser.parseString(this.content).getAsJsonObject();

        ZUCStruct zucStruct;

        switch (zucTypes){
            case MESSAGE:
                zucStruct = new ZUCMessage(data);
                break;
            case DISCONNECTION:
                zucStruct = new ZUCDisconnection();
                break;
            default:
                throw new IllegalArgumentException();
        }

        zucStruct.executeServerCmd();
    }
}
