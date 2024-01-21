package com.zephyrupdater.server.serverCmd.cmdlist.serverManagement;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.clientUtils.ClientHandler;
import com.zephyrupdater.server.serverCmd.ServerCmd;

import java.util.List;

public class CmdListAllConnexions implements ServerCmd {
    @Override
    public String getCmdName() {
        return "list";
    }

    @Override
    public void execute(List<String> argv) {
        System.out.println("Total connections: " + AppServer.clients.size());
        int nb = 0;
        for (ClientHandler client:  AppServer.clients) {
            String id = "";

            System.out.printf("Client %02d: %s%n", nb++, client.getHost() + " id: " + client.getClientId());
        }
    }
}