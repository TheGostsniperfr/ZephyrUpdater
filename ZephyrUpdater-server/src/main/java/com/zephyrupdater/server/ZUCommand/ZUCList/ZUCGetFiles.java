package com.zephyrupdater.server.ZUCommand.ZUCList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCGetFilesCore;
import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.MainServer;
import com.zephyrupdater.server.ZUCommand.ZUCStruct;
import com.zephyrupdater.server.client.ClientHandler;

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
        JsonObject response = AppServer.getUpdateRequestManager().getResponse(this);
        if(response == null){
            client.sendMsgToClient("Request doest not exist on server.");
            return;
        }

        client.sendCmdToClient(new ZUCGetFiles(response));
    }
}
