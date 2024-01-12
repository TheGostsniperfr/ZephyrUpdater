package com.zephyrupdater.server;

import com.zephyrupdater.common.CommonUtil;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainServer {
    public static void main(String[] args) {
        /*
        String decodedPath = MainServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            decodedPath = URLDecoder.decode(decodedPath, StandardCharsets.UTF_8);
            Path publicDir = Paths.get(decodedPath);

            if(!Files.exists(publicDir)){
                Files.createDirectories(publicDir.getParent());
            }

            System.out.println("PublicDirPath: " + publicDir);

        }catch (Exception e){
            e.printStackTrace();
        }*/

        new AppServer().launchServer();
    }
}