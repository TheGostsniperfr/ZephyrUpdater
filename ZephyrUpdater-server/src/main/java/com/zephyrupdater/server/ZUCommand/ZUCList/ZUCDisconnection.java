package com.zephyrupdater.server.ZUCommand.ZUCList;

import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCDisconnectionCore;
import com.zephyrupdater.server.ZUCommand.ZUCStruct;
import com.zephyrupdater.server.client.ClientHandler;

public class ZUCDisconnection extends ZUCDisconnectionCore implements ZUCStruct {
    @Override
    public void execute(ClientHandler client) {
        client.setIsConnect(false);
    }
}
