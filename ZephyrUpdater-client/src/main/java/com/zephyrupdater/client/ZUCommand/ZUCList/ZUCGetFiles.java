package com.zephyrupdater.client.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.Updater.ExternalFilesUpdater.ExternalFilesUpdater;
import com.zephyrupdater.client.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCGetFilesCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ZUCGetFiles extends ZUCGetFilesCore implements ZUCStruct {
    public ZUCGetFiles(JsonObject data) {
        super(data);
    }

    @Override
    public void executeServerCmd() {
        ExternalFilesUpdater.checkUpdateExtFiles(this.extFilesJson);
    }

    public static void executeClientCmd(List<String> argv){
        if (argv.size() < 2) {
            printHelp();
            return;
        }

        Path filePath = Paths.get(argv.get(1));

        ZUPManager.sendData(
                AppClient.getServerSocket(),
                new ZUPCommandCore(new ZUCGetFilesCore(argv.get(1)))
        );
    }

    public static void printHelp(){
        System.out.println("getFiles [relative path to the target directory for files to download]");
        //ex: getFiles Arffornia_v.5/mc_files
    }
}
