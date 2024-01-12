package com.zephyrupdater.server.serverCmd;

import java.util.List;

public interface ServerCmd {
     String getCmdName();

    void execute(List<String> argv);
}