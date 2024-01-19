package com.zephyrupdater.common.FileUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    private static final int BUFFER_SIZE = 1024;

    public static JsonObject loadJsonFromStream(InputStream inputStream){
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
            throw new RuntimeException(e);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(bytesRead == -1 || bytesRead == 0){
            throw new RuntimeException("Error to read stream ");
        }

        return JsonParser.parseString(byteArrayOutputStream.toString(StandardCharsets.UTF_8)).getAsJsonObject();
    }

    public static JsonObject loadJsonFromFilePath(Path filePath){
        try {
            return loadJsonFromStream(Files.newInputStream(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createDirIfNotExist(Path path){
        if(!Files.exists(path)){
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
