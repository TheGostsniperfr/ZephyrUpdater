package com.zephyrupdater.server.updater.request;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.FileUtils.ExternalFilesUtils.ExternalFileCore;
import com.zephyrupdater.common.FileUtils.HashUtils.HashAlgoType;
import com.zephyrupdater.server.MainServer;

import java.io.File;
import java.util.List;

public class UpdateRequest {
    private final String requestAlias;
    private List<ExternalFileCore> externalFileCores;

    public UpdateRequest(JsonObject data){
        this.requestAlias = CommonUtil.getValueFromJson(UpdateRequestKeys.REQUEST_alias.getKey(), data, String.class);
        JsonArray files = data.get(UpdateRequestKeys.FILES.getKey()).getAsJsonArray();

        if(files == null) { throw new RuntimeException("Error: Invalid jsonObject: no fields 'files'"); }

        for(JsonElement jsonElement : files){
            if(!jsonElement.isJsonObject()){
                throw new RuntimeException("Error: Invalid jsonElement: " + jsonElement);
            }

            externalFileCores.add(new ExternalFileCore(jsonElement.getAsJsonObject()));
        }
    }

    public UpdateRequest(String requestAlias, List<File> targetFiles){
        this.requestAlias = requestAlias;
        for(File targetFile : targetFiles){
            externalFileCores.add(new ExternalFileCore(
                    targetFile.toPath().toAbsolutePath(),
                    MainServer.publicDirPath,
                    HashAlgoType.SHA1));
        }
    }
    
    public JsonObject getJson(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(UpdateRequestKeys.REQUEST_alias.getKey(), this.requestAlias);

        JsonArray jsonArray = new JsonArray();
        for (ExternalFileCore externalFileCore : externalFileCores) {
            jsonArray.add(externalFileCore.getJson());
        }

        return jsonObject;
    }
}
