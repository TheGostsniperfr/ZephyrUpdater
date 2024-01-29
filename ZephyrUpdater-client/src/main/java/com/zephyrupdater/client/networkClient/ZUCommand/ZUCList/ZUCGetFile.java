package com.zephyrupdater.client.networkClient.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.networkClient.ZUCommand.ZUCStruct;
import com.zephyrupdater.client.networkClient.ZephyrNetClient;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCGetFileCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ZUCGetFile extends ZUCGetFileCore implements ZUCStruct {
    public ZUCGetFile(String fileName, Path relativeFilePath) {
        super(fileName, relativeFilePath);
    }

    public ZUCGetFile(JsonObject data) { super(data); }

    @Override
    public void executeServerCmd() {

    }


    public static void executeClientCmd(List<String> argv) {
        if (argv.size() < 2) {
            printHelp();
            return;
        }

        Path filePath = Paths.get(argv.get(1));

        ZUPManager.sendData(
                ZephyrNetClient.getServerSocket(),
                new ZUPCommandCore(new ZUCGetFileCore(
                                        filePath.getFileName().toString(),
                                        filePath))
        );
    }


    public static void printHelp(){
        System.out.println(getStructType().getCmdAlias() + " [relative path to the file]");
    }
}
