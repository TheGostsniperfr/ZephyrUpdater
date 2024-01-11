package com.zephyrupdater.server.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCLogin;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCMessage;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;
import com.zephyrupdater.common.ZUFile.FileManager;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPManager;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPCommand;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPFile;
import com.zephyrupdater.server.client.Auth.ClientAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientProcess {
    public static void clientDataHeaderProcess(ClientHandler client, JsonObject dataHeader){
        String zupName = CommonUtil.getValueFromJson(
                ZUPKeys.STRUCT_TYPE.getKey(),
                dataHeader,
                String.class
        );

        ZUPTypes dataType = ZUPManager.findZUPTypesByName(zupName);
        if(dataType == null) return;

        switch (dataType) {
            case COMMAND:
                executeClientCmd(client, new ZUPCommand(dataHeader));
                break;
            case FILE:
                FileManager.createFileFromStream(client.clientSocket, new ZUPFile(dataHeader));
                break;
            default:
                System.err.println("Invalid arg read: " + dataType);
        }
    }
    private static void executeClientCmd(ClientHandler client, ZUPCommand zupCommand){
        ZUCTypes zucTypes = zupCommand.cmdStructType;
        JsonObject data = JsonParser.parseString(zupCommand.content).getAsJsonObject();

        switch (zucTypes){
            case LOGIN:
                if(!ClientAuth.isValidAccount(new ZUCLogin(data))){
                    client.setIsConnect(false);
                }
                break;
            case MESSAGE:
                ZUCMessage zucMessage = new ZUCMessage(data);

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
