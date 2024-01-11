package com.zephyrupdater.server.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCDisconnection;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCLogin;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCMessage;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;
import com.zephyrupdater.common.ZUFile.FileManager;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPManager;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPCommand;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPFile;
import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.client.Auth.ClientAuth;

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
            InputStream inputStream = clientSocket.getInputStream();

            while (isConnect) {
                JsonObject dataHeader = ZUPManager.readJsonFromStream(inputStream);
                if (dataHeader == null) return; // -> deco client
                ClientProcess.clientDataHeaderProcess(this, dataHeader);
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

    public void setIsConnect(Boolean state){
        this.isConnect = state;
    }

    public String getHost(){
        return this.clientSocket.getInetAddress().getHostAddress();
    }
}
