package com.zephyrupdater.server.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCUpdateCore;
import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.MainServer;
import com.zephyrupdater.server.ZUCommand.ZUCStruct;
import com.zephyrupdater.server.clientUtils.ClientHandler;

import java.nio.file.Path;

public class ZUCUpdate extends ZUCUpdateCore implements ZUCStruct {
    public Path absTargetDirPath;
    public ZUCUpdate(JsonObject data) {
        super(data);
    }

    public ZUCUpdate(JsonObject extFilesJson, JsonObject curseModJson) {
        super(extFilesJson, curseModJson);
    }

    @Override
    public void execute(ClientHandler client) {
        this.absTargetDirPath = MainServer.publicDirPath.resolve(absTargetDirPath);
        JsonObject extFilesJson = AppServer.getUpdateRequestManager().getResponse(this);

        if(extFilesJson == null){
            client.sendMsgToClient("Request doest not exist on server.");
            return;
        }

        this.curseModJson = AppServer.getCurseForgeUpdater().getModList();
        client.sendCmdToClient(new ZUCUpdate(extFilesJson, this.curseModJson));
    }
}