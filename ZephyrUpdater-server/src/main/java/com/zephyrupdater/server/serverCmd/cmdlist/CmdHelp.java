package com.zephyrupdater.server.serverCmd.cmdlist;

import com.zephyrupdater.server.serverCmd.CmdManager;
import com.zephyrupdater.server.serverCmd.ServerCmd;

import java.util.List;

public class CmdHelp implements ServerCmd {

    @Override
    public String getCmdName() {
        return "help";
    }

    @Override
    public void execute() {
        System.out.println("Help menu: \n");

        List<Class<? extends ServerCmd>> allClasses = CmdManager.getAllServerCmdClasses();

        int n = 0;
        for(Class<? extends ServerCmd> clazz : allClasses){
            try {
                System.out.println(n++ + ": " + clazz.getDeclaredConstructor().newInstance().getCmdName());
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
