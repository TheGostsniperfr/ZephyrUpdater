package com.zephyrupdater.client.Updater.ExternalFilesUpdater.ExternalFilesUtils;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.ZUCommand.ZUCList.ZUCGetFile;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.FileUtils.HashUtils.HASH_ALGO_TYPE;
import com.zephyrupdater.common.FileUtils.HashUtils.HashAlgo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExternalFile {
    private final String hash;
    private final HASH_ALGO_TYPE hashAlgoType;
    private final Path relativeFilePath;
    private final String fileName;

    public ExternalFile(JsonObject extJsonFile){
        this.relativeFilePath =
                Paths.get(CommonUtil.getValueFromJson(EXTERNAL_FILE_KEY.FILE_NAME.getKey(), extJsonFile, String.class));
        this.hash = CommonUtil.getValueFromJson(EXTERNAL_FILE_KEY.HASH.getKey(), extJsonFile, String.class);
        this.hashAlgoType =
                HashAlgo.getAlgoTypeByName(CommonUtil.getValueFromJson(EXTERNAL_FILE_KEY.HASH_ALGO.getKey(), extJsonFile, String.class));
        this.fileName = this.relativeFilePath.getFileName().toString();
    }

    private void downloadFile(){
        AppClient.sendCmdToServer(new ZUCGetFile(this.fileName, this.relativeFilePath));
    }

    private Boolean needToBeUpdate(){
        if(!Files.exists(this.relativeFilePath)){
            return true;
        }

        return !HashAlgo.getHashFromFilePath(this.relativeFilePath, HASH_ALGO_TYPE.SHA1).equals(this.hash);
    }

    public void checkUpdate(){
        if(needToBeUpdate()){
            System.out.println("Downloading: " + this.fileName);
            downloadFile();
        }
    }

    public String getHash() {
        return hash;
    }

    public Path getRelativeFilePath() {
        return relativeFilePath;
    }

    public HASH_ALGO_TYPE getHashAlgoType() {
        return hashAlgoType;
    }
}
