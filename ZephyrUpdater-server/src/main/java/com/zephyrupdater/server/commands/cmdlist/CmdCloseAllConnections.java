package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.commands.ServerCmd;

import java.io.IOException;
import java.net.Socket;

public class CmdCloseAllConnections implements ServerCmd {
    @Override
    public String getCmdName() {
        return "allConnDeco";
    }

    @Override
    public void execute() {
        for (Socket socket : AppServer.activeConnections) {
            try {
                System.out.println("closing: " + socket.getInetAddress().getHostAddress());

                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                //pass
            }
        }

        AppServer.activeConnections.clear();
    }
}

