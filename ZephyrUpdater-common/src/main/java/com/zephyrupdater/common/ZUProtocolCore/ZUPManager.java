package com.zephyrupdater.common.ZUProtocolCore;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ZUPManager {

    /**
     * Send data to a specific socket with type header
     *
     * @param socket socket to send data
     * @param data data to send.
     */
    public static void sendData(Socket socket, ZUPStructCore data){
        try {
            System.out.println("send data: " + data.getJson());
            OutputStream outputStream = socket.getOutputStream();

            outputStream.write(data.getJson().toString().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ZUPTypes findZUPTypesByName(String name){
        for (ZUPTypes cType : ZUPTypes.values()) {
            if (cType.toString().equals(name)) {
                return cType;
            }
        }
        System.err.println("Unknown ZUPStruct type: " + name);
        return null;
    }
}
