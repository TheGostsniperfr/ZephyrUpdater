package com.zephyrupdater.client.Updater;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zephyrupdater.client.MainClient;
import com.zephyrupdater.client.Updater.CurseForgeModUpdater.CurseForgeUtils;
import com.zephyrupdater.client.Updater.ExternalFilesUpdater.ExternalFilesUpdater;
import com.zephyrupdater.client.Updater.JavaUpdater.JavaUpdater;
import com.zephyrupdater.client.Updater.VanillaUpdater.McVanillaUpdater;
import com.zephyrupdater.common.OsSpec;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;

import java.nio.file.Path;

public class McUpdaterManager {
    private static final Path modDirPath = MainClient.gameDirPath.resolve("mods");
    private UpdateProgressSteps updateProgressSteps;
    private final String gameDirName;
    private final Path gameDir;
    private final String mcVersion;

    private final OsSpec osSpec;

    public McUpdaterManager(String gameDirName, String mcVersion, OsSpec osSpec){
        this.gameDirName = gameDirName;
        this.mcVersion = mcVersion;
        this.osSpec = osSpec;
        this.gameDir = osSpec.getAppdataPath().resolve(gameDirName);
        FileUtils.createDirIfNotExist(this.gameDir);
        updateProgressSteps = UpdateProgressSteps.IDLE;
    }

    public void update (JsonArray extUpdateFilesJson, JsonObject curseModJson){
        if(updateProgressSteps != UpdateProgressSteps.IDLE) {
            System.out.println("Updater is already working.");
            return;
        }

        System.out.println("Starting to update game files:");

        // Update Java
        JavaUpdater.javaUpdate();

        // Update Minecraft vanilla files
        McVanillaUpdater.checkUpdate(this);

        // Update curse forge mods
        CurseForgeUtils.updateCurseForgeMod(curseModJson, modDirPath);

        // Update external files
        ExternalFilesUpdater.checkUpdateExtFiles(extUpdateFilesJson);


        System.out.println("Successful to update game files");
        this.updateProgressSteps = UpdateProgressSteps.IDLE;
    }

    public UpdateProgressSteps getUpdateProgressSteps() {
        return updateProgressSteps;
    }

    public String getGameDirName() {
        return gameDirName;
    }

    public Path getGameDir() {
        return gameDir;
    }

    public OsSpec getOsSpec() {
        return osSpec;
    }

    public String getMcVersion() {
        return mcVersion;
    }
}
