package com.zephyrupdater.server.serverCmd.cmdlist;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.client.ClientHandler;
import com.zephyrupdater.server.serverCmd.ServerCmd;

public class CmdListAllConnexions implements ServerCmd {
    @Override
    public String getCmdName() {
        return "list";
    }

    @Override
    public void execute() {
        System.out.println("Total connections: " + AppServer.clients.size());
        int nb = 0;
        for (ClientHandler client:  AppServer.clients) {
            System.out.printf("Client %02d: %s%n", nb++, client.getHost());
        }
    }
}