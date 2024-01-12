package com.zephyrupdater.server.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCLoginCore;
import com.zephyrupdater.server.ZUCommand.ZUCStruct;
import com.zephyrupdater.server.client.Auth.ClientAuth;
import com.zephyrupdater.server.client.ClientHandler;

public class ZUCLogin extends ZUCLoginCore implements ZUCStruct {


    public ZUCLogin(JsonObject data) {
        super(data);
    }

    @Override
    public void execute(ClientHandler client) {
        if(!ClientAuth.isValidAccount(this)){
            client.sendMsgToClient("Wrong id or password.");
            System.out.println(client.getHost() + " Failed to login: wrong id or password.");
            return;
        }

        client.setIsAuth(true);
        client.setClientId(this.id);
        client.sendMsgToClient("Success to login.\nHi " + this.id + " !");
        System.out.println(client.getHost() + " Success to login.");
    }
}
