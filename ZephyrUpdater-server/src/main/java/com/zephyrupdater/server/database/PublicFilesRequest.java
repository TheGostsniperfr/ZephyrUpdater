package com.zephyrupdater.server.database;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.utils.FileUtils.ExternalFilesUtils.ExternalFileCore;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;
import com.zephyrupdater.common.utils.FileUtils.HashUtils.HashAlgoType;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class PublicFilesRequest {
    private static final String DB_NAME = "publicFilesDB.json";

    private final Path dbFilePath;
    private JsonObject db;

    public PublicFilesRequest(Path cacheDirPath){
        this.dbFilePath = cacheDirPath.resolve(DB_NAME);
        FileUtils.createDirIfNotExist(this.dbFilePath.getParent());
        this.loadDB();
    }

    public void addRequest(String requestAlias, String targetDir){
        Path targetDirPath = Paths.get(targetDir);

        if(!Files.exists(targetDirPath) || Files.isDirectory(targetDirPath)){
            System.err.println("Invalid target folder");
            return;
        }

        if(getResponseObjFromRequest(requestAlias) != null){
            System.err.println("Request Alias already exist.");
            return;
        }

        System.out.print("Creating new request:\nAlias: " + requestAlias + "\nTarget dir: " + targetDir);
        JsonObject newRequest = new JsonObject();
        List<File> files = FileUtils.getRecursiveFilesFromDirPath(targetDirPath);

        JsonArray publicFileArray = new JsonArray();
        for (File file : files){
            System.out.println("Adding: " + file.getAbsolutePath().replace(targetDir, ""));
            ExternalFileCore extFile = new ExternalFileCore(file.getAbsolutePath(), targetDir, HashAlgoType.SHA1);
            publicFileArray.add(extFile.getJson().toString());
        }

        newRequest.add("files", publicFileArray);
        newRequest.add("curseForgeMods", new JsonObject());

        this.db.add(requestAlias, newRequest);

        System.out.println("Success to add: " + requestAlias);
    }

    public void addCurseForgeModToModList(String requestAlias, String modName, String fileId, String projectId){
        JsonObject request = getResponseObjFromRequest(requestAlias);
        if(request == null){
            System.err.println("Unknown request alias.");
            return;
        }

        JsonObject modList = request.getAsJsonObject("modList");

        if(modList == null) { modList = new JsonObject(); }

        if(modList.get(modName) != null){
            System.err.println("Same mod name is already exist.");
            return;
        }

        JsonObject tmpObj = new JsonObject();
        tmpObj.addProperty("fileId", fileId);
        tmpObj.addProperty("projectId", projectId);
        modList.add(modName, tmpObj);

        this.saveDB();
        System.out.println("Success to add: " + modName);
    }

    public String getResponseFromRequest(String requestAlias){
        JsonObject responseObj = getResponseObjFromRequest(requestAlias);
        if(responseObj != null){
            return responseObj.toString();
        }

        return null;
    }

    public JsonObject getResponseObjFromRequest(String requestAlias){
        for(String request : this.db.keySet()){
            if(request.equals(requestAlias)){
                return this.db.getAsJsonObject(request);
            }
        }

        return null;
    }

    public void loadDB(){
        if(!Files.exists(this.dbFilePath)){
            FileUtils.saveJsonAt(new JsonObject(), this.dbFilePath);
        }

        this.db = FileUtils.loadJsonFromFilePath(this.dbFilePath);
    }

    public void saveDB(){
        FileUtils.saveJsonAt(this.db.getAsJsonObject(), this.dbFilePath);
    }

    public Set<String> getAllRequestAlias(){
        return this.db.keySet();
    }
}
