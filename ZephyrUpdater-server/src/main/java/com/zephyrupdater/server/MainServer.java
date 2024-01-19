package com.zephyrupdater.server;

import com.zephyrupdater.common.FileUtils.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainServer {

    public static Path publicDirPath;
    public static Path cacheDirPath;
    public static final Path curseModJsonPath = publicDirPath.resolve("curseModList.json");

    public static void main(String[] args){
        setRuntimeDirPath();
        System.out.println(publicDirPath);

        new AppServer().launchServer();
    }

    private static void setRuntimeDirPath(){
        String path = MainServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File serverClassFile = new File(path);
        MainServer.publicDirPath = Paths.get(serverClassFile.getParentFile().getAbsolutePath()).resolve("public");
        MainServer.cacheDirPath = Paths.get(serverClassFile.getParentFile().getAbsolutePath()).resolve("cache");

        FileUtils.createDirIfNotExist(MainServer.publicDirPath);
        FileUtils.createDirIfNotExist(MainServer.cacheDirPath);
    }

}