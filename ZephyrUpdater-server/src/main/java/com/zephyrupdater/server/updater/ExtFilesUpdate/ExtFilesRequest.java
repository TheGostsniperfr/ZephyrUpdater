package com.zephyrupdater.server.updater.ExtFilesUpdate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.FileUtils.HashUtils.HashAlgoType;
import com.zephyrupdater.server.MainServer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExtFilesRequest {
    private List<ExtFile> extFiles;

    public ExtFilesRequest(JsonObject data){
        JsonArray files = data.get(ExtFilesRequestKeys.FILES.getKey()).getAsJsonArray();

        if(files == null) { throw new RuntimeException("Error: Invalid jsonObject: no fields 'files'"); }

        this.extFiles = new ArrayList<>();

        for(JsonElement jsonElement : files){
            if(!jsonElement.isJsonObject()){
                throw new RuntimeException("Error: Invalid jsonElement: " + jsonElement);
            }

            extFiles.add(new ExtFile(jsonElement.getAsJsonObject()));
        }
    }

    public ExtFilesRequest(String requestAlias, List<File> targetFiles){
        this.extFiles = new ArrayList<>();
        for(File targetFile : targetFiles){
            System.out.println("Adding: " + targetFile.getName() + " metadata to cache.");

            System.out.println("abs path: " + targetFile.toPath().toAbsolutePath());
            ExtFile extFile = new ExtFile(
                    targetFile.toPath().toAbsolutePath(),
                    MainServer.publicDirPath,
                    HashAlgoType.SHA1);

            System.out.println("valid file: " + extFile.isValidFile());

            extFiles.add(extFile);
        }
    }

    public JsonArray getExtFilesAsJsonArray(){
        JsonArray jsonArray = new JsonArray();

        for(ExtFile extFile : this.extFiles){
            jsonArray.add(extFile.getJson());
        }

        return jsonArray;
    }

    public void checkRequest(){
        this.extFiles.removeIf(extFile -> !extFile.isValidFile());
    }
}