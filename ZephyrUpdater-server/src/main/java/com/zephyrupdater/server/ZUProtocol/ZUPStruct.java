package com.zephyrupdater.server.ZUProtocol;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUProtocol.ZUPStructCore;
import com.zephyrupdater.server.client.ClientHandler;

public interface ZUPStruct extends ZUPStructCore {
    public void execute(ClientHandler client);
}
