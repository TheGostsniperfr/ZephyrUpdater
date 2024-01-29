package com.zephyrupdater.client.networkClient.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.networkClient.ZUCommand.ZUCList.ZUCDisconnection;
import com.zephyrupdater.client.networkClient.ZUCommand.ZUCList.ZUCMessage;
import com.zephyrupdater.client.networkClient.ZUCommand.ZUCList.ZUCUpdate;
import com.zephyrupdater.client.networkClient.ZUCommand.ZUCStruct;
import com.zephyrupdater.client.networkClient.ZUProtocol.ZUPStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;


public class ZUPCommand extends ZUPCommandCore implements ZUPStruct {
    public ZUPCommand(JsonObject dataHeader) {
        super(dataHeader);
    }

    @Override
    public void execute() {
        ZUCTypes zucTypes = this.cmdStructType;

        ZUCStruct zucStruct;

        switch (zucTypes){
            case MESSAGE:
                zucStruct = new ZUCMessage(this.content);
                break;
            case DISCONNECTION:
                zucStruct = new ZUCDisconnection();
                break;
            case UPDATE:
                zucStruct = new ZUCUpdate(this.content);
                break;
            default:
                throw new IllegalArgumentException();
        }

        zucStruct.executeServerCmd();
    }
}
