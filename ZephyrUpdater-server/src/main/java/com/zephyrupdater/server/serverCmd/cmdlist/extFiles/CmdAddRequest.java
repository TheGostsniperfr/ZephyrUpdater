package com.zephyrupdater.server.serverCmd.cmdlist.extFiles;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.MainServer;
import com.zephyrupdater.server.serverCmd.ServerCmd;

import java.nio.file.Path;
import java.util.List;

public class CmdAddRequest implements ServerCmd {
    @Override
    public String getCmdName() {
        return "addRequest";
    }

    @Override
    public void execute(List<String> argv) {
        if(argv.size() < 2){
            printHelp();
            return;
        }

        Path absPath = MainServer.publicDirPath.resolve(argv.get(1));
        AppServer.getUpdateRequestManager().addRequest(argv.get(0), absPath);
    }

    public void printHelp(){
        System.out.println("[request alias] [target dir]");
    }
}
