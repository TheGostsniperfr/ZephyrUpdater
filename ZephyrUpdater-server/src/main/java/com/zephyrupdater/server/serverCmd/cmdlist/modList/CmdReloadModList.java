package com.zephyrupdater.server.serverCmd.cmdlist.modList;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.serverCmd.ServerCmd;

import java.util.List;

public class CmdReloadModList implements ServerCmd {
    @Override
    public String getCmdName() {
        return "reloadModList";
    }

    @Override
    public void execute(List<String> argv) {
        AppServer.getCurseForgeUpdater().loadModList();
        System.out.println("Successful mod list reload");
    }
}
