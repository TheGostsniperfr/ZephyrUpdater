package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.commands.ServerCmd;

public class CmdListAllConnexions implements ServerCmd {
    @Override
    public String getCmdName() {
        return "list";
    }

    @Override
    public void execute() {
        System.out.println("Total connections: " + AppServer.clients.size());
        int nb = 0;
        for (AppServer.ClientHandler client:  AppServer.clients) {
            System.out.printf("Client %02d: %s%n", nb++, client.clientSocket.getInetAddress().getHostAddress());
        }
    }
}