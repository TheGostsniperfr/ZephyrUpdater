package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.commands.ICmd;

import java.util.List;

public class CmdPrintHelp implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        List<ICmd> cmdList = server.getCmdManager().getAllServerCmdClasses();
        System.out.println("Found " + cmdList.size() + " commands:");
        for (int i = 0; i < cmdList.size() ; i++) {
            try {
                System.out.println((i + 1) + ": " + cmdList.get(i).getHelp());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getCmdName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return getCmdName() + ": type help for help.";
    }
}
