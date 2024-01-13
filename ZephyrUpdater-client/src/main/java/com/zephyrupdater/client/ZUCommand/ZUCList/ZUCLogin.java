package com.zephyrupdater.client.ZUCommand.ZUCList;

import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCLoginCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;

import java.util.List;

public class ZUCLogin extends ZUCLoginCore implements ZUCStruct {
    public ZUCLogin(String id, String password) {
        super(id, password);
    }

    @Override
    public void executeServerCmd() {

    }

    public static void executeClientCmd(List<String> argv){
        if (argv.size() < 3) {
            printHelp();
            return;
        }

        ZUPManager.sendData(
                AppClient.getServerSocket(),
                new ZUPCommandCore(new ZUCLogin(argv.get(1), argv.get(2))));
    }



    public static void printHelp(){
        System.out.println("login [id] [password]");
    }



}
