package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.commands.ICmd;

import java.util.List;

public class CmdReloadDB implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        server.getFilesDB().loadDB();
    }

    @Override
    public String getCmdName() {
        return "reloadDB";
    }

    @Override
    public String getHelp() {
        return getCmdName() + ": reload the database.";
    }
}
