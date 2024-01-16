package com.zephyrupdater.client.Updater.CurseForgeModUpdater;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.Updater.CurseForgeModUpdater.CurseForgeUtils.CurseForgeMod;
import com.zephyrupdater.client.Updater.CurseForgeModUpdater.CurseForgeUtils.CurseForgeUtils;

import java.nio.file.Path;
import java.util.List;

public class CurseForgeModUpdater {
    public static void updateCurseForgeMod(JsonObject curseModJson, Path modDirPath){
        List<CurseForgeMod> modList = CurseForgeUtils.getModFileList(curseModJson, modDirPath);
        CurseForgeUtils.checkUpdateModList(modList);
    }
}