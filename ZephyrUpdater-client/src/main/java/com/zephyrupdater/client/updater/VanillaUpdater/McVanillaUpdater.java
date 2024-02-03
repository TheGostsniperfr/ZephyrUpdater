package com.zephyrupdater.client.updater.VanillaUpdater;

import com.zephyrupdater.client.games.gameList.McGameManager;
import com.zephyrupdater.client.updater.UpdateProgressSteps;
import com.zephyrupdater.common.utils.FileUtils.DownloadableFile;

import java.util.List;

public class McVanillaUpdater {
    public static void checkUpdate(McGameManager mcUpdaterManager){
        McManifestParser mcManifestParser = new McManifestParser(mcUpdaterManager);

        mcUpdaterManager.setUpdateProgressSteps(UpdateProgressSteps.FETCH_MCV);
        mcManifestParser.parseFiles();

        mcUpdaterManager.setUpdateProgressSteps(UpdateProgressSteps.DL_LIBS);
        mcManifestParser.getDownloadableLibs().forEach(DownloadableFile::checkUpdate);

        mcUpdaterManager.setUpdateProgressSteps(UpdateProgressSteps.DL_NATIVES);
        mcManifestParser.getDownloadableNatives().forEach(DownloadableFile::checkUpdate);

        mcUpdaterManager.setUpdateProgressSteps(UpdateProgressSteps.EXTRACT_NATIVES);
        extractNatives(mcManifestParser.getDownloadableNatives());

        mcUpdaterManager.setUpdateProgressSteps(UpdateProgressSteps.DL_MC_CLIENT);
        mcManifestParser.getClientFile().checkUpdate();
    }


    private static void extractNatives(List<DownloadableFile> natives){
        //TODO
    }
}
