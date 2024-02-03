package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.commands.ICmd;
import com.zephyrupdater.server.utils.PublicFilesUtils;

import java.util.List;

public class CmdSetPubicState implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        if(argv.size() < 2){
            System.out.println(getHelp());
            return;
        }

        Boolean newState = argv.get(0).trim().equalsIgnoreCase("true");

        PublicFilesUtils.setPublicStateRequest(server.getPublicFilesDB(), newState, argv.get(1));
    }

    @Override
    public String getCmdName() {
        return "setPublicState";
    }

    @Override
    public String getHelp() {
        return getCmdName() + " [true|false] [request alias]: set the public statue of a request.";
    }
}
