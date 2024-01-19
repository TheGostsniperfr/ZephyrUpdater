package com.zephyrupdater.server.updater.request;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.FileUtils.FileUtils;
import com.zephyrupdater.server.MainServer;
import com.zephyrupdater.server.ZUCommand.ZUCList.ZUCGetFiles;

import java.io.File;
import java.nio.file.Path;


public class UpdateRequestManager {

    private final Path dBPath = MainServer.cacheDirPath.resolve("requestDB.json");
    private JsonObject db;

    private void init(){
        FileUtils.createDirIfNotExist(dBPath.getParent());
        loadDB();

    }

    public UpdateRequestManager(){
        init();
    }


    private void loadDB(){
        this.db = FileUtils.loadJsonFromFilePath(dBPath);
    }



    public JsonElement getResponse(ZUCGetFiles zucGetFiles){
        for()
    }

    public static void addResponseToCache(){

    }
}
