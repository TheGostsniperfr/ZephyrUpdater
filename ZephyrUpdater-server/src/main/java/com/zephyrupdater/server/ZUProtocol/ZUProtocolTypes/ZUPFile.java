package com.zephyrupdater.server.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUFile.FileManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPFileCore;
import com.zephyrupdater.server.ZUProtocol.ZUPStruct;
import com.zephyrupdater.server.client.ClientHandler;

public class ZUPFile extends ZUPFileCore implements ZUPStruct{
    public ZUPFile(JsonObject dataHeader) {
        super(dataHeader);
    }
    @Override
    public void execute(ClientHandler client) {
        FileManager.createFileFromStream(client.clientSocket, this);
    }
}
