package com.zephyrupdater.server.ZUProtocol;

import com.zephyrupdater.common.ZUProtocolCore.ZUPStructCore;
import com.zephyrupdater.server.client.ClientHandler;

public interface ZUPStruct extends ZUPStructCore {
    public void execute(ClientHandler client);
}