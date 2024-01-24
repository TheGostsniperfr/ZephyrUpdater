package com.zephyrupdater.server.ZUProtocol;

import com.zephyrupdater.common.ZUProtocolCore.ZUPStructCore;
import com.zephyrupdater.server.clientUtils.ClientHandler;

public interface ZUPStruct extends ZUPStructCore {
    void execute(ClientHandler client);
}
