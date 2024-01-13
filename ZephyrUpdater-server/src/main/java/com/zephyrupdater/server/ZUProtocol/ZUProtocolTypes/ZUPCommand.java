package com.zephyrupdater.server.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;
import com.zephyrupdater.server.ZUCommand.ZUCList.ZUCDisconnection;
import com.zephyrupdater.server.ZUCommand.ZUCList.ZUCLogin;
import com.zephyrupdater.server.ZUCommand.ZUCList.ZUCMessage;
import com.zephyrupdater.server.ZUCommand.ZUCList.ZUCUpdate;
import com.zephyrupdater.server.ZUCommand.ZUCStruct;
import com.zephyrupdater.server.ZUProtocol.ZUPStruct;
import com.zephyrupdater.server.client.ClientHandler;

public class ZUPCommand extends ZUPCommandCore implements ZUPStruct {
    public ZUPCommand(JsonObject dataHeader) {
        super(dataHeader);
    }

    @Override
    public void execute(ClientHandler client) {
        ZUCTypes zucTypes = this.cmdStructType;
        JsonObject data = JsonParser.parseString(this.content).getAsJsonObject();
        ZUCStruct zucStruct = null;
        switch (zucTypes){
            case LOGIN:
                zucStruct = new ZUCLogin(data);
                break;
            case MESSAGE:
                zucStruct = new ZUCMessage(data);
                break;
            case DISCONNECTION:
                zucStruct = new ZUCDisconnection();
                break;
            case UPDATE:
                zucStruct = new ZUCUpdate(data);
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
