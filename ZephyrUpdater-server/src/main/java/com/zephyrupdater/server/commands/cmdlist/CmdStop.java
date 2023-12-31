package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.commands.ServerCmd;

public class CmdStop implements ServerCmd {

    @Override
    public String getCmdName() {
        return "stop";
    }

    @Override
    public void execute() {
        System.out.println("Closing server.");
        AppServer.isServerRunning = false;
        new CmdCloseAllConnections().execute();
        System.out.println("Server Close.\nSee you soon.");
        System.exit(0);
    }
}