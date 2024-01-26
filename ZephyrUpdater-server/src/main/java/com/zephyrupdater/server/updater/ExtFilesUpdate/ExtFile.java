package com.zephyrupdater.server.updater.ExtFilesUpdate;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.utils.FileUtils.ExternalFilesUtils.ExternalFileCore;
import com.zephyrupdater.common.utils.FileUtils.HashUtils.HashAlgo;
import com.zephyrupdater.common.utils.FileUtils.HashUtils.HashAlgoType;
import com.zephyrupdater.server.MainServer;

import java.nio.file.Files;
import java.nio.file.Path;

public class ExtFile extends ExternalFileCore {
    public ExtFile(JsonObject extJsonFile) {
        super(extJsonFile);
    }

    public ExtFile(Path absPathToFile, Path basePath, HashAlgoType hashAlgo){
        super(absPathToFile, basePath, hashAlgo);
    }

    public Boolean needToBeUpdate(){
        String hash = HashAlgo.getHashFromFilePath(this.getAbsFilePath(MainServer.publicDirPath), this.getHashAlgoType());
        if(!hash.equals(this.getHash())){
            this.setHash(hash);
            return true;
        }

        return false;
    }

    public Boolean isValidFile(){
        Path absPath = this.getAbsFilePath(MainServer.publicDirPath);
        return Files.exists(absPath) && !Files.isDirectory(absPath);
    }
}
