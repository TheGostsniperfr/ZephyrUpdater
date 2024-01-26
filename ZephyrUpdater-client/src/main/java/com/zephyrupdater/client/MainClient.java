package com.zephyrupdater.client;

import com.zephyrupdater.common.OsSpec;

import java.nio.file.Files;
import java.nio.file.Path;

public class MainClient {
    public static final String GAME_DIR_NAME = ".Arffornia.V.5";
    public static Path gameDirPath;
    public static OsSpec osSpec;
    public static void main(String[] args) {
        osSpec = new OsSpec();
        gameDirPath = osSpec.getAppdataPath().resolve(GAME_DIR_NAME);
        checkGameFolder();
        AppClient.launchClient();
    }

    private static void checkGameFolder(){
        System.out.println("Appdata folder:" + gameDirPath);
        try{
            if(!Files.exists(gameDirPath)){
                Files.createDirectories(gameDirPath);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
