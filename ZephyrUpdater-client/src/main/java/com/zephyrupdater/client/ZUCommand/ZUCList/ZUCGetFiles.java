package com.zephyrupdater.client.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCGetFilesCore;

public class ZUCGetFiles extends ZUCGetFilesCore implements ZUCStruct {
    public ZUCGetFiles(JsonObject data) {
        super(data);
    }

    @Override
    public void executeServerCmd() {

    }

    public static void printHelp(){
        System.out.println("getFiles []");
    }
}
