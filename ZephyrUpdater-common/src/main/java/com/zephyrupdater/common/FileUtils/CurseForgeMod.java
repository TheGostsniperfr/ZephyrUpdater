package com.zephyrupdater.common.FileUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;

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


    public CurseForgeMod(JsonObject jsonObject) {
        try {
            JsonObject dataObj = jsonObject.get(CURSE_KEY.DATA.getKey()).getAsJsonObject();
            this.fileName = CommonUtil.getValueFromJson(CURSE_KEY.FILE_NAME.getKey(), dataObj, String.class);
            this.size = CommonUtil.getValueFromJson(CURSE_KEY.SIZE.getKey(), dataObj, Long.class);
            this.url = new URL(CommonUtil.getValueFromJson(CURSE_KEY.URL.getKey(), dataObj, String.class));

            JsonArray hashesArray = dataObj.getAsJsonArray(CURSE_KEY.HASH.getKey());
            JsonObject firstHashObj = hashesArray.get(0).getAsJsonObject();
            this.hash = CommonUtil.getValueFromJson(CURSE_KEY.VALUE.getKey(), firstHashObj, String.class);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void downloadMod(Path pathToDownload){
        InputStream inputStream;
        try {
            if(!Files.exists(pathToDownload)){
                Files.createDirectories(pathToDownload);
            }

            inputStream = this.url.openStream();
            Path destinationFile = pathToDownload.resolve(this.fileName);
            if (!Files.exists(destinationFile)) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
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
}
