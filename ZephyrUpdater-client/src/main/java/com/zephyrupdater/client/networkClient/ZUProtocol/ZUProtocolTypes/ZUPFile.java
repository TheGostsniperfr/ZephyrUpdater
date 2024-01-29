package com.zephyrupdater.client.networkClient.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.networkClient.ZUProtocol.ZUPStruct;
import com.zephyrupdater.client.networkClient.ZephyrNetClient;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPFileCore;


public class ZUPFile extends ZUPFileCore implements ZUPStruct {
    public ZUPFile(JsonObject dataHeader) {
        super(dataHeader);
    }

    @Override
    public void execute() {
        // TODO
        //ZUFileManager.createFileFromStream(ZephyrNetClient.getServerSocket(), this, game.getGameDir());
        ZephyrNetClient.fileReady = true;
    }
}
