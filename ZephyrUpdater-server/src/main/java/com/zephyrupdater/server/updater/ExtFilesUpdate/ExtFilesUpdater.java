package com.zephyrupdater.server.updater.ExtFilesUpdate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.FileUtils.FileUtils;
import com.zephyrupdater.server.MainServer;
import com.zephyrupdater.server.ZUCommand.ZUCList.ZUCUpdate;

import java.nio.file.Files;
import java.nio.file.Path;


public class ExtFilesUpdater {

    private final Path dBSavePath = MainServer.cacheDirPath.resolve("requestDB.json");
    private JsonArray db;

    private void init(){
        FileUtils.createDirIfNotExist(dBSavePath.getParent());
        if(!Files.exists(this.dBSavePath)){
            JsonObject emptyDB = new JsonObject();
            emptyDB.add(ExtFilesRequestKeys.DB_NAME.getKey(), new JsonArray());
            FileUtils.saveJsonAt(emptyDB, this.dBSavePath);
        }
        loadDB();
    }

    public ExtFilesUpdater(){
        init();
    }

    public JsonObject getResponse(ZUCUpdate zucUpdate){
        for(JsonElement elem : this.db){
            if(!elem.isJsonObject()){ throw new RuntimeException("Invalid db Json."); }

            if(zucUpdate.request.trim().equals(
                    CommonUtil.getValueFromJson(
                        ExtFilesRequestKeys.REQUEST_alias.getKey(),
                        elem.getAsJsonObject(),
                        String.class))) {

                return new ExtFilesRequest(elem.getAsJsonObject()).getJson();
            }
        }

        return null;
    }

    public void addRequest(String requestAlias, Path absTargetDirPath){
        if(!absTargetDirPath.startsWith(MainServer.publicDirPath)){
            System.err.println("Path is not targeting in public folder.");
        }

        if(!Files.exists(absTargetDirPath) | !Files.isDirectory(absTargetDirPath)){
            System.err.println("Invalid target directory path.");
        }

        System.out.println("Adding new request, target directory: "  + absTargetDirPath);
        ExtFilesRequest extFilesRequest = new ExtFilesRequest(requestAlias, FileUtils.getRecursiveFilesFromDirPath(absTargetDirPath));
        this.db.add(extFilesRequest.getJson());
        saveDB();
    }

    /**
     * Check if files in db still existing
     * Check if the file hash has changed
     */
    public void checkDB(){
        for(JsonElement elem : this.db){
            ExtFilesRequest request = new ExtFilesRequest(elem.getAsJsonObject());
            request.checkRequest();
        }

        saveDB();
    }

    public void saveDB(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(ExtFilesRequestKeys.DB_NAME.getKey(), this.db);
        FileUtils.saveJsonAt(jsonObject, this.dBSavePath);
    }
    public void loadDB(){
        JsonElement tempLoad = FileUtils.loadJsonFromFilePath(dBSavePath).get(ExtFilesRequestKeys.DB_NAME.getKey());

        if(!tempLoad.isJsonArray()) {
            throw new RuntimeException("Invalid db file at: " + this.dBSavePath);
        }

        this.db = tempLoad.getAsJsonArray();
    }

    public JsonArray getDb() {
        return db;
    }
}
