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

        DBStruct db;
        if(argv.get(1).trim().equals("modList")){
            db = server.getModListDB();
        } else if (argv.get(1).trim().equals("files")) {
            db = server.getFilesDB();
        } else{
            System.out.println("Invalid database name.");
            return;
        }

        Boolean newState = argv.get(0).trim().equalsIgnoreCase("true");
        DBStructUtils.setIsShared(db, newState, argv.get(2));
    }

    @Override
    public String getCmdName() {
        return "setPublicState";
    }

    @Override
    public String getHelp() {
        return getCmdName() + " [true|false] [modList|files] [request alias]: set the shared statue of a request.";
    }
}
