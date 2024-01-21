package com.zephyrupdater.server;

import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.server.clientUtils.ClientHandler;
import com.zephyrupdater.server.serverCmd.CmdManager;
import com.zephyrupdater.server.updater.CurseForgeUpdater;
import com.zephyrupdater.server.updater.ExtFilesUpdate.ExtFilesUpdater;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class AppServer {
    // List of all client connected to the server.
    public static List<ClientHandler> clients = new ArrayList<>();
    private static ExtFilesUpdater extFilesUpdater;
    private static CurseForgeUpdater curseForgeUpdater;

    public void launchServer() {
        extFilesUpdater = new ExtFilesUpdater();
        curseForgeUpdater = new CurseForgeUpdater();

        try {
            ServerSocket serverSocket = new ServerSocket(CommonUtil.SERVER_PORT);
            System.out.println("Waiting for connexion...");

            //command server thread:
            Thread consoleListenerThread = new Thread(() -> CmdManager.listenToConsole(serverSocket));
            consoleListenerThread.start();

            while (true) {
                //wait client conn:
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connexion: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);

                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static ExtFilesUpdater getUpdateRequestManager() {
        return extFilesUpdater;
    }
    public static CurseForgeUpdater getCurseForgeUpdater() {
        return curseForgeUpdater;
    }
}