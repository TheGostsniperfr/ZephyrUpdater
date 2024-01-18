package com.zephyrupdater.common.ZUProtocolCore;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class ZUPManager {
    private static final int BUFFER_SIZE = 1024;

    /**
     * Send data to a specific socket with type header
     *
     * @param socket socket to send data
     * @param data data to send.
     */
    public static void sendData(Socket socket, ZUPStructCore data){
        try {

            OutputStream outputStream = socket.getOutputStream();
            //get data header
            String dataToSend = data.getJson();
            //System.out.println("Data to send: " + dataToSend);

            outputStream.write(dataToSend.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void readData(Socket socket){

    }

    public static JsonObject readJsonFromStream(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);

                if (bytesRead < BUFFER_SIZE) {
                    break;
                }
            }
        } catch (SocketException e){
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(bytesRead == -1 || bytesRead == 0){
            return null;
        }
        //System.out.println("Byte read: " + bytesRead);
        //System.out.println(byteArrayOutputStream.toString(StandardCharsets.UTF_8));
        return JsonParser.parseString(byteArrayOutputStream.toString(StandardCharsets.UTF_8)).getAsJsonObject();
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
