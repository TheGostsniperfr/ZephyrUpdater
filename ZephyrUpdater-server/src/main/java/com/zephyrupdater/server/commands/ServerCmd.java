package com.zephyrupdater.server.commands;

public interface ServerCmd {
    String getCmdName();

    void execute();
}