package com.zephyrupdater.client.Updater.ModUpdater;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.Updater.ModUpdater.CurseForgeUtils.CurseForgeMod;
import com.zephyrupdater.client.Updater.ModUpdater.CurseForgeUtils.CurseForgeUtils;

import java.nio.file.Path;
import java.util.List;

public class ModUpdater {
    private final Path modDirPath;
    private final JsonObject curseModJson;
    private final JsonObject extModJson;

    public ModUpdater(Path modDirPath, JsonObject curseModJson, JsonObject extModJson){
        this.modDirPath = modDirPath;
        this.curseModJson = curseModJson;
        this.extModJson = extModJson;
    }

    public void update(){
        updateCurseMod();
        updateExtMod();
    }

    private void updateCurseMod(){
        List<CurseForgeMod> modList = CurseForgeUtils.getModFileList(this.curseModJson, this.modDirPath);
        CurseForgeUtils.checkUpdateModList(modList);
    }

    private void updateExtMod(){
        // TODO
    }
}