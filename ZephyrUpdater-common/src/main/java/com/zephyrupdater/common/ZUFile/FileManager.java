package com.zephyrupdater.common.ZUFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCGetFileCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPFileCore;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {

    private static final int BUFFER_SIZE = 8192;

    public static void createFileFromStream(Socket socket, ZUPFileCore zupFile, Path downloadDirPath) {
        Path filePath = downloadDirPath.resolve(zupFile.filePath);
        File file = new File(filePath.toUri());
        System.out.println("Downloading: " + file.getName() + " at: " + filePath);
        long totalBytes = 0;

        try {
            if(!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }
            InputStream inputStream = socket.getInputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            BufferedOutputStream bufferedOutputStream =
                         new BufferedOutputStream(new FileOutputStream(filePath.toString()));

            while (totalBytes < zupFile.getDataSize() && (bytesRead = inputStream.read(buffer)) != -1) {
                totalBytes += bytesRead;
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }

            bufferedOutputStream.close();

            System.out.println("Success to download: " + file.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendFileFromStream(Socket socket, ZUCGetFileCore fileCmd){

        File fileToSend = new File(fileCmd.absFilePath.toUri());
        if(!fileToSend.exists()){
            System.err.println("File does not exist.");
            return;
        }

        ZUPManager.sendData(socket, new ZUPFileCore(fileCmd.relativeFilePath.toString(), fileToSend.length()));

        try {
            OutputStream outputStream = socket.getOutputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileToSend));
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, bytesRead);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static File findFolderStartingByWith(Path pathToSearch, String startName){
        File dir = new File(pathToSearch.toUri());
        if(dir.exists() && dir.isDirectory()) {
            for (File currentFile : dir.listFiles()) {
                if (currentFile.isDirectory() && currentFile.getName().startsWith(startName)){
                    return currentFile;
                }
            }
        }

        return null;
    }

    public static void saveJsonAt(JsonObject jsonObject, Path filepath, String filename){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter fileWriter = new FileWriter(filepath.resolve(filename).toFile())) {
            JsonWriter jsonWriter = new JsonWriter(fileWriter);
            jsonWriter.setIndent("\t");
            gson.toJson(jsonObject, jsonWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonObject createJsonObjectFromFile(Path filePath){
        try (FileReader fileReader = new FileReader(filePath.toFile())) {
            JsonParser jsonParser = new JsonParser();
            return jsonParser.parse(fileReader).getAsJsonObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}