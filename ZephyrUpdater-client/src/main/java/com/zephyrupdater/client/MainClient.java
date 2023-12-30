package com.zephyrupdater.client;

import com.zephyrupdater.common.CommonUtil;
public class MainClient {
    public static void main(String[] args) {
        System.out.println("Client: " + CommonUtil.SHARE_MSG);

        AppClient.launchClient();
    }
}
