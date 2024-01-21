package com.zephyrupdater.common.FileUtils.ExternalFilesUtils;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.FileUtils.FileUtils;
import com.zephyrupdater.common.FileUtils.HashUtils.HashAlgoType;
import com.zephyrupdater.common.FileUtils.HashUtils.HashAlgo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExternalFileCore {
    private String hash;
    private final HashAlgoType hashAlgoType;
    private final Path relativeFilePath;

    public ExternalFileCore(JsonObject extJsonFile){
        this.relativeFilePath =
                Paths.get(CommonUtil.getValueFromJson(ExtFileKeys.RELATIVE_FILE_PATH.getKey(), extJsonFile, String.class));
        this.hash = CommonUtil.getValueFromJson(ExtFileKeys.HASH.getKey(), extJsonFile, String.class);
        this.hashAlgoType =
                HashAlgo.getAlgoTypeByName(CommonUtil.getValueFromJson(ExtFileKeys.HASH_ALGO.getKey(), extJsonFile, String.class));
    }

    public ExternalFileCore(Path absPathToFile, Path basePath, HashAlgoType hashAlgo){
        if(!Files.exists(absPathToFile)){
            throw new IllegalArgumentException("No file at: " + absPathToFile);
        }
        this.relativeFilePath = FileUtils.getRelativePathFromAbs(absPathToFile, basePath);
        this.hash = HashAlgo.getHashFromFilePath(absPathToFile, hashAlgo);
        this.hashAlgoType = hashAlgo;
    }

    public String getHash() {
        return hash;
    }
    public void setHash(String hash){
        this.hash = hash;
    }
    public String getFileName() { return this.relativeFilePath.getFileName().toString(); }
    public Path getRelativeFilePath() {
        return this.relativeFilePath;
    }
    public Path getAbsFilePath(Path basePath) { return basePath.resolve(this.relativeFilePath); }
    public HashAlgoType getHashAlgoType() {
        return hashAlgoType;
    }
    public JsonObject getJson(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ExtFileKeys.RELATIVE_FILE_PATH.getKey(), this.relativeFilePath.toString());
        jsonObject.addProperty(ExtFileKeys.HASH.getKey(), this.hash);
        jsonObject.addProperty(ExtFileKeys.HASH_ALGO.getKey(), this.hashAlgoType.getKey());

        return jsonObject;
    }
}
