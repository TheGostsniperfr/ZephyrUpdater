package com.zephyrupdater.server.serverCmd.cmdlist.serverManagement;

import com.zephyrupdater.server.clientUtils.ClientHandler;
import com.zephyrupdater.server.serverCmd.ServerCmd;

import java.util.List;

public class CmdMessage implements ServerCmd {
    @Override
    public String getCmdName() {
        return "msg";
    }

    @Override
    public void execute(List<String> argv) {
        if(argv.size() < 2){
            printHelp();
            return;
        }

        ClientHandler client = ClientHandler.getClientById(argv.get(0));
        if(client == null){
            System.out.println("No clients log with id: " + argv.get(0) + " is connected");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(argv.get(1));

        for (int i = 2; i < argv.size(); i++) {
            stringBuilder.append(" ").append(argv.get(i));
        }

        System.out.println(stringBuilder);

        client.sendMsgToClient(stringBuilder.toString());
    }

    public void printHelp(){
        System.out.println(this.getCmdName() + " [Client id] [Message]");
    }
}
