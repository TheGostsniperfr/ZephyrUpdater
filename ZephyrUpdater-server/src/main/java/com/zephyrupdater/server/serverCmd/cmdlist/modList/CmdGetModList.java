package com.zephyrupdater.server.serverCmd.cmdlist.modList;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.serverCmd.ServerCmd;

import java.util.List;

public class CmdGetModList implements ServerCmd {
    @Override
    public String getCmdName() {
        return "getModList";
    }

    @Override
    public void execute(List<String> argv) {
        for(String modName : AppServer.getCurseForgeUpdater().getModList().keySet()){
            System.out.println(modName);
        }
    }
}
