package com.zephyrupdater.server.utils.commands.cmdlist;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.utils.commands.ICmd;

import java.util.List;

public class CmdAddRequest implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        if(argv.size() < 2){
            System.out.println(getHelp());
            return;
        }

        server.getPublicFilesRequest().addRequest(argv.get(0), argv.get(1));
    }

    @Override
    public String getCmdName() {
        return null;
    }

    @Override
    public String getHelp() {
        return getCmdName() + ": add a new request to database.";
    }
}
