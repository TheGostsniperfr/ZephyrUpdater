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

