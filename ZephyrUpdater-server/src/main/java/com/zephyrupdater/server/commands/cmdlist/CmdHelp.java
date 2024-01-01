package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.commands.CmdManager;
import com.zephyrupdater.server.commands.ServerCmd;

import java.lang.reflect.InvocationTargetException;
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
