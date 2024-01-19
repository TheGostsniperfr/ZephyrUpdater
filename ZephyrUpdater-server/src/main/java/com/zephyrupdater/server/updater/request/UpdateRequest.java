package com.zephyrupdater.server.updater.request;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.FileUtils.HashUtils.HASH_ALGO_TYPE;
import com.zephyrupdater.common.FileUtils.HashUtils.HashAlgo;
import com.zephyrupdater.server.MainServer;

import java.nio.file.Files;
import java.nio.file.Path;

public class UpdateRequest {
    private final String requestName;
    private final Path requestRelativePath;
    private String hash;
    private HASH_ALGO_TYPE hashAlgoType;
    private Path requestAbsPath;


    public UpdateRequest(String requestName, Path requestRelativePath){
        this.requestName = requestName;
        this.requestRelativePath = requestRelativePath;
        this.requestAbsPath = MainServer.publicDirPath.resolve(this.requestRelativePath);
        
        if(!Files.exists(this.requestAbsPath)){
            throw new IllegalArgumentException(this.requestAbsPath.toString() + "does not exist.");
        }
        
        this.hash = HashAlgo.getHashFromFilePath(this.requestAbsPath, HASH_ALGO_TYPE.SHA1);
        this.hashAlgoType =  HASH_ALGO_TYPE.SHA1;
    }


    
    public JsonObject getJson(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(UpdateRequestKeys.REQUEST_NAME.getKey(), this.requestName);
        jsonObject.addProperty(UpdateRequestKeys.TARGET_RELATIVE_PATH.getKey(), this.requestRelativePath.toString());
        jsonObject.addProperty(UpdateRequestKeys.HASH.getKey(), this.hash);
        jsonObject.addProperty(UpdateRequestKeys.HASH_ALGO.getKey(), this.hashAlgoType.getKey());

        return jsonObject;
    }

    public Path getRequestRelativePath() {
        return requestRelativePath;
    }

    public String getRequestName() {
        return requestName;
    }

    public HASH_ALGO_TYPE getHashAlgoType() {
        return hashAlgoType;
    }

    public String getHash() {
        return hash;
    }

    public Path getRequestAbsPath() {
        return requestAbsPath;
    }
}
