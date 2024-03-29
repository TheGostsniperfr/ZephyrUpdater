package com.zephyrupdater.server.commands;

import com.zephyrupdater.server.ZephyrServerManager;

import java.util.List;

public interface ICmd {
    void execute(ZephyrServerManager server, List<String> argv);
    String getCmdName();

    String getHelp();
}
