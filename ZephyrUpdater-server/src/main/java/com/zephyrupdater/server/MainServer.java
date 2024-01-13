package com.zephyrupdater.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainServer {
    public static void main(String[] args) {

        String path = MainServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File serverFile = new File(path);
        Path serverDir = Paths.get(serverFile.getParentFile().getAbsolutePath()).resolve("public");

        if(!Files.exists(serverDir)){

            try {
                Files.createDirectories(serverDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



        System.out.println(serverDir);



        new AppServer().launchServer();
    }
}