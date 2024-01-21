package com.zephyrupdater.server.ZUCommand;

import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.server.clientUtils.ClientHandler;

public interface ZUCStruct extends ZUCStructCore {
    void execute(ClientHandler client);
}
