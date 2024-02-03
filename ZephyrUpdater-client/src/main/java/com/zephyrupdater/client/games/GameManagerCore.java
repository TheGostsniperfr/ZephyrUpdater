package com.zephyrupdater.client.games;

import com.zephyrupdater.client.ZephyrUpdater;
import com.zephyrupdater.client.updater.UpdateProgressSteps;
import com.zephyrupdater.common.OsSpec;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;

import java.nio.file.Path;

public abstract class GameManagerCore {
    private UpdateProgressSteps updateProgressSteps;
    private final String gameDirName;
    private final Path gameDir;
    private final OsSpec osSpec;
    public GameManagerCore(String gameDirName, ZephyrUpdater zephyrUpdater){
        this.gameDirName = gameDirName;
        this.osSpec = zephyrUpdater.getOsSpec();
        this.gameDir = this.osSpec.getAppdataPath().resolve(this.gameDirName);
        FileUtils.createDirIfNotExist(this.gameDir);
        updateProgressSteps = UpdateProgressSteps.IDLE;
    }

    public abstract void update();

    public abstract void launchGame();

    public void setUpdateProgressSteps(UpdateProgressSteps updateProgressSteps) {
        System.out.println(updateProgressSteps.getStepName());
        this.updateProgressSteps = updateProgressSteps;
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
}
