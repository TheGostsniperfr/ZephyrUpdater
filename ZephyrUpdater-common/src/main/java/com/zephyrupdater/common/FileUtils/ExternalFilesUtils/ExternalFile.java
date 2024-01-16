package com.zephyrupdater.common.FileUtils.ExternalFilesUtils;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ExternalFile {
    private final String hash;
    private final Path filePath;

    public ExternalFile(Path filePath, String hash){
        this.filePath = filePath;
        this.hash = hash;
    }

    public ExternalFile(JsonObject extJsonFile){
        this.filePath = Paths.get(CommonUtil.getValueFromJson(EXTERNAL_FILE_KEY.FILE_NAME.getKey(), extJsonFile, String.class));
        this.hash = CommonUtil.getValueFromJson(EXTERNAL_FILE_KEY.HASH.getKey(), extJsonFile, String.class);
    }

    public void update(){
        // TODO
    }

    public String getHash() {
        return hash;
    }

    public Path getFilePath() {
        return filePath;
    }
}
