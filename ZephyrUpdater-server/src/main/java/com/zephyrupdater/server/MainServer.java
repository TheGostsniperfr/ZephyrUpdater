package com.zephyrupdater.server;

import com.zephyrupdater.common.FileUtils.CurseForgeUtils;

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

        Path modsDir = publicFilesPath.resolve("mods");
        //CurseForgeUtils.createBlankJsonModList(10, publicFilesPath.resolve("mods"), "modList.json");
        CurseForgeUtils.downloadModList(modsDir.resolve("modList.json"), modsDir);

        //new AppServer().launchServer();
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