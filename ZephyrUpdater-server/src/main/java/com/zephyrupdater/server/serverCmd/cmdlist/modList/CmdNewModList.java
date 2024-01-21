package com.zephyrupdater.server.serverCmd.cmdlist.modList;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.serverCmd.ServerCmd;

import java.util.List;

public class CmdNewModList implements ServerCmd {
    @Override
    public String getCmdName() {
        return "newModList";
    }

    @Override
    public void execute(List<String> argv) {
        if(argv.size() < 1){
            printHelp();
            return;
        }

        AppServer.getCurseForgeUpdater().createBlankJsonModList(Integer.parseInt(argv.get(0)));
    }

    public void printHelp(){
        System.out.println("[number of mods]");
    }
}
