package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.commands.ServerCmd;

public class CmdCloseAllConnections implements ServerCmd {
    @Override
    public String getCmdName() {
        return "allConnDeco";
    }

    @Override
    public void execute() {
        for (AppServer.ClientHandler client:  AppServer.clients) {
            try {
                System.out.println("closing: " + client.clientSocket.getInetAddress().getHostAddress());
                //client.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

