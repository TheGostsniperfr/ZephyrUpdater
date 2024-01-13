package com.zephyrupdater.client.ZUCommand.ZUCList;

import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCStopCore;

import java.util.List;

public class ZUCStop extends ZUCStopCore implements ZUCStruct {
    @Override
    public void executeServerCmd() {

    }

    public static void executeClientCmd(List<String> argv) {
        if(AppClient.getIsConnect()){
            AppClient.disconnectFromServer(true);
        }

        System.exit(0);
    }
}
