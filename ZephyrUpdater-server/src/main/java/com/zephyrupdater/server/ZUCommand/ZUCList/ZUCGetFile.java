package com.zephyrupdater.server.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCGetFileCore;
import com.zephyrupdater.common.ZUFile.FileManager;
import com.zephyrupdater.server.MainServer;
import com.zephyrupdater.server.ZUCommand.ZUCStruct;
import com.zephyrupdater.server.client.ClientHandler;

import java.io.File;

public class ZUCGetFile extends ZUCGetFileCore implements ZUCStruct {
    public ZUCGetFile(JsonObject data) {
        super(data);
    }

    @Override
    public void execute(ClientHandler client) {

        // check if the file exist on server
        this.absFilePath = MainServer.publicFilesPath.resolve(this.relativeFilePath);
        File serverFile = new File(this.absFilePath.toUri());
        if(!serverFile.exists() || serverFile.isDirectory()){
            // Invalid file
            client.sendMsgToClient("File does not exist on server.");
            return;
        }

        System.out.println("abs Path to file: " + this.absFilePath);
        System.out.println("rela Path to file: " + this.relativeFilePath);

        // Send file to client
        FileManager.sendFileFromStream(client.clientSocket, this);
    }
}
