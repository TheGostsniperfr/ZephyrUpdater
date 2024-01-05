package com.zephyrupdater.server.serverCmd;

public interface ServerCmd {
    String getCmdName();

    void execute();
}