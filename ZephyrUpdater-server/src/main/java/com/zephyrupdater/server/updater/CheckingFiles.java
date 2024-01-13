package com.zephyrupdater.server.updater;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class CheckingFiles {
    public static String getFilesJson(Path filesParentPath) throws IOException, NoSuchAlgorithmException {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonVal = new JsonObject();

        File directory = new File(filesParentPath.toString());
        File[] list = directory.listFiles();

        if (list.length != 0) {
            System.out.println("files list :");


            for (File file : list) {
                System.out.println("############");
                if(file.isDirectory()){
                    System.out.println("Directory: " + file.getName());
                    continue;
                }

                System.out.println("File: " + file.getName());


                byte[] fileContentBytes = Files.readAllBytes(Paths.get(file.getPath()));
                byte[] hash = MessageDigest.getInstance("SHA-256").digest(fileContentBytes);

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
