package com.zephyrupdater.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainServer {

    public static Path publicFilesPath;
    public static void main(String[] args){
        getPublicFilesPath();
        System.out.println(publicFilesPath);
        //System.out.println(CheckingFiles.getFilesJson(publicFilesPath));

        new AppServer().launchServer();
    }

    private static void getPublicFilesPath(){
        String path = MainServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File publicFiles = new File(path);
        publicFilesPath = Paths.get(publicFiles.getParentFile().getAbsolutePath()).resolve("public");

        if(!Files.exists(publicFilesPath)){

            try {
                Files.createDirectories(publicFilesPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}