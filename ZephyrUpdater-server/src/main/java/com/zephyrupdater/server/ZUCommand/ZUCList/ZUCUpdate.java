package com.zephyrupdater.server.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCUpdateCore;
import com.zephyrupdater.server.MainServer;
import com.zephyrupdater.server.ZUCommand.ZUCStruct;
import com.zephyrupdater.server.client.ClientHandler;
import com.zephyrupdater.server.updater.CheckingFiles;

import java.nio.file.Files;
import java.nio.file.Path;

public class ZUCUpdate extends ZUCUpdateCore implements ZUCStruct {
    public ZUCUpdate(JsonObject data) {
        super(data);
    }

    public ZUCUpdate(String folderPath, JsonObject filesJson) {
        super(folderPath, filesJson);
    }

    @Override
    public void execute(ClientHandler client) {
        Path pathToGetJson = MainServer.publicFilesPath.resolve(this.folderPath);
        if(!Files.exists(pathToGetJson)){
            client.sendMsgToClient("Invalid path to check!");
            return;
        }

        this.filesJson = CheckingFiles.getFilesJson(pathToGetJson);
        client.sendCmdToClient(this);
    }
}
