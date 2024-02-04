package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.commands.ICmd;
import com.zephyrupdater.server.database.DBStruct;
import com.zephyrupdater.server.utils.DBStructUtils;

import java.util.List;

public class CmdSetSharedState implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        if(argv.size() < 3){
            System.out.println(getHelp());
            return;
        }

        DBStruct db = DBStructUtils.getDBByName(server, argv.getFirst());
        if(db == null) { return; }

        Boolean newState = argv.get(1).trim().equalsIgnoreCase("true");
        DBStructUtils.setIsShared(db, newState, argv.get(2));
    }

    @Override
    public String getCmdName() {
        return "setSharedState";
    }

    @Override
    public String getHelp() {
        return getCmdName() + " [modList|files] [true|false] [request alias]: set the shared statue of a request.";
    }
}
