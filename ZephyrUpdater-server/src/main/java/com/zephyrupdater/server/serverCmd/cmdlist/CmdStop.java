package com.zephyrupdater.server.serverCmd.cmdlist;

import com.zephyrupdater.server.serverCmd.ServerCmd;
import com.zephyrupdater.server.AppServer;

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

    private void informClientsAboutShutdown() {
        String shutdownMessage = "serverStop\n";

        for (AppServer.ClientHandler client : AppServer.clients) {
            try {
                client.sendMessage(shutdownMessage);
                System.out.println("Client: " + client.clientSocket.getInetAddress() + " has been forcibly disconnected.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}