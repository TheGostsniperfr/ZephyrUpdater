package com.zephyrupdater.common.ZUCommandCore.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;

public class ZUCConnectCore implements ZUCStructCore {

    public String host;
    public int serverPort;
    @Override
    public ZUCTypes getStructType() {
        return ZUCTypes.CONNECT;
    }

    public static String getCmdName(){
        return "connect";
    }

    @Override
    public JsonObject getJson() {
        return new JsonObject();
    }

    public ZUCConnectCore(String host, int port){
        this.host = host;
        this.serverPort = port;
    }

}
