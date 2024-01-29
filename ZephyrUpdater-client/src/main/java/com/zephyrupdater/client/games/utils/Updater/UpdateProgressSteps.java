package com.zephyrupdater.client.games.utils.Updater;

public enum UpdateProgressSteps {
    IDLE("Idle"),
    DL_JAVA("Downloading java"),
    INSTALL_JAVA("Installing java"),
    FETCH_MCV("Fetching minecraft vanilla files"),
    DL_LIBS("Downloading libs"),
    DL_NATIVES("Downloading natives"),
    EXTRACT_NATIVES("Extracting natives"),
    DL_MC_CLIENT("Downloading minecraft client"),
    DL_MODS("Downloading mods"),
    DL_EXT_FILES("Downloading external files");
    private final String stepName;

    UpdateProgressSteps(String stepName) {
        this.stepName = stepName;
    }

    public String getStepName() {
        return this.stepName;
    }
}
