package com.zephyrupdater.client;

import com.zephyrupdater.common.OSType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainClient {
    public static final String GAME_DIR_NAME = ".Arffornia.V.5";
    public static Path gameDirPath = getAppDataRoamingPath().resolve(GAME_DIR_NAME);
    public static OSType CLIENT_OS;
    public static void main(String[] args) {
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

    private static Path getAppDataRoamingPath() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            CLIENT_OS = OSType.WINDOWS;
            return Paths.get(System.getenv("APPDATA"));
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            if(os.contains("mac")){
                CLIENT_OS = OSType.MAC;
            }else{
                CLIENT_OS = OSType.LINUX;
            }
            return Paths.get(System.getProperty("user.home"));
        } else {
            System.out.println("OS not supported.");
            return null;
        }
    }
}
