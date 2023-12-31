package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.commands.ServerCmd;

import java.net.Socket;

public class CmdListAllConnexions implements ServerCmd {
    @Override
    public String getCmdName() {
        return "list";
    }

    @Override
    public void execute() {
        System.out.println("Total connections: " + AppServer.activeConnections.size());
        int nb = 0;
        for(Socket socket : AppServer.activeConnections){
            System.out.printf("Client %02d: %s%n", nb++, socket.getInetAddress().getHostAddress());
        }
    }
}