package com.zephyrupdater.client.networkClient.ZUCommand.ZUCList;

import com.zephyrupdater.client.networkClient.ZUCommand.ZUCStruct;
import com.zephyrupdater.client.networkClient.ZephyrNetClient;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCConnectCore;

import java.net.Socket;
import java.util.List;

public class ZUCConnect extends ZUCConnectCore implements ZUCStruct {
    public ZUCConnect(String host, int port) {
        super(host, port);
    }

    @Override
    public void executeServerCmd() {

    }

    public static void executeClientCmd(List<String> argv) {
        if(argv.size() <= 2){
            printHelp();
            return;
        }

        if(ZephyrNetClient.getServerSocket() != null){
            System.out.println("You are already connect to a server.");
            return;
        }
        int serverPort;

        try {
            serverPort = Integer.parseInt(argv.get(2));
        } catch (NumberFormatException e){
            System.out.println("Invalid port format.");
            return;
        }

        ZUCConnectCore zucConnect = new ZUCConnectCore(argv.get(1), serverPort);

        for(int nbTry = 0; nbTry < CommonUtil.nbTry; nbTry++) {
            try {
                ZephyrNetClient.setServerSocket(new Socket(CommonUtil.HOST, CommonUtil.SERVER_PORT)); /* TODO */
                System.out.println("Successful server connection.");
                ZephyrNetClient.setIsConnect(true);
                ZephyrNetClient.setListenToServerThread(new Thread(() -> ZephyrNetClient.listenToServer()));
                ZephyrNetClient.getListenToServerThread().start();
                break;

            } catch (Exception e) {
                try {
                    Thread.sleep(CommonUtil.timeRetryInterval);
                }catch (Exception eWait){
                    eWait.printStackTrace();
                }
            }
        }
        if(!ZephyrNetClient.getIsConnect()){
            System.out.println(
                    "No further information from server: "
                            + zucConnect.host + ":"
                            + zucConnect.serverPort
            );
        }

    }

    public static void printHelp(){
        System.out.println(getStructType().getCmdAlias() + " [host] [port]");
    }
}
