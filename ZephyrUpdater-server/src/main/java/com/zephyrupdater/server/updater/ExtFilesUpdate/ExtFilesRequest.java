package com.zephyrupdater.server.updater.ExtFilesUpdate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.FileUtils.ExternalFilesUtils.ExternalFileCore;
import com.zephyrupdater.common.FileUtils.HashUtils.HashAlgoType;
import com.zephyrupdater.server.MainServer;

import java.io.File;
import java.util.List;

public class ExtFilesRequest {
    private final String requestAlias;
    private List<ExtFile> extFiles;

    public ExtFilesRequest(JsonObject data){
        this.requestAlias = CommonUtil.getValueFromJson(ExtFilesRequestKeys.REQUEST_alias.getKey(), data, String.class);
        JsonArray files = data.get(ExtFilesRequestKeys.FILES.getKey()).getAsJsonArray();

        if(files == null) { throw new RuntimeException("Error: Invalid jsonObject: no fields 'files'"); }

        for(JsonElement jsonElement : files){
            if(!jsonElement.isJsonObject()){
                throw new RuntimeException("Error: Invalid jsonElement: " + jsonElement);
            }

            extFiles.add(new ExtFile(jsonElement.getAsJsonObject()));
        }
    }

    public ExtFilesRequest(String requestAlias, List<File> targetFiles){
        this.requestAlias = requestAlias;
        for(File targetFile : targetFiles){
            System.out.println("Adding: " + targetFile.getName() + " metadata to cache.");

            extFiles.add(new ExtFile(
                    targetFile.toPath().toAbsolutePath(),
                    MainServer.publicDirPath,
                    HashAlgoType.SHA1));
        }
    }
    
    public JsonObject getJson(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ExtFilesRequestKeys.REQUEST_alias.getKey(), this.requestAlias);

        JsonArray jsonArray = new JsonArray();
        for (ExternalFileCore externalFileCore : extFiles) {
            jsonArray.add(externalFileCore.getJson());
        }

        return jsonObject;
    }

    public void checkRequest(){
        this.extFiles.removeIf(extFile -> !extFile.isValidFile());
    }
}