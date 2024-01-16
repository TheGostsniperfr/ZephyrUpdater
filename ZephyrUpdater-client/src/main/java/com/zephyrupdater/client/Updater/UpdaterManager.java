package com.zephyrupdater.client.Updater;

import com.zephyrupdater.client.MainClient;
import com.zephyrupdater.client.Updater.JavaUpdater.JavaUpdater;
import com.zephyrupdater.common.FileUtils.CurseForgeMod;
import com.zephyrupdater.common.FileUtils.CurseForgeUtils;

import java.nio.file.Path;
import java.util.List;

public class UpdaterManager {

    public static void update(){
        JavaUpdater.javaUpdate();

        Path modPath = MainClient.clientFilePath.resolve("mods");
        //CurseForgeUtils.createBlankJsonModList(3, MainClient.clientFilePath, "modList.json");
        List<CurseForgeMod> modList = CurseForgeUtils.getModList(MainClient.clientFilePath.resolve("modList.json"), MainClient.clientFilePath);
        for(CurseForgeMod curseForgeMod : modList){
            System.out.println("Downloading: " + curseForgeMod.getFileName());
            curseForgeMod.downloadMod(modPath);
        }
    }
}
