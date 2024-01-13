package com.zephyrupdater.server;

import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.server.updater.CheckingFiles;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class MainServer {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

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
        System.out.println(CheckingFiles.getFilesJson(serverDir));



        new AppServer().launchServer();
    }
}