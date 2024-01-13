package com.zephyrupdater.server.updater;

import com.google.gson.JsonObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;


public class CheckingFiles {
    public static JsonObject getFilesJson(Path filesParentPath){
        System.out.println("Listing files to update at: " + filesParentPath.toString());

        JsonObject jsonObject = new JsonObject();
        getJsonObjFromDir(jsonObject, filesParentPath);

        return jsonObject;
    }

    private static void getJsonObjFromDir(JsonObject mainJson, Path currentDirPath){
        File directory = new File(currentDirPath.toString());
        File[] files = directory.listFiles();

        for(File file : files){
            if(file.isDirectory()){
                getJsonObjFromDir(mainJson, Paths.get(file.getPath().toString()));
                continue;
            }

            JsonObject currentObject = new JsonObject();
            try{
                byte[] fileContentBytes = Files.readAllBytes(Paths.get(file.getPath()));
                byte[] hash = MessageDigest.getInstance("SHA-256").digest(fileContentBytes);
                currentObject.addProperty("path", file.getPath());
                currentObject.addProperty("hash", hash.toString());

                mainJson.add(file.getName(), currentObject);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
