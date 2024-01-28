package com.zephyrupdater.client.Updater.VanillaUpdater;

import com.zephyrupdater.common.utils.FileUtils.DownloadableFile;

public class McVanillaUpdater {
    public static void checkUpdate(String mcVersion){
        McManifestParser mcManifestParser = new McManifestParser(mcVersion);
        mcManifestParser.parseFiles();
        mcManifestParser.getDownloadableFiles().forEach(DownloadableFile::checkUpdate);
    }
}
