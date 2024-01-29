package com.zephyrupdater.client.games.utils.Updater.ExternalFilesUpdater;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.zephyrupdater.client.games.utils.Updater.ExternalFilesUpdater.ExternalFilesUtils.ExternalFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ExternalFilesUpdater {
    public static void checkUpdateExtFiles(JsonArray extFilesJson, Path gameDir){
        for (ExternalFile externalFile : parseExtFilesJson(extFilesJson)) {
            externalFile.checkUpdate(gameDir);
        }
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
