package com.zephyrupdater.server.utils.commands.cmdlist;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.utils.commands.CmdManager;
import com.zephyrupdater.server.utils.commands.ICmd;

import java.util.List;

public class CmdPrintHelp implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        for (Class<? extends ICmd> aClass : CmdManager.getAllServerCmdClasses()) {
            try {
                System.out.println(aClass.getDeclaredConstructor().newInstance().getHelp());
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
