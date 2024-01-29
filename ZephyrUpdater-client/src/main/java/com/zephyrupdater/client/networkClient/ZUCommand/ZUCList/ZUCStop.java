package com.zephyrupdater.client.networkClient.ZUCommand.ZUCList;

import com.zephyrupdater.client.networkClient.ZUCommand.ZUCStruct;
import com.zephyrupdater.client.networkClient.ZephyrNetClient;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCStopCore;

import java.util.List;

public class ZUCStop extends ZUCStopCore implements ZUCStruct {
    @Override
    public void executeServerCmd() {

    }

    public static void executeClientCmd(List<String> argv) {
        if(ZephyrNetClient.getIsConnect()){
            ZephyrNetClient.disconnectFromServer(true);
        }

        System.exit(0);
    }

    public static void printHelp(){
        System.out.println(getStructType().getCmdAlias());
    }

}
