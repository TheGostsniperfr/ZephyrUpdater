package com.zephyrupdater.common.ZUCommand.ZUCList;

import com.zephyrupdater.common.ZUCommand.ZUCStructCore;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;

public class ZUCConnectCore extends ZUCStructCore {

    public String host;
    public int serverPort;
    public static String getCmdName(){
        return "connect";
    }

    @Override
    public String getJson() {
        return "ZUCConnect is a local cmd";
    }

    public ZUCConnectCore(String host, int port){
        this.structType = ZUCTypes.CONNECT;
        this.host = host;
        this.serverPort = port;
    }
    public static void printHelp(){
        System.out.println("connect [host] [port]");
    }
}
