package com.zephyrupdater.server.serverCmd.cmdlist.serverManagement;

import com.zephyrupdater.server.clientUtils.ClientHandler;
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
    public void execute(List<String> argv) {
        System.out.println("Closing server...");
        informClientsAboutShutdown();
        System.out.println("Server closed, see you.");
        System.exit(0);
    }

    public static void informClientsAboutShutdown() {
        List<ClientHandler> clientsCopy = new ArrayList<>(AppServer.clients);
        for (ClientHandler client : clientsCopy) {
            try {
                client.disconnect(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}