package com.zephyrupdater.server.serverCmd.cmdlist.modList;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.serverCmd.ServerCmd;

import java.util.List;

public class CmdAddModToModList implements ServerCmd {
    @Override
    public String getCmdName() {
        return "addMod";
    }

    @Override
    public void execute(List<String> argv) {
        if(argv.size() < 3){
            printHelp();
            return;
        }

        AppServer.getCurseForgeUpdater().addMod(argv.get(0), argv.get(1), argv.get(2));
    }

    public void printHelp(){
        System.out.println("[modName] [fileId] [projectId]");
    }
}
