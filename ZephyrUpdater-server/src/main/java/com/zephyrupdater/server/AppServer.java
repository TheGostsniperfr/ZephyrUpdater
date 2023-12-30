package com.zephyrupdater.server;

import com.zephyrupdater.common.CommonUtil;

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
import java.util.Scanner;


public class AppServer {
    private static final int BUFFER_SIZE = 1024;

    private static final List<Socket> activeConnections = new ArrayList<>();
    public static void launchServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(CommonUtil.SERVER_PORT);
            System.out.println("Waiting for connexion...");

            //commande server thread:
            Thread consoleListenerThread = new Thread(() -> listenToConsole(serverSocket));
            consoleListenerThread.start();

            while (true) {
                //wait client conn:
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connexion: " + clientSocket.getInetAddress());

                activeConnections.add(clientSocket);

                //create thread:
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void listenToConsole(ServerSocket serverSocket) {
        Scanner scanner = new Scanner(System.in);
        while(true){
            String cmd = scanner.nextLine();
            if(cmd.trim().equals("stop")){
                sendCmdToAllClients("serverStop");
                closeAllConnections();
                System.exit(0);
            }
            else if(cmd.trim().equals("list")){
                System.out.println("Total connections: " + activeConnections.size());
                int nb = 0;
                for(Socket socket : activeConnections){
                    System.out.println(
                            String.format("Client %02d: %s", nb++, socket.getInetAddress().getHostAddress()));
                }

            }else{
                System.out.println("Unknown command.");
            }
        }
    }

    private static void sendCmdToAllClients(String cmd) {
        for(Socket socket : activeConnections){
            try{
                if (!socket.isClosed()) {
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(cmd.getBytes(StandardCharsets.UTF_8));
                }
            } catch (IOException e){
                throw new RuntimeException(e.getMessage());
            }
        }
    }


    private static void closeAllConnections() {
        for(Socket socket : activeConnections){
            try {
                if(!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnections.clear();
    }


    private static void handleClient(Socket clientSocket){
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputSteam = clientSocket.getOutputStream();


            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while((bytesRead = inputStream.read(buffer)) != -1)
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

            inputStream.close();
            outputSteam.close();
            clientSocket.close();
            activeConnections.remove(clientSocket);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
