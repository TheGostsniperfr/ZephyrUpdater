package com.zephyrupdater.client.Updater;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zephyrupdater.client.MainClient;
import com.zephyrupdater.client.Updater.CurseForgeModUpdater.CurseForgeUtils;
import com.zephyrupdater.client.Updater.ExternalFilesUpdater.ExternalFilesUpdater;
import com.zephyrupdater.client.Updater.JavaUpdater.JavaUpdater;

import java.nio.file.Path;

public class UpdaterManager {
    private static final Path modDirPath = MainClient.gameDirPath.resolve("mods");

    public static void update(JsonArray extUpdateFilesJson, JsonObject curseModJson){
        System.out.println("Starting to update game files:");

        // Update Java
        JavaUpdater.javaUpdate();

        // Update curse forge mods
        CurseForgeUtils.updateCurseForgeMod(curseModJson, modDirPath);

        // Update external files
        ExternalFilesUpdater.checkUpdateExtFiles(extUpdateFilesJson);

        System.out.println("Successful to update game files");
    }
}
