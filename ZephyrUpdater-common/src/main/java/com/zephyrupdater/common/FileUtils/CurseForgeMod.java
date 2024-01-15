package com.zephyrupdater.common.FileUtils;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;

import java.net.URL;

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
            JsonObject hashObj = dataObj.get(CURSE_KEY.HASH.getKey()).getAsJsonObject();
            this.hash = CommonUtil.getValueFromJson(CURSE_KEY.VALUE.getKey(), hashObj, String.class);
        }
        catch (Exception e){
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
