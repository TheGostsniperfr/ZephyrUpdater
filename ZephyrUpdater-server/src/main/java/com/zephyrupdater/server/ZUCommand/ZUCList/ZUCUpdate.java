package com.zephyrupdater.server.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCUpdateCore;
import com.zephyrupdater.common.ZUFile.ZUFileManager;
import com.zephyrupdater.server.MainServer;
import com.zephyrupdater.server.ZUCommand.ZUCStruct;
import com.zephyrupdater.server.client.ClientHandler;
import com.zephyrupdater.server.updater.CheckingFiles;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class ZUCUpdate extends ZUCUpdateCore implements ZUCStruct {
    public ZUCUpdate(JsonObject data) {
        super(data);
    }

    public ZUCUpdate(String folderPath, JsonObject filesJson, JsonObject curseModJson) {
        super(folderPath, filesJson, curseModJson);
    }

    @Override
    public void execute(ClientHandler client) {
        try {
            Path pathToGetJson = MainServer.publicDirPath.resolve(this.folderPath);

            if(!Files.exists(pathToGetJson)){
                throw new InvalidPathException("", "Path does not exist.");
            }

            this.filesJson = CheckingFiles.getFilesJson(pathToGetJson);
            this.curseModJson = ZUFileManager.createJsonObjectFromFile(MainServer.curseModJsonPath);
            client.sendCmdToClient(this);
        } catch (InvalidPathException e){
            client.sendMsgToClient("Invalid path to check!");
        }
    }
}
