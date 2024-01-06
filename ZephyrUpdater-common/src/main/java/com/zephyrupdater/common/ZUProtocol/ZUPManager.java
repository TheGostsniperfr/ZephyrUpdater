package com.zephyrupdater.common.ZUProtocol;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPEndPoint;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPFile;

public class ZUPManager {
    private static final int BUFFER_SIZE = 1024;


    /**
     * Send data to a specific socket with type header
     *
     * @param socket socket to send data
     * @param data data to send.
     */
    public static void sendData(Socket socket, ZUPStruct data){
        try {
            OutputStream outputStream = socket.getOutputStream();
            //get data header
            String dataToSend = data.getJson();
            System.out.println("Data to send: " + dataToSend);

            outputStream.write(dataToSend.getBytes(StandardCharsets.UTF_8));

            if(data.isMultiChunks){
                if(data.getClass() == ZUPFile.class){
                    String filename = ((ZUPFile) data).fileName;
                    try {
                        FileInputStream fileInputStream = new FileInputStream(filename);
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int bytesRead;

                        while((bytesRead = fileInputStream.read(buffer)) != -1){
                            outputStream.write(buffer, 0, bytesRead);
                        }

                    } catch (FileNotFoundException e){
                        System.err.println("File: " + filename + "not found.");
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                //send end multi chunks point
                outputStream.write(new ZUPEndPoint().getJson().getBytes(StandardCharsets.UTF_8));
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void readData(Socket socket){

    }

    public static JsonObject readJsonFromStream(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);

                if (bytesRead < BUFFER_SIZE) {
                    break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return JsonParser.parseString(byteArrayOutputStream.toString(StandardCharsets.UTF_8)).getAsJsonObject();
    }
}
