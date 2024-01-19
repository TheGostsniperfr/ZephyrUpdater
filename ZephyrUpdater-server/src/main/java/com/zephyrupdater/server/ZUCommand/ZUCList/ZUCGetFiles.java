package com.zephyrupdater.server.ZUCommand.ZUCList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCGetFilesCore;
import com.zephyrupdater.server.MainServer;
import com.zephyrupdater.server.ZUCommand.ZUCStruct;
import com.zephyrupdater.server.client.ClientHandler;
import com.zephyrupdater.server.updater.request.UpdateRequestManager;

import java.nio.file.Path;

public class ZUCGetFiles extends ZUCGetFilesCore implements ZUCStruct {

    public Path absTargetDirPath;

    public ZUCGetFiles(JsonObject data) {
        super(data);
    }
    public ZUCGetFiles(JsonElement extFilesJson) {
        super(extFilesJson);
    }

    @Override
    public void execute(ClientHandler client) {
        this.absTargetDirPath = MainServer.publicDirPath.resolve(absTargetDirPath);
        client.sendCmdToClient(new ZUCGetFiles(UpdateRequestManager.getResponse(this)));
    }
}
