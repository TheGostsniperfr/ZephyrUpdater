package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.commands.ICmd;

import java.util.List;

public class CmdStopServer implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        server.stop();
    }

    @Override
    public String getCmdName() {
        return "stop";
    }

    @Override
    public String getHelp() {
        return getCmdName() + ": stop the server.";
    }
}
