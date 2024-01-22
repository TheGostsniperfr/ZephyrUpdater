package com.zephyrupdater.client.Updater.ExternalFilesUpdater;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.zephyrupdater.client.Updater.ExternalFilesUpdater.ExternalFilesUtils.ExternalFile;

import java.util.ArrayList;
import java.util.List;

public class ExternalFilesUpdater {
    public static void checkUpdateExtFiles(JsonArray extFilesJson){
        parseExtFilesJson(extFilesJson).forEach(ExternalFile::checkUpdate);
    }

    private static List<ExternalFile> parseExtFilesJson(JsonArray extFilesJson){
        List<ExternalFile> externalFiles = new ArrayList<>();
        for(JsonElement jsonElement: extFilesJson){
            if(!jsonElement.isJsonObject()){
                System.out.println(jsonElement + " is not a JsonObject");
                continue;
            }

            externalFiles.add(new ExternalFile(jsonElement.getAsJsonObject()));
        }

        return externalFiles;
    }
}
