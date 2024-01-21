package com.zephyrupdater.server.serverCmd.cmdlist.extFiles;

import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.serverCmd.ServerCmd;

import java.util.List;

public class CmdRequestList implements ServerCmd {
    @Override
    public String getCmdName() {
        return "requestList";
    }

    @Override
    public void execute(List<String> argv) {
        AppServer.getUpdateRequestManager().getDb().keySet().forEach(System.out::println);
    }
}
