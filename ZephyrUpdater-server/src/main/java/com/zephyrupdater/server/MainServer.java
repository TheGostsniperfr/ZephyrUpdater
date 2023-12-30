package com.zephyrupdater.server;

import com.zephyrupdater.common.CommonUtil;
public class MainServer {
    public static void main(String[] args) {
        System.out.println("Server: " + CommonUtil.SHARE_MSG);

        AppServer.launchServer();
    }

}
