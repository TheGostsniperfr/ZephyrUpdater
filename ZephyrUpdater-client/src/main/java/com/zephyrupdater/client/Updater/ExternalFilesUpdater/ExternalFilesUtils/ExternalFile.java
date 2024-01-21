package com.zephyrupdater.client.Updater.ExternalFilesUpdater.ExternalFilesUtils;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.MainClient;
import com.zephyrupdater.client.ZUCommand.ZUCList.ZUCGetFile;
import com.zephyrupdater.common.FileUtils.ExternalFilesUtils.ExternalFileCore;
import com.zephyrupdater.common.FileUtils.FileUtils;

public class ExternalFile extends ExternalFileCore {


    public ExternalFile(JsonObject extJsonFile) {
        super(extJsonFile);
    }

    private void downloadFile(){
        AppClient.sendCmdToServer(new ZUCGetFile(this.getFileName(), this.getRelativeFilePath()));
    }

    private Boolean needToBeUpdate(){
        return !FileUtils.isSameFile(this.getAbsFilePath(MainClient.clientFilePath), this.getHash(), this.getHashAlgoType());
    }

    public void checkUpdate(){
        if(needToBeUpdate()){
            downloadFile();
        }
    }
}
