package com.zephyrupdater.common.ZUProtocol;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPCommand;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPEndPoint;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPFile;
import com.zephyrupdater.common.ZUProtocol.ZUProtocolTypes.ZUPMessage;

public class ZUPManager {
    /**
     * Send data to a specific socket with type header
     *
     * @param socket socket to send data
     * @param data data to send.
     */

    private static int BUFFER_SIZE = 1024;
    public static void sendData(Socket socket, ZUPStruct data){
        try {
            OutputStream outputStream = socket.getOutputStream();
            //get data header
            String dataToSend = data.getJson();
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

    public static void readData(InputStream inputStream){
        JsonObject dataHeader = readJsonFromStream(inputStream);
        String dataStrType = ZUPStruct.getValueFromJson(ZUPKeys.CONTENT.getKey(), dataHeader, String.class);

        ZUPTypes dataType = null;

        for(ZUPTypes cType : ZUPTypes.values()){
            if(cType.toString().equals(dataStrType)){
                dataType = cType;
                break;
            }
        }

        if(dataType == null){
            System.err.println("Unknown ZUPStruct type: " + dataStrType);
            return;
        }

        switch (dataType){
            case MESSAGE:
                ZUPMessage zupMessage = new ZUPMessage(dataHeader);
                System.out.println(zupMessage.content);
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
                        if(serverResp.trim().equals(ZUPEndPoint.endPointFlag)){
                            break;
                        }

                        totalBytes += bytesRead;
                        fileOutputStream.write(buffer, 0, bytesRead);

                        /*
                        //Echo debug
                        System.out.println("Server Resp: " + serverResp);
                        */
                    }

                    fileOutputStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }

                if(totalBytes != zupFile.dataSize){
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

    private static JsonObject readJsonFromStream(InputStream inputStream) {
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
