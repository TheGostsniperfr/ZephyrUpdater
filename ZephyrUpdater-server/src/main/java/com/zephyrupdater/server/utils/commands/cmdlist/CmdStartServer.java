package com.zephyrupdater.server.utils.commands.cmdlist;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.utils.commands.ICmd;

import java.util.List;

public class CmdStartServer implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        server.start();
    }

    @Override
    public String getCmdName() {
        return "start";
    }

    @Override
    public String getHelp() {
        return getCmdName() + ": start the server.";
    }
}
