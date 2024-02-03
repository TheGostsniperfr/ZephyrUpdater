package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.commands.ICmd;

import java.util.List;

public class CmdSaveDB implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        server.getPublicFilesDB().saveDB();
    }

    @Override
    public String getCmdName() {
        return "saveDB";
    }

    @Override
    public String getHelp() {
        return getCmdName() + ": save the database.";
    }
}
