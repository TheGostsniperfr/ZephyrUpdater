package com.zephyrupdater.server.serverCmd.cmdlist;

import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCDisconnection;
import com.zephyrupdater.common.ZUProtocol.ZUPManager;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPCommand;
import com.zephyrupdater.server.serverCmd.ServerCmd;
import com.zephyrupdater.server.AppServer;

import java.util.ArrayList;
import java.util.List;

public class CmdStop implements ServerCmd {

    @Override
    public String getCmdName() {
        return "stop";
    }

    @Override
    public void execute() {
        System.out.println("Closing server...");
        informClientsAboutShutdown();
        System.out.println("Server closed, see you.");
        System.exit(0);
    }

    public static void informClientsAboutShutdown() {
        List<AppServer.ClientHandler> clientsCopy = new ArrayList<>(AppServer.clients);
        for (AppServer.ClientHandler client : clientsCopy) {
            try {
                AppServer.disconnect(client, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}