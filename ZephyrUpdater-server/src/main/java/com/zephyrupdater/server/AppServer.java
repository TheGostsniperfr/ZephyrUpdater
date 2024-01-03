package com.zephyrupdater.server;

import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUProtocol.ZUPManager;
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
    public static List<ClientHandler> clients = new ArrayList<>();
    public void launchServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(CommonUtil.SERVER_PORT);
            System.out.println("Waiting for connexion...");

            //commande server thread:
            Thread consoleListenerThread = new Thread(() -> CmdManager.listenToConsole(serverSocket));
            consoleListenerThread.start();

            while (true) {
                //wait client conn:
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connexion: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);

                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public class ClientHandler implements Runnable{
        public Socket clientSocket;
        public ClientHandler(Socket socket){
            this.clientSocket = socket;
        }

        public void sendMessage(String message) throws IOException {
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        }
        @Override
        public void run() {
            try {
                ZUPManager.readData(clientSocket);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            finally {
                disconnect();
            }

        }

        public void disconnect(){
            try {
                System.out.println("Test msg deco");
                sendMessage("serverStop\n");
                clientSocket.close();
                clients.remove(this);
                System.out.println("Client: " + clientSocket.getInetAddress() + " has been disconnected.");
                System.out.flush();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void sendCmdToAllClients(String cmd) {
        for (AppServer.ClientHandler client: AppServer.clients) {
            try{
                client.sendMessage(cmd);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}