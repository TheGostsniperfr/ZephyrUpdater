package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.commands.ICmd;
import com.zephyrupdater.server.database.DBStruct;
import com.zephyrupdater.server.utils.DBStructUtils;

import java.util.List;

public class CmdSaveDB implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        if(argv.isEmpty()){
            System.out.println(getHelp());
            return;
        }

        DBStruct db = DBStructUtils.getDBByName(server, argv.getFirst());
        if(db == null) { return; }

        db.saveDB();
        System.out.println("Success to save db.");
    }

    @Override
    public String getCmdName() {
        return "saveDB";
    }

    @Override
    public String getHelp() {
        return getCmdName() + " [files|modList]: save the database.";
    }
}
