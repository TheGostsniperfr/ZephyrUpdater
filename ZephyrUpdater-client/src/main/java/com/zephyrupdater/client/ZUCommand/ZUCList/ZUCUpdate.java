package com.zephyrupdater.client.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.Updater.UpdaterManager;
import com.zephyrupdater.client.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCUpdateCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;

import java.util.List;

public class ZUCUpdate extends ZUCUpdateCore implements ZUCStruct {
    public ZUCUpdate(JsonObject data) {
        super(data);
    }

    public ZUCUpdate(String folderPath) {
        super(folderPath);
    }

    @Override
    public void executeServerCmd() {
        System.out.println("extFilesjson: " + extFilesJson);
        System.out.println("curseMods: " + curseModJson);
        new Thread(() -> UpdaterManager.update(this.extFilesJson, this.curseModJson)).start();
        }


    public static void executeClientCmd(List<String> argv){
        if(argv.size() < 2){
            printHelp();
            return;
        }

        ZUPManager.sendData(
                AppClient.getServerSocket(),
                new ZUPCommandCore(new ZUCUpdate(argv.get(1))));
    }

    public static void printHelp(){
        System.out.println("update [request alias]");
    }
}
