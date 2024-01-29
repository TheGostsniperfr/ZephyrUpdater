package com.zephyrupdater.client.networkClient;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.ZephyrUpdater;
import com.zephyrupdater.client.networkClient.ZUCommand.ZUCManager;
import com.zephyrupdater.client.networkClient.ZUCommand.ZUCStruct;
import com.zephyrupdater.client.networkClient.ZUProtocol.ZUPStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCDisconnectionCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;
import com.zephyrupdater.common.ZUProtocolCore.ZUPManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;

import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ZephyrNetClient {
    private static Socket serverSocket = null;
    private static Thread listenToServerThread = null;
    private static Boolean isConnect = false;
    public static Boolean fileReady = false;
    public static ZephyrUpdater zUpdater;

    public static void launchClient(ZephyrUpdater zephyrUpdater) {
        zUpdater = zephyrUpdater;
        Thread consoleListenerThread = new Thread(() -> listenToConsole());
        consoleListenerThread.start();
    }

    public static void listenToServer()
    {
        try {
            InputStream inputStream = serverSocket.getInputStream();
            while(isConnect) {
                JsonObject dataHeader = FileUtils.loadJsonFromStream(inputStream);
                if(dataHeader == null){
                    disconnectFromServer(false);
                    break;
                }

                ZUPStruct zupStruct = ZUPStruct.getStructFromDataHeader(dataHeader);
                if(zupStruct != null){
                    zupStruct.execute();
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void listenToConsole(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String msg = scanner.nextLine();
            msg = msg.replace("  ", " ");
            List<String> argv = List.of(msg.split(" "));

            if (argv.isEmpty()) { continue; }

            Class<? extends ZUCStruct> clazz =  ZUCManager.getClassByAlias(argv.get(0));
            if(clazz == null){
                System.err.println("Unknown command alias, type help for help.");
                continue;
            }

            ZUCManager.executeClientCmd(clazz, argv);
        }
    }

    public static void disconnectFromServer(Boolean propagate){
        if(!isConnect){ return; }

        System.out.println("Disconnecting from the server...");

        try{
            if(listenToServerThread != null) {
                listenToServerThread.interrupt();
            }
            if (propagate) {
                ZUPManager.sendData(serverSocket, new ZUPCommandCore(new ZUCDisconnectionCore()));
            }
            isConnect = false;
        } catch (Exception e){
            e.printStackTrace();
        }

        serverSocket = null;
        System.out.println("You have been disconnected.");
    }

    public static Socket getServerSocket() {
        return serverSocket;
    }

    public static void setServerSocket(Socket serverSocket) {
        ZephyrNetClient.serverSocket = serverSocket;
    }

    public static Boolean getIsConnect() {
        return isConnect;
    }

    public static void setIsConnect(Boolean isConnect) {
        ZephyrNetClient.isConnect = isConnect;
    }

    public static Thread getListenToServerThread() {
        return listenToServerThread;
    }

    public static void setListenToServerThread(Thread listenToServerThread) {
        ZephyrNetClient.listenToServerThread = listenToServerThread;
    }
    public static void sendCmdToServer(ZUCStructCore cmd){
        if(ZUCTypes.getZUCType(cmd) == ZUCTypes.GET_FILE){
            fileReady = false;
        }
        ZUPManager.sendData(getServerSocket(), new ZUPCommandCore(cmd));
    }
}