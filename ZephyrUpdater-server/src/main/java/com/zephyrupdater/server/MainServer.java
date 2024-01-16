package com.zephyrupdater.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainServer {

    public static Path publicFilesPath = getPublicFilesPath();
    public static final Path curseModJsonPath = publicFilesPath.resolve("curseModList.json");

    public static void main(String[] args){
        System.out.println(publicFilesPath);

        new AppServer().launchServer();
    }

    private static Path getPublicFilesPath(){
        String path = MainServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File publicFiles = new File(path);
        Path publicFilesPath = Paths.get(publicFiles.getParentFile().getAbsolutePath()).resolve("public");

        if(!Files.exists(publicFilesPath)){

            try {
                Files.createDirectories(publicFilesPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return publicFilesPath;
    }
}