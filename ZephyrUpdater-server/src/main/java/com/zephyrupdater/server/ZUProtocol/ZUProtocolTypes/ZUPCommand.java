package com.zephyrupdater.server.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;
import com.zephyrupdater.server.ZUCommand.ZUCList.*;
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
        ZUCStruct zucStruct = null;
        switch (zucTypes){
            case LOGIN:
                zucStruct = new ZUCLogin(this.content);
                break;
            case MESSAGE:
                zucStruct = new ZUCMessage(this.content);
                break;
            case DISCONNECTION:
                zucStruct = new ZUCDisconnection();
                break;
            case UPDATE:
                zucStruct = new ZUCUpdate(this.content);
                break;
            case GET_FILE:
                zucStruct = new ZUCGetFile(this.content);
                break;
            default:
                System.err.println("Invalid Argument: " + zucTypes);
        }

        if(zucStruct != null) {
            if(!client.getIsAuth() && zucTypes != ZUCTypes.LOGIN){
                client.sendMsgToClient(ClientHandler.MSG_PLS_LOGIN);
                return;
            }
            zucStruct.execute(client);
        }
    }
}
