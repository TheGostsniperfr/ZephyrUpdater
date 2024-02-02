package com.zephyrupdater.client.games.utils.Updater.ExternalFilesUpdater.ExternalFilesUtils;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.networkClient.ZUCommand.ZUCList.ZUCGetFile;
import com.zephyrupdater.client.networkClient.ZephyrNetClient;
import com.zephyrupdater.common.utils.FileUtils.ExternalFilesUtils.ExternalFileCore;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;

import java.nio.file.Path;

public class ExternalFile extends ExternalFileCore {
    public ExternalFile(JsonObject extJsonFile) {
        super(extJsonFile);
    }

    private void downloadFile(){
        //ZephyrNetClient.sendCmdToServer(new ZUCGetFile(this.getFileName(), this.getRelativeFilePath()));
    }

    private Boolean needToBeUpdate(Path gameDir){
        return !FileUtils.isSameFile(this.getAbsFilePath(gameDir), this.getHash(), this.getHashAlgoType());
    }

    public void checkUpdate(Path gameDir){
        if(needToBeUpdate(gameDir)){
            System.out.println("Starting to download: " + this.getFileName());
            downloadFile();


            System.out.println("Success to download: " + this.getFileName());
        }
    }
}
