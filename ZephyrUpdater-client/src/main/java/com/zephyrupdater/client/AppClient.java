package com.zephyrupdater.client;

import com.google.gson.JsonObject;
import com.zephyrupdater.client.ZUCommand.ZUCStruct;
import com.zephyrupdater.client.ZUProtocol.ZUPStruct;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCDisconnectionCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
public class AppClient {


    private static Socket serverSocket = null;
    private static Thread listenToServerThread = null;
    private static Boolean isConnect = false;

    public static void launchClient() {
        Thread consoleListenerThread = new Thread(() -> listenToConsole());
        consoleListenerThread.start();
    }

    public static void listenToServer()
    {
        try {
            InputStream inputStream;
            try {
                inputStream = serverSocket.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while(isConnect) {
                JsonObject dataHeader = ZUPManager.readJsonFromStream(inputStream);
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

            //input struct -> cmd [options] (ex: msg hello world -> print: hello world)

            msg = msg.replace("  ", " ");
            List<String> argv = List.of(msg.split(" "));

            if (argv.isEmpty()) {
                continue;
            }

            ZUCStruct.executeClientCmd(argv);
        }
    }

    public static void disconnectFromServer(Boolean propagate){
        if(!isConnect){
            return;
        }

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
        AppClient.serverSocket = serverSocket;
    }

    public static Boolean getIsConnect() {
        return isConnect;
    }

    public static void setIsConnect(Boolean isConnect) {
        AppClient.isConnect = isConnect;
    }

    public static Thread getListenToServerThread() {
        return listenToServerThread;
    }

    public static void setListenToServerThread(Thread listenToServerThread) {
        AppClient.listenToServerThread = listenToServerThread;
    }
}