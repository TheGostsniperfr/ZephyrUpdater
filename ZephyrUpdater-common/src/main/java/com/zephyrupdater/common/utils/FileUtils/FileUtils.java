package com.zephyrupdater.common.utils.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.zephyrupdater.common.utils.FileUtils.HashUtils.HashAlgo;
import com.zephyrupdater.common.utils.FileUtils.HashUtils.HashAlgoType;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

        //System.out.println("raw data: " + byteArrayOutputStream.toString(StandardCharsets.UTF_8));
        return JsonParser.parseString(byteArrayOutputStream.toString(StandardCharsets.UTF_8)).getAsJsonObject();
    }

    public static JsonObject loadJsonFromFilePath(Path filePath){
        try {
            return loadJsonFromStream(Files.newInputStream(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonObject loadJsonFromUrl(URL url)  {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                return gson.fromJson(response.toString(), JsonObject.class);
            } else {
                throw new IOException("Failed to load json from URL. Response code: " + responseCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonObject loadJsonFromUrl(String url){
        try{
            return loadJsonFromUrl(new URL(url));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveJsonAt(JsonObject jsonObject, Path filePath){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(filePath.toFile())) {
            if(!Files.exists(filePath.getParent())){
                Files.createDirectories(filePath.getParent());
            }

            JsonWriter jsonWriter = new JsonWriter(fileWriter);
            jsonWriter.setIndent("\t");
            gson.toJson(jsonObject, jsonWriter);
        } catch (IOException e) {
            e.printStackTrace();
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

    public static List<File> getRecursiveFilesFromDirPath(Path targetDirPath){
        File targetDir = new File(targetDirPath.toUri());
        if(!targetDir.exists() | !targetDir.isDirectory()){
            throw new RuntimeException("Invalid path: " + targetDirPath);
        }

        List<File> listedFile = new ArrayList<>();

        for(File file : targetDir.listFiles()){
            if(file.isFile()){
                listedFile.add(file);
                continue;
            }
            listedFile.addAll(getRecursiveFilesFromDirPath(file.toPath().toAbsolutePath()));
        }

        return listedFile;
    }

    public static Path getRelativePathFromAbs(Path absPath, Path basePath){
        return basePath.relativize(absPath);
    }

    /**
     * Check if a file is still the same by checking his hash
     *
     * @param absFilePath absolute path to the file
     * @param hash current file hash
     * @param hashAlgoType current file algo type
     * @return true is the file is the same, else false
     */
    public static Boolean isSameFile(Path absFilePath, String hash, HashAlgoType hashAlgoType){
        if(!Files.exists(absFilePath) | Files.isDirectory(absFilePath)){
            return false;
        }

        return HashAlgo.getHashFromFilePath(absFilePath, hashAlgoType).equals(hash);
    }

    public static Boolean isValidTargetDirPath(Path targetDirPath){
        return Files.exists(targetDirPath) && Files.isDirectory(targetDirPath);
    }

    public static Boolean isValidTargetDirPath(String targetDirStrPath){
        return isValidTargetDirPath(Paths.get(targetDirStrPath));
    }
}
