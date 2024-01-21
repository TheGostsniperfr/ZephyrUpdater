package com.zephyrupdater.server.updater.ExtFilesUpdate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.FileUtils.FileUtils;
import com.zephyrupdater.common.PromptUtils;
import com.zephyrupdater.server.MainServer;
import com.zephyrupdater.server.ZUCommand.ZUCList.ZUCUpdate;

import java.nio.file.Files;
import java.nio.file.Path;


public class ExtFilesUpdater {

    private final Path dBSavePath = MainServer.cacheDirPath.resolve("requestDB.json");
    private JsonObject db;

    private void init(){
        FileUtils.createDirIfNotExist(dBSavePath.getParent());
        if(!Files.exists(this.dBSavePath)){
            this.db = new JsonObject();
            this.saveDB();
        }else{
            loadDB();
        }
    }

    public ExtFilesUpdater(){
        init();
    }

    public JsonArray getResponse(ZUCUpdate zucUpdate){
        for(String requestName : this.db.keySet()){
            JsonObject request = this.db.get(requestName).getAsJsonObject();
            if(zucUpdate.request.trim().equals(requestName)) {
                return new ExtFilesRequest(request).getExtFilesAsJsonArray();
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
        if(this.isRequestAliasExist(requestAlias)){
            if(!PromptUtils.getUserChoice("This request alias is already use, do you want to overwrite ?")){
                return;
            }
        }

        System.out.println("Adding new request, target directory: "  + absTargetDirPath);
        ExtFilesRequest extFilesRequest = new ExtFilesRequest(requestAlias, FileUtils.getRecursiveFilesFromDirPath(absTargetDirPath));
        this.db.add(requestAlias, extFilesRequest.getExtFilesAsJsonArray());
        saveDB();
    }

    /**
     * Check if files in db still existing
     * Check if the file hash has changed
     */
    public void checkDB(){
        for(String requestName : this.db.keySet()){
            JsonElement requestElem = this.db.get(requestName);

            if(!requestElem.isJsonObject()){
                throw new RuntimeException("Invalid database json.");
            }

            new ExtFilesRequest(requestElem.getAsJsonObject()).checkRequest();
        }

        saveDB();
    }

    private Boolean isRequestAliasExist(String requestAlias){
        for(String alias : this.db.keySet()){
            if(alias.equals(requestAlias)){
                return true;
            }
        }

        return false;
    }

    public void saveDB(){
        System.out.println("Saving ext files db.");
        FileUtils.saveJsonAt(this.db, this.dBSavePath);
    }
    public void loadDB(){
        JsonElement tempLoad = FileUtils.loadJsonFromFilePath(dBSavePath);

        if(!tempLoad.isJsonObject()) {
            throw new RuntimeException("Invalid db file at: " + this.dBSavePath);
        }

        this.db = tempLoad.getAsJsonObject();
    }

    public JsonObject getDb() {
        return this.db;
    }
}
