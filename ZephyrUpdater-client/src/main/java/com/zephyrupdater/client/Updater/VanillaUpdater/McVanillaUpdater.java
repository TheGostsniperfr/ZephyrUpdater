package com.zephyrupdater.client.Updater.VanillaUpdater;

import com.zephyrupdater.client.Updater.McUpdaterManager;
import com.zephyrupdater.common.utils.FileUtils.DownloadableFile;

public class McVanillaUpdater {
    public static void checkUpdate(McUpdaterManager mcUpdaterManager){
        McManifestParser mcManifestParser = new McManifestParser(mcVersion);
        mcManifestParser.parseFiles();
        mcManifestParser.getDownloadableFiles().forEach(DownloadableFile::checkUpdate);
    }
}
