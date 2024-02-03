package com.zephyrupdater.server.utils;

import com.google.gson.JsonObject;
import com.zephyrupdater.server.database.ModListDB;

public class ModListDBUtils {
    public static void addCurseForgeModToModList(ModListDB modListDB, String requestAlias, String modName, String fileId, String projectId){
        JsonObject request = DBStructUtils.getRequestObj(modListDB, requestAlias);
        if(request == null){
            System.err.println("Unknown request alias.");
            return;
        }

        JsonObject modList = request.getAsJsonObject("modList");

        if(modList == null) { modList = new JsonObject(); }

        if(modList.get(modName) != null){
            System.err.println("Same mod name is already exist.");
            return;
        }

        JsonObject tmpObj = new JsonObject();
        tmpObj.addProperty("fileId", fileId);
        tmpObj.addProperty("projectId", projectId);
        modList.add(modName, tmpObj);

        modListDB.saveDB();
        System.out.println("Success to add mod: " + modName);
    }
}
