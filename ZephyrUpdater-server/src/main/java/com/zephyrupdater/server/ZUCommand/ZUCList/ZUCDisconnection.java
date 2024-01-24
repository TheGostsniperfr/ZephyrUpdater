package com.zephyrupdater.server.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCDisconnectionCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;
import com.zephyrupdater.server.ZUCommand.ZUCStruct;
import com.zephyrupdater.server.clientUtils.ClientHandler;

public class ZUCDisconnection extends ZUCDisconnectionCore implements ZUCStruct {

    public ZUCDisconnection(JsonObject data){
        super(data);
    }
    @Override
    public void execute(ClientHandler client) {
        client.setIsConnect(false);
    }

    public static ZUCTypes getStructType(){
        return ZUCTypes.DISCONNECTION;
    }
}
