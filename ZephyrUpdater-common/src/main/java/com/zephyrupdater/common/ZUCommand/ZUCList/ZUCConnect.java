package com.zephyrupdater.common.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCKeys;
import com.zephyrupdater.common.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;

public class ZUCConnect extends ZUCStruct {

    public String host;
    public int serverPort;
    public static String getCmdName(){
        return "connect";
    }

    @Override
    public String getJson() {
        return "ZUCConnect is a local cmd";
    }

    public ZUCConnect(String host, int port){
        this.structType = ZUCTypes.CONNECT;
        this.host = host;
        this.serverPort = port;
    }
    public static void printHelp(){
        System.out.println("connect [host] [port]");
    }
}
