package com.zephyrupdater.server;

import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.server.commands.CmdManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AppServer {
    private static final int BUFFER_SIZE = 1024;

    public static boolean isServerRunning = true;

    public static final List<Socket> activeConnections = new ArrayList<>();
    public static void launchServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(CommonUtil.SERVER_PORT);
            System.out.println("Waiting for connexion...");

            //commande server thread:
            Thread consoleListenerThread = new Thread(() -> CmdManager.listenToConsole(serverSocket));
            consoleListenerThread.start();

            while (isServerRunning) {
                //wait client conn:
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connexion: " + clientSocket.getInetAddress());

                activeConnections.add(clientSocket);

                //create thread:
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private static void handleClient(Socket clientSocket){
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputSteam = clientSocket.getOutputStream();


            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while(isServerRunning && !clientSocket.isClosed() && (bytesRead = inputStream.read(buffer)) != -1)
            {
                String data = new String(buffer, 0, bytesRead);

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH:mm:ss");

                if(data.trim().equals("exit")){
                    System.out.println(
                        dateFormat.format(currentDate)
                        + clientSocket.getInetAddress()
                        + " Disconnected."
                    );

                    break;
                }

                String rep = (
                    dateFormat.format(currentDate)
                    + " Msg from "
                    + clientSocket.getInetAddress()
                    + " -> " + data
                );

                System.out.println(rep);
                outputSteam.write(rep.getBytes(StandardCharsets.UTF_8));
            }

            if(!clientSocket.isClosed()) {
                inputStream.close();
                outputSteam.close();
                clientSocket.close();
                activeConnections.remove(clientSocket);
            }


        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}