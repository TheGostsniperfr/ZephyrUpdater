package com.zephyrupdater.client.updater.CurseForgeModUpdater;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.utils.FileUtils.CurseKeys;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;
import com.zephyrupdater.common.utils.FileUtils.HashUtils.HashAlgoType;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CurseForgeMod {
    private final String fileName;
    private final String hash;
    private final long size;
    private final URL url;
    private final Path modsDirPath;
    private final Path modFilePath;

    public CurseForgeMod(JsonObject jsonObject, Path modDirPath) {
        try {
            JsonObject dataObj = jsonObject.get(CurseKeys.DATA.getKey()).getAsJsonObject();
            this.fileName = CommonUtil.getValueFromJson(CurseKeys.FILE_NAME.getKey(), dataObj, String.class);
            this.size = CommonUtil.getValueFromJson(CurseKeys.SIZE.getKey(), dataObj, Long.class);
            this.url = new URL(CommonUtil.getValueFromJson(CurseKeys.URL.getKey(), dataObj, String.class));

            JsonArray hashesArray = dataObj.getAsJsonArray(CurseKeys.HASH.getKey());
            JsonObject firstHashObj = hashesArray.get(0).getAsJsonObject();
            this.hash = CommonUtil.getValueFromJson(CurseKeys.VALUE.getKey(), firstHashObj, String.class);

            this.modsDirPath = modDirPath;
            this.modFilePath = this.modsDirPath.resolve(this.fileName);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void downloadMod(){
        InputStream inputStream;
        try {
            if(!Files.exists(this.modsDirPath)){
                Files.createDirectories(modsDirPath);
            }

            inputStream = this.url.openStream();
            if (!Files.exists(this.modFilePath)) {
                Files.copy(inputStream, this.modFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean needToBeUpdate(){
        return !FileUtils.isSameFile(this.modFilePath, this.hash, HashAlgoType.SHA1);
    }

    public void checkUpdate(){
        if(needToBeUpdate()){
            System.out.println("Downloading: " + this.fileName);
            downloadMod();
        }
    }

    public URL getUrl() {
        return url;
    }

    public long getSize() {
        return size;
    }

    public String getFileName() {
        return fileName;
    }

    public String getHash() {
        return hash;
    }

    public Path getModFilePath() {
        return modFilePath;
    }

    public Path getModsDirPath() {
        return modsDirPath;
    }
}
