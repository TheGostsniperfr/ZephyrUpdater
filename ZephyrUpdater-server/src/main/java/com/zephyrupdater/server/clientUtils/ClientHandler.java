package com.zephyrupdater.server.clientUtils;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.FileUtils.FileUtils;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCDisconnectionCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCMessageCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPKeys;
import com.zephyrupdater.common.ZUProtocolCore.ZUPManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUPTypes;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;
import com.zephyrupdater.server.AppServer;
import com.zephyrupdater.server.ZUProtocol.ZUPStruct;
import com.zephyrupdater.server.ZUProtocol.ZUProtocolTypes.ZUPCommand;
import com.zephyrupdater.server.ZUProtocol.ZUProtocolTypes.ZUPFile;

import java.io.InputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static final int BUFFER_SIZE = 1024;
    public final Socket clientSocket;
    private Boolean isConnect = true;
    private String clientId = "NOT_LOGGED";
    private Boolean isAuth = false;
    public static final String MSG_PLS_LOGIN = "Please login before.";

    public ClientHandler(Socket socket) {
        AppServer.clients.add(this);
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = this.clientSocket.getInputStream();

            while (isConnect) {
                JsonObject dataHeader = FileUtils.loadJsonFromStream(inputStream);
                if (dataHeader == null) return; // -> deco client
                clientDataHeaderProcess(dataHeader);
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
                ZUPManager.sendData(this.clientSocket, new ZUPCommandCore(new ZUCDisconnectionCore()));
            }
            this.clientSocket.close();
            AppServer.clients.remove(this);
            System.out.println("Client: " + this.getHost() + " has been disconnected.");
            System.out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clientDataHeaderProcess(JsonObject dataHeader){
        String zupName = CommonUtil.getValueFromJson(
                ZUPKeys.STRUCT_TYPE.getKey(),
                dataHeader,
                String.class
        );

        ZUPTypes dataType = ZUPManager.findZUPTypesByName(zupName);
        if(dataType == null) return;
        ZUPStruct zupStruct;

        switch (dataType) {
            case COMMAND:
                zupStruct = new ZUPCommand(dataHeader);
                break;
            case FILE:
                zupStruct = new ZUPFile(dataHeader);
                break;
            default:
                System.err.println("Invalid arg read: " + dataType);
                return;
        }

        if(!this.isAuth && dataType != ZUPTypes.COMMAND){
            sendMsgToClient(ClientHandler.MSG_PLS_LOGIN);
            return;
        }
        zupStruct.execute(this);
    }

    public void setIsConnect(Boolean state){
        this.isConnect = state;
    }
    public String getHost(){
        return this.clientSocket.getInetAddress().getHostAddress();
    }
    public Boolean getIsAuth() { return this.isAuth; }
    public void setIsAuth(Boolean state) { this.isAuth = state; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getClientId() { return clientId; }
    public void sendCmdToClient(ZUCStructCore zucStruct){
        if(this.isConnect) {
            ZUPManager.sendData(this.clientSocket, new ZUPCommandCore(zucStruct));
        }
    }
    public void sendMsgToClient(String msg){
        sendCmdToClient(new ZUCMessageCore(msg));
    }

    public static ClientHandler getClientById(String clientId){
        for(ClientHandler client : AppServer.clients){
            if(client.getIsAuth() && client.getClientId().equals(clientId)){
                return client;
            }
        }

        return null;
    }
}
