package com.zephyrupdater.client.Updater.ExternalFilesUpdater.ExternalFilesUtils;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.ZUCommand.ZUCList.ZUCGetFile;
import com.zephyrupdater.common.FileUtils.ExternalFilesUtils.ExternalFileCore;
import com.zephyrupdater.common.FileUtils.HashUtils.HashAlgo;
import com.zephyrupdater.common.FileUtils.HashUtils.HashAlgoType;

import java.nio.file.Files;

public class ExternalFile extends ExternalFileCore {


    public ExternalFile(JsonObject extJsonFile) {
        super(extJsonFile);
    }

    private void downloadFile(){
        AppClient.sendCmdToServer(new ZUCGetFile(this.getFileName(), this.getRelativeFilePath()));
    }

    private Boolean needToBeUpdate(){
        if(!Files.exists(this.getRelativeFilePath())){
            return true;
        }

        return !HashAlgo.getHashFromFilePath(this.getRelativeFilePath(), HashAlgoType.SHA1).equals(this.getHash());
    }

    public void checkUpdate(){
        if(needToBeUpdate()){
            downloadFile();
        }
    }
}
