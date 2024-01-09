package com.zephyrupdater.server.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCDisconnection;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCLogin;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCMessage;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPManager;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPCommand;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPEndPoint;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPFile;
import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.client.Auth.ClientAuth;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler implements Runnable {
    private static final int BUFFER_SIZE = 1024;
    public final Socket clientSocket;
    private Boolean isConnect = true;

    public ClientHandler(Socket socket) {
        AppServer.clients.add(this);
        this.clientSocket = socket;
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

            while (isConnect) {
                JsonObject dataHeader = ZUPManager.readJsonFromStream(inputStream);
                if (dataHeader == null) return;

                String zupName = CommonUtil.getValueFromJson(
                        ZUPKeys.STRUCT_TYPE.getKey(),
                        dataHeader,
                        String.class
                );

                ZUPTypes dataType = ZUPManager.findZUPTypesByName(zupName);
                if(dataType == null) continue;

                switch (dataType) {
                    case COMMAND:

                        executeClientCmd(new ZUPCommand(dataHeader));
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
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(false);
        }
    }

    public void disconnect(Boolean propagate){
        if(this.clientSocket.isClosed()){
            return;
        }
        try {
            this.isConnect = false;
            if(propagate) {
                ZUPManager.sendData(this.clientSocket, new ZUPCommand(new ZUCDisconnection()));
            }
            this.clientSocket.close();
            AppServer.clients.remove(this);
            System.out.println("Client: " + this.getHost() + " has been disconnected.");
            System.out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getHost(){
        return this.clientSocket.getInetAddress().getHostAddress();
    }

    private void executeClientCmd(ZUPCommand zupCommand){
        ZUCTypes zucTypes = zupCommand.cmdStructType;
        JsonObject data = JsonParser.parseString(zupCommand.content).getAsJsonObject();

        switch (zucTypes){
            case LOGIN:
                if(!ClientAuth.isValidAccount(new ZUCLogin(data))){
                    this.isConnect = false;
                }
                break;
            case MESSAGE:
                ZUCMessage zucMessage = new ZUCMessage(data);

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH:mm:ss");

                System.out.println(dateFormat.format(currentDate)
                        + " from "
                        + this.getHost()
                        + " -> " + zucMessage.content);

                break;
            case DISCONNECTION:
                this.isConnect = false;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
