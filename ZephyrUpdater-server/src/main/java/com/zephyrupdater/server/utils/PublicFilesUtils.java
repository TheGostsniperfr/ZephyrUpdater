package com.zephyrupdater.server.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.utils.FileUtils.ExternalFilesUtils.ExternalFileCore;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;
import com.zephyrupdater.common.utils.FileUtils.HashUtils.HashAlgoType;
import com.zephyrupdater.server.database.PublicFilesDB;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PublicFilesUtils {
    public static void addCurseForgeModToModList(PublicFilesDB publicFilesDB, String requestAlias, String modName, String fileId, String projectId){
        JsonObject request = PublicFilesUtils.getResponseObjFromRequest(publicFilesDB, requestAlias);
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

        publicFilesDB.saveDB();
        System.out.println("Success to add: " + modName);
    }

    public static void addRequest(PublicFilesDB publicFilesDB, String requestAlias, String targetDir){
        Path targetDirPath = Paths.get(targetDir);

        if(!Files.exists(targetDirPath) || Files.isDirectory(targetDirPath)){
            System.err.println("Invalid target folder");
            return;
        }

        if(getResponseObjFromRequest(publicFilesDB, requestAlias) != null){
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

        newRequest.addProperty("isPublic", false);
        newRequest.add("files", publicFileArray);
        newRequest.add("curseForgeMods", new JsonObject());

        publicFilesDB.getDB().add(requestAlias, newRequest);

        System.out.println("Success to add: " + requestAlias);
    }

    public static String getResponseFromRequest(PublicFilesDB publicFilesDB, String requestAlias){
        JsonObject responseObj = getResponseObjFromRequest(publicFilesDB, requestAlias);
        if(responseObj != null){
            return responseObj.toString();
        }

        return null;
    }

    public static JsonObject getResponseObjFromRequest(PublicFilesDB publicFilesDB, String requestName){
        for(String requestAlias : publicFilesDB.getAllRequestAlias()){
            if(requestAlias.equals(requestName)){
                return publicFilesDB.getDB().getAsJsonObject(requestAlias);
            }
        }

        return null;
    }
}
