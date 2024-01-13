package com.zephyrupdater.client.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCUpdateCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;

import java.util.List;

public class ZUCUpdate extends ZUCUpdateCore implements ZUCStruct {
    public ZUCUpdate(JsonObject data) {
        super(data);
    }

    public ZUCUpdate(String folderPath, JsonObject filesJson) {
        super(folderPath, filesJson);
    }

    @Override
    public void executeServerCmd() {
        /* TODO
        *
        * check files (if they need to be update)
        *
        * */

        System.out.println("Update file at: " + this.folderPath);
        System.out.println(this.filesJson.toString());
    }

    /**
     * Checking files at a specific folder
     * path format: Arffornia_V.5/clientFiles/
     * or           Arffornia_V.5/launcherFiles/
     *
     * @param argv
     */
    public static void executeClientCmd(List<String> argv){
        if(argv.size() < 2){
            printHelp();
            return;
        }

        String folderPath = argv.get(1);
        if(folderPath.equals("*")){
            folderPath = "";
        }

        ZUPManager.sendData(
                AppClient.getServerSocket(),
                new ZUPCommandCore(new ZUCUpdate(folderPath, new JsonObject())));
    }

    public static void printHelp(){
        System.out.println("update [folder path (To check update)]");
    }
}
