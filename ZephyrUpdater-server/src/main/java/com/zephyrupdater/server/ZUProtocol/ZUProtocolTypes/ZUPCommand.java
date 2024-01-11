package com.zephyrupdater.server.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCLoginCore;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCMessageCore;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPCommandCore;
import com.zephyrupdater.server.ZUProtocol.ZUPStruct;
import com.zephyrupdater.server.client.Auth.ClientAuth;
import com.zephyrupdater.server.client.ClientHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ZUPCommand extends ZUPCommandCore implements ZUPStruct {
    public ZUPCommand(JsonObject dataHeader) {
        super(dataHeader);
    }

    @Override
    public void execute(ClientHandler client) {
        ZUCTypes zucTypes = this.cmdStructType;
        JsonObject data = JsonParser.parseString(this.content).getAsJsonObject();

        switch (zucTypes){
            case LOGIN:
                if(!ClientAuth.isValidAccount(new ZUCLoginCore(data))){
                    client.setIsConnect(false);
                }
                break;
            case MESSAGE:
                ZUCMessageCore zucMessage = new ZUCMessageCore(data);

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH:mm:ss");

                System.out.println(dateFormat.format(currentDate)
                        + " from "
                        + client.getHost()
                        + " -> " + zucMessage.content);

                break;
            case DISCONNECTION:
                client.setIsConnect(false);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
