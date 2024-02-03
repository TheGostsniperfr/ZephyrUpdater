package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.commands.ICmd;
import com.zephyrupdater.server.utils.ModListDBUtils;

import java.util.List;

public class CmdAddModToModList implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        if(argv.size() < 4){
            System.out.println(getHelp());
            return;
        }

        ModListDBUtils.addCurseForgeModToModList(server.getModListDB(), argv.get(0), argv.get(1), argv.get(2), argv.get(3));
    }

    @Override
    public String getCmdName() {
        return "addMod";
    }

    @Override
    public String getHelp() {
        return getCmdName() + ": add a mod to the mod list.";
    }
}
