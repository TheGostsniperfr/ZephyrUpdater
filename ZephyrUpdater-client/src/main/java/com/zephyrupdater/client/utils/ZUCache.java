package com.zephyrupdater.client.utils;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.ZephyrUpdater;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class ZUCache {
    private static final String ZEPHYR_CACHE_DIR_NAME = "cache";
    private static final String ZEPHYR_CACHE_FILE_NAME = "zephyrUpdaterCache.json";
    private final ZephyrUpdater zephyrUpdater;
    private JsonObject cacheObj;

    public ZUCache(ZephyrUpdater zephyrUpdater){
        this.zephyrUpdater = zephyrUpdater;

        loadCache();
    }

    public void saveCache(){
        FileUtils.saveJsonAt(this.cacheObj, this.getCacheFilePath());
    }

    public void loadCache(){
        if(!Files.exists(this.getCacheFilePath())){
            createBlancCacheFile();
            return;
        }

        this.cacheObj = FileUtils.loadJsonFromFilePath(this.getCacheFilePath());
    }

    private void createBlancCacheFile(){
        this.cacheObj = new JsonObject();

        // Set default value
        this.setGameIndex(0);
    }

    private Path getCacheFilePath(){
        Path cacheFile = zephyrUpdater.getZephyrUpdaterDir()
                            .resolve(ZEPHYR_CACHE_DIR_NAME)
                            .resolve(ZEPHYR_CACHE_FILE_NAME);

        FileUtils.createDirIfNotExist(cacheFile.getParent());
        return cacheFile;
    }

    public void setGameIndex(int index){
        this.cacheObj.addProperty(ZUCacheKeys.GAME_INDEX.getKey(), index);
    }
    public int getGameIndex(){
        return CommonUtil.getValueFromJson(ZUCacheKeys.GAME_INDEX.getKey(), this.cacheObj, Integer.class);
    }
}
