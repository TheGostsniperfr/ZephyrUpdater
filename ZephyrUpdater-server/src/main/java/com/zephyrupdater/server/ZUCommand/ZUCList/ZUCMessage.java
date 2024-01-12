package com.zephyrupdater.server.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCMessageCore;
import com.zephyrupdater.server.ZUCommand.ZUCStruct;
import com.zephyrupdater.server.client.ClientHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ZUCMessage extends ZUCMessageCore implements ZUCStruct {
    public ZUCMessage(JsonObject data) {
        super(data);
    }

    @Override
    public void execute(ClientHandler client) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH:mm:ss");

        String clientId = "";
        if(client.getIsAuth()) {
            clientId = " Id: " +client.getClientId() ;
        }
        System.out.println(dateFormat.format(currentDate)
                + " from "
                + client.getHost()
                + clientId
                + " -> " + this.content);
    }
}
