package com.zephyrupdater.server.updater;

import java.io.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Arrays;


public class CheckingFiles {
    public static String getFilesJson(Path filesParentPath) throws IOException, NoSuchAlgorithmException {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonVal = new JsonObject();

        File directory = new File("ZephyrUpdater-server/build/classes/java/public");
        File[] list = directory.listFiles();



        if (list != null) {
            System.out.println("files list :");


            for (File file : list) {
                byte[] fileContentBytes = Files.readAllBytes(Paths.get(file.getPath()));
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(fileContentBytes);




                String path = file.getPath();
                String name = file.getName();

                if (file.isFile()) {
                    System.out.println("name : " + name);
                    System.out.println("path : " + path);
                    System.out.println("hash : " + Arrays.toString(hash));

                    jsonVal.addProperty(path, Arrays.toString(hash));
                    jsonObject.add(name, jsonVal);

                }
            }
            System.out.println("------------------------------------");
            return jsonObject.toString();
        }
        else{
            System.out.println("No such file or directory...");
        }
        return null;
    }
}
