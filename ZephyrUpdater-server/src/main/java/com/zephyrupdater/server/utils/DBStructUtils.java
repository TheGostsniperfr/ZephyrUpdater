package com.zephyrupdater.server.utils;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.PromptUtils;
import com.zephyrupdater.server.database.DBStruct;

public class DBStructUtils {
    public static JsonObject getRequestObj(DBStruct db, String requestName){
        for(String requestAlias : db.getAllRequestAlias()){
            if(requestAlias.equals(requestName)){
                return db.getDB().getAsJsonObject(requestAlias);
            }
        }

        return null;
    }

    public static Boolean isSharedRequest(JsonObject request){
        System.out.println(request.get("isShared").toString());
        return request.get("isShared").getAsBoolean();
    }

    public static void setIsShared(DBStruct db, Boolean newState, String requestAlias){
        JsonObject requestObj = DBStructUtils.getRequestObj(db, requestAlias);
        if(requestObj == null){
            System.out.println("Invalid request alias: " + requestAlias);
            return;
        }

        requestObj.addProperty("isShared", newState.toString());
        db.saveDB();
        System.out.println("Success to set: " + requestAlias + " isShared: " + newState);
    }

    public static Boolean isRequestAlreadyExist(DBStruct db, String requestAlias){
        if(DBStructUtils.getRequestObj(db, requestAlias) != null){
            return !PromptUtils.getUserChoice("Request Alias already exist. Overwrite ?");
        }

        return false;
    }
}
