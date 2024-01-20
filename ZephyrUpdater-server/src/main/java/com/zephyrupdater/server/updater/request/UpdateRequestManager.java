package com.zephyrupdater.server.updater.request;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.FileUtils.FileUtils;
import com.zephyrupdater.server.MainServer;
import com.zephyrupdater.server.ZUCommand.ZUCList.ZUCGetFiles;

import java.nio.file.Files;
import java.nio.file.Path;


public class UpdateRequestManager {

    private final Path dBSavePath = MainServer.cacheDirPath.resolve("requestDB.json");
    private JsonArray db;

    private void init(){
        FileUtils.createDirIfNotExist(dBSavePath.getParent());
        if(!Files.exists(this.dBSavePath)){
            JsonObject emptyDB = new JsonObject();
            emptyDB.add(UpdateRequestKeys.DB_NAME.getKey(), new JsonArray());
            FileUtils.saveJsonAt(emptyDB, this.dBSavePath);
        }
        loadDB();
    }

    public UpdateRequestManager(){
        init();
    }

    public JsonObject getResponse(ZUCGetFiles zucGetFiles){
        for(JsonElement elem : this.db){
            if(!elem.isJsonObject()){ throw new RuntimeException("Invalid db Json."); }

            if(zucGetFiles.request.trim().equals(
                    CommonUtil.getValueFromJson(
                        UpdateRequestKeys.REQUEST_alias.getKey(),
                        elem.getAsJsonObject(),
                        String.class))) {

                return new UpdateRequest(elem.getAsJsonObject()).getJson();
            }
        }

        return null;
    }

    public void addResponseToCache(String requestAlias, Path absTargetDirPath){
        if(!absTargetDirPath.startsWith(MainServer.publicDirPath)){
            System.err.println("Path is not targeting in public folder.");
        }

        if(!Files.exists(absTargetDirPath) | !Files.isDirectory(absTargetDirPath)){
            System.err.println("Invalid target directory path.");
        }

        UpdateRequest updateRequest = new UpdateRequest(requestAlias, FileUtils.getRecursiveFilesFromDirPath(absTargetDirPath));
        this.db.add(updateRequest.getJson());
    }

    public void saveDB(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(UpdateRequestKeys.DB_NAME.getKey(), this.db);
        FileUtils.saveJsonAt(jsonObject, this.dBSavePath);
    }
    public void loadDB(){
        JsonElement tempLoad = FileUtils.loadJsonFromFilePath(dBSavePath).get(UpdateRequestKeys.DB_NAME.getKey());

        if(!tempLoad.isJsonArray()) {
            throw new RuntimeException("Invalid db file at: " + this.dBSavePath);
        }

        this.db = tempLoad.getAsJsonArray();
    }
}
