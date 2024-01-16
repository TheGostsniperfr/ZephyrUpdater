package com.zephyrupdater.client.Updater;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.MainClient;
import com.zephyrupdater.client.Updater.JavaUpdater.JavaUpdater;
import com.zephyrupdater.client.Updater.ModUpdater.ModUpdater;

import java.nio.file.Path;

public class UpdaterManager {
    private static final Path modDirPath = MainClient.clientFilePath.resolve("mods");

    public static void update(JsonObject extUpdateFilesJson, JsonObject curseModJson){
        // Update Java
        JavaUpdater.javaUpdate();

        // Update mods
        ModUpdater modUpdater = new ModUpdater(modDirPath, curseModJson, extUpdateFilesJson);
        modUpdater.update();
    }
}
