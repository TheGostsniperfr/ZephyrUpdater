package com.zephyrupdater.server.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCGetFileCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;
import com.zephyrupdater.common.ZUFile.ZUFileManager;
import com.zephyrupdater.server.MainServer;
import com.zephyrupdater.server.ZUCommand.ZUCStruct;
import com.zephyrupdater.server.clientUtils.ClientHandler;

import java.io.File;

public class ZUCGetFile extends ZUCGetFileCore implements ZUCStruct {
    public ZUCGetFile(JsonObject data) {
        super(data);
    }

    @Override
    public void execute(ClientHandler client) {

        // check if the file exist on server
        this.absFilePath = MainServer.publicDirPath.resolve(this.relativeFilePath);
        File serverFile = new File(this.absFilePath.toUri());
        if(!serverFile.exists() || serverFile.isDirectory()){
            // Invalid file
            client.sendMsgToClient("File does not exist on server.");
            return;
        }

        // Send file to client
        ZUFileManager.sendFileFromStream(client.clientSocket, this);
    }

    public static ZUCTypes getStructType(){
        return ZUCTypes.GET_FILE;
    }
}
