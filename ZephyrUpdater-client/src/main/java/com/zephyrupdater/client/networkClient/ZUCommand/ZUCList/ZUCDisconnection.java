package com.zephyrupdater.client.networkClient.ZUCommand.ZUCList;

import com.zephyrupdater.client.networkClient.ZUCommand.ZUCStruct;
import com.zephyrupdater.client.networkClient.ZephyrNetClient;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCDisconnectionCore;

import java.util.List;

public class ZUCDisconnection extends ZUCDisconnectionCore implements ZUCStruct {

    @Override
    public void executeServerCmd() {
        ZephyrNetClient.disconnectFromServer(false);
        System.out.println("Server close.");
    }



    public static void executeClientCmd(List<String> argv) {
        ZephyrNetClient.disconnectFromServer(true);
    }


    public static void printHelp(){
        System.out.println(getStructType().getCmdAlias());
    }
}
