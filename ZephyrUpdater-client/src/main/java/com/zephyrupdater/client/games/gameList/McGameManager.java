package com.zephyrupdater.client.games.gameList;

import com.zephyrupdater.client.ZephyrUpdater;
import com.zephyrupdater.client.games.GameManagerCore;
import com.zephyrupdater.client.games.utils.Updater.JavaUpdater.JavaUpdater;
import com.zephyrupdater.client.games.utils.Updater.UpdateProgressSteps;
import com.zephyrupdater.client.games.utils.Updater.VanillaUpdater.McVanillaUpdater;

import java.nio.file.Path;

public class McGameManager extends GameManagerCore {
    private final String mcVersion;
    private final String javaVersion;

    public McGameManager(String gameDirName, String mcVersion, String javaVersion, ZephyrUpdater zephyrUpdater) {
        super(gameDirName, zephyrUpdater);
        this.mcVersion = mcVersion;
        this.javaVersion = javaVersion;
    }

    @Override
    public void update(){
        if(this.getUpdateProgressSteps() != UpdateProgressSteps.IDLE) {
            System.out.println("Updater is already working.");
            return;
        }

        System.out.println("Starting to update game files:");

        // Update Java
        new JavaUpdater(this, this.getJavaVersion());

        // Update Minecraft vanilla files
        McVanillaUpdater.checkUpdate(this);

        // Update curse forge mods
        //CurseForgeUtils.updateCurseForgeMod(curseModJson, this.getModDirPath());

        // Update external files
        //ExternalFilesUpdater.checkUpdateExtFiles(extUpdateFilesJson, this.getGameDir());


        System.out.println("Successful to update game files");
        this.setUpdateProgressSteps(UpdateProgressSteps.IDLE);
    }

    @Override
    public void launchGame() {

    }

    public String getMcVersion() {
        return mcVersion;
    }
    public Path getModDirPath(){
        return this.getGameDir().resolve("mods");
    }

    public String getJavaVersion() {
        return javaVersion;
    }
}
