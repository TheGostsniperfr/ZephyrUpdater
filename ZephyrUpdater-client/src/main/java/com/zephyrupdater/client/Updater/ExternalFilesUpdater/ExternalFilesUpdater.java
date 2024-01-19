package com.zephyrupdater.client.Updater.ExternalFilesUpdater;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.client.Updater.ExternalFilesUpdater.ExternalFilesUtils.ExternalFile;

import java.util.ArrayList;
import java.util.List;

public class ExternalFilesUpdater {
    public static void checkUpdateExtFiles(JsonObject extFilesJson){
        parseExtFilesJson(extFilesJson).forEach(ExternalFile::checkUpdate);
    }

    private static List<ExternalFile> parseExtFilesJson(JsonObject extFilesJson){
        List<ExternalFile> externalFiles = new ArrayList<>();
        for(String fileName : extFilesJson.keySet()){
            JsonElement jsonElement = extFilesJson.get(fileName);

            if(!jsonElement.isJsonObject()){
                System.out.println(jsonElement + " is not a JsonObject");
                continue;
            }

            externalFiles.add(new ExternalFile(jsonElement.getAsJsonObject()));
        }

        return externalFiles;
    }
}
