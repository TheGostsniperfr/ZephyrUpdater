package com.zephyrupdater.server.client;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCList.ZUCDisconnectionCore;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;
import com.zephyrupdater.common.ZUProtocol.ZUPManager;
import com.zephyrupdater.common.ZUProtocol.ZUPTypes;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPCommandCore;
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

    public ClientHandler(Socket socket) {
        AppServer.clients.add(this);
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = this.clientSocket.getInputStream();

            while (isConnect) {
                JsonObject dataHeader = ZUPManager.readJsonFromStream(inputStream);
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

        zupStruct.execute(this);
    }

    public void setIsConnect(Boolean state){
        this.isConnect = state;
    }

    public String getHost(){
        return this.clientSocket.getInetAddress().getHostAddress();
    }
}
