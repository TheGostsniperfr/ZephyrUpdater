package com.zephyrupdater.server;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPManager;
import com.zephyrupdater.common.ZUProtocol.ZUPStruct;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPCommand;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPEndPoint;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPFile;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPMessage;
import com.zephyrupdater.server.serverCmd.CmdManager;

import java.io.FileOutputStream;
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
                InputStream inputStream;
                try {
                    inputStream = clientSocket.getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                do {
                    JsonObject dataHeader = ZUPManager.readJsonFromStream(inputStream);
                    String dataStrType = ZUPStruct.getValueFromJson(ZUPKeys.STRUCT_TYPE.getKey(), dataHeader, String.class);

                    ZUPTypes dataType = null;

                    for (ZUPTypes cType : ZUPTypes.values()) {
                        if (cType.toString().equals(dataStrType)) {
                            dataType = cType;
                            break;
                        }
                    }

                    if (dataType == null) {
                        System.err.println("Unknown ZUPStruct type: " + dataStrType);
                        return;
                    }

                    switch (dataType) {
                        case MESSAGE:
                            ZUPMessage zupMessage = new ZUPMessage(dataHeader);

                            Date currentDate = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH:mm:ss");

                            System.out.println(dateFormat.format(currentDate)
                                    + " from "
                                    + clientSocket.getInetAddress()
                                    + " -> " + zupMessage.content);
                            break;

                        case COMMAND:
                            ZUPCommand zupCommand = new ZUPCommand(dataHeader);

                            /* TODO */
                            System.out.println(zupCommand.content);
                            break;

                        case FILE:
                            ZUPFile zupFile = new ZUPFile(dataHeader);
                            long totalBytes = 0;

                            //get multi chunks data
                            try {
                                byte[] buffer = new byte[BUFFER_SIZE];
                                int bytesRead;

                                FileOutputStream fileOutputStream = new FileOutputStream(zupFile.fileName);


                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    String serverResp = new String(buffer, 0, bytesRead);
                                    if (serverResp.trim().equals(ZUPEndPoint.endPointFlag)) {
                                        break;
                                    }

                                    totalBytes += bytesRead;
                                    fileOutputStream.write(buffer, 0, bytesRead);
                                }

                                fileOutputStream.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (totalBytes != zupFile.dataSize) {
                                /*
                                    TODO

                                    Delete the file or create a tmp folder (if the data is corrupted)
                                */

                                System.err.println("Error: Data corrupted: " + (zupFile.dataSize - totalBytes) + " bytes missing");
                            }

                            break;

                        case END_POINT:
                            System.err.println("Invalid end point transfer detect.");
                            break;

                        default:
                            throw new IllegalArgumentException();
                    }
                }while(true);
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