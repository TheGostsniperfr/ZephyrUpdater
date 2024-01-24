package com.zephyrupdater.server.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;
import com.zephyrupdater.server.ZUCommand.ZUCManager;
import com.zephyrupdater.server.ZUCommand.ZUCStruct;
import com.zephyrupdater.server.ZUProtocol.ZUPStruct;
import com.zephyrupdater.server.clientUtils.ClientHandler;

public class ZUPCommand extends ZUPCommandCore implements ZUPStruct {
    public ZUPCommand(JsonObject dataHeader) {
        super(dataHeader);
    }

    @Override
    public void execute(ClientHandler client) {
        ZUCTypes zucTypes = this.cmdStructType;
        Class<? extends ZUCStruct> zucStruct = ZUCManager.getClassByType(this.cmdStructType);

        if(zucStruct == null){
            System.err.println("Invalid Argument: " + zucTypes);
        }

        if(!client.getIsAuth() && zucTypes != ZUCTypes.LOGIN){
            client.sendMsgToClient(ClientHandler.MSG_PLS_LOGIN);
            return;
        }

        try {
            zucStruct.getDeclaredConstructor(JsonObject.class).newInstance(this.content).execute(client);
        } catch (Exception e) {
            System.err.println("Internal error to execute command: ");
            e.printStackTrace();
        }
    }
}
