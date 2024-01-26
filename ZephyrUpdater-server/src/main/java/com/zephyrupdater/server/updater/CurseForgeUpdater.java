package com.zephyrupdater.server.updater;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.utils.FileUtils.CurseKeys;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;
import com.zephyrupdater.server.MainServer;

import java.nio.file.Files;
import java.nio.file.Path;

public class CurseForgeUpdater {
    private final Path curseModJsonPath = MainServer.cacheDirPath.resolve("curseModList.json");
    private JsonObject modList;
    private void init(){
        FileUtils.createDirIfNotExist(this.curseModJsonPath.getParent());
        if(!Files.exists(this.curseModJsonPath)){
            FileUtils.saveJsonAt(new JsonObject(), this.curseModJsonPath);
        }
        this.loadModList();
    }

    public CurseForgeUpdater(){
        init();
    }

    public void createBlankJsonModList(int nbMods){
        this.modList = new JsonObject();
        for(int i = 0; i < nbMods; i++){
            JsonObject modJson = new JsonObject();
            modJson.addProperty(CurseKeys.FILE_ID.getKey(),"");
            modJson.addProperty(CurseKeys.PROJECT_ID.getKey(),"");
            this.modList.add(new StringBuilder("CurseForgeUrl").append(i).toString(), modJson);
        }

        this.saveModList();
    }

    public void addMod(String modName, String fileId, String projectId){
        JsonObject newModObj = new JsonObject();
        newModObj.addProperty(CurseKeys.FILE_ID.getKey(), fileId);
        newModObj.addProperty(CurseKeys.PROJECT_ID.getKey(), projectId);

        this.modList.add(modName, newModObj);
        this.saveModList();
    }

    public void saveModList(){
        FileUtils.saveJsonAt(this.modList, this.curseModJsonPath);
    }

    public void loadModList(){
        this.modList = FileUtils.loadJsonFromFilePath(curseModJsonPath);
    }

    public JsonObject getModList() {
        return modList;
    }
}
