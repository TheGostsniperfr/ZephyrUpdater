package com.zephyrupdater.server.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUFile.ZUFileManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPFileCore;
import com.zephyrupdater.server.MainServer;
import com.zephyrupdater.server.ZUProtocol.ZUPStruct;
import com.zephyrupdater.server.clientUtils.ClientHandler;

public class ZUPFile extends ZUPFileCore implements ZUPStruct{
    public ZUPFile(JsonObject dataHeader) {
        super(dataHeader);
    }
    @Override
    public void execute(ClientHandler client) {
        ZUFileManager.createFileFromStream(client.clientSocket, this, MainServer.publicDirPath);
    }
}
