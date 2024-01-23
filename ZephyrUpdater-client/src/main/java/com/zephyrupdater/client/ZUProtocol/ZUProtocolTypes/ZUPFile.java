package com.zephyrupdater.client.ZUProtocol.ZUProtocolTypes;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.MainClient;
import com.zephyrupdater.client.ZUProtocol.ZUPStruct;
import com.zephyrupdater.common.ZUFile.ZUFileManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPFileCore;


public class ZUPFile extends ZUPFileCore implements ZUPStruct {
    public ZUPFile(JsonObject dataHeader) {
        super(dataHeader);
    }

    @Override
    public void execute() {
        ZUFileManager.createFileFromStream(AppClient.getServerSocket(), this, MainClient.clientFilePath);
        AppClient.fileReady = true;
    }
}
