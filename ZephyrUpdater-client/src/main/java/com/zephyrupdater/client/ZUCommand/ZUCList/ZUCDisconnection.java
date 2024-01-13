package com.zephyrupdater.client.ZUCommand.ZUCList;

import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCDisconnectionCore;

import java.util.List;

public class ZUCDisconnection extends ZUCDisconnectionCore implements ZUCStruct {

    @Override
    public void executeServerCmd() {
        AppClient.disconnectFromServer(false);
        System.out.println("Server close.");
    }

    public static void executeClientCmd(List<String> argv) {
        AppClient.disconnectFromServer(true);
    }
}
