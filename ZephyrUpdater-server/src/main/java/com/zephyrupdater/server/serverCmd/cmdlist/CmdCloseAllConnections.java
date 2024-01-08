package com.zephyrupdater.server.serverCmd.cmdlist;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.serverCmd.ServerCmd;

public class CmdCloseAllConnections implements ServerCmd {
    @Override
    public String getCmdName() {
        return "allConnDeco";
    }

    @Override
    public void execute() {
        if(AppServer.clients.isEmpty()){
            System.out.println("No client connected to the server.");
            return;
        }
        CmdStop.informClientsAboutShutdown();
    }
}

