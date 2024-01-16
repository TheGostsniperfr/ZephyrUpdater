package com.zephyrupdater.client.Updater;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.MainClient;
import com.zephyrupdater.client.Updater.CurseForgeModUpdater.CurseForgeModUpdater;
import com.zephyrupdater.client.Updater.JavaUpdater.JavaUpdater;

import java.nio.file.Path;

public class UpdaterManager {
    private static final Path modDirPath = MainClient.clientFilePath.resolve("mods");

    public static void update(JsonObject extUpdateFilesJson, JsonObject curseModJson){
        // Update Java
        JavaUpdater.javaUpdate();

        // Update curse forge mods
        CurseForgeModUpdater.updateCurseForgeMod(curseModJson, modDirPath);

        // Update external files
        // TODO
    }



}
