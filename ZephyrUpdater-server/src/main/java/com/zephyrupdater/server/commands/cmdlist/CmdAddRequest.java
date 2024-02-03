package com.zephyrupdater.server.commands.cmdlist;

import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.commands.ICmd;
import com.zephyrupdater.server.utils.PublicFilesUtils;

import java.util.List;

public class CmdAddRequest implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        if(argv.size() < 2){
            System.out.println(getHelp());
            return;
        }

        PublicFilesUtils.addRequest(server.getPublicFilesDB(), argv.get(0), argv.get(1));
    }

    @Override
    public String getCmdName() {
        return "addRequest";
    }

    @Override
    public String getHelp() {
        return getCmdName() + " [request alias] [target folder]: add a new request to database.";
    }
}
