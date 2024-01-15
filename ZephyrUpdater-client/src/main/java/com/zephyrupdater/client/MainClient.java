package com.zephyrupdater.client;

import com.zephyrupdater.client.Updater.UpdaterManager;
import com.zephyrupdater.common.OSType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainClient {
    public static final String GAME_DIR_NAME = ".Arffornia.V.5";
    public static Path clientFilePath = getAppDataRoamingPath().resolve(GAME_DIR_NAME);
    public static OSType CLIENT_OS;
    public static void main(String[] args) {
        checkGameFolder();
        UpdaterManager.update();
        AppClient.launchClient();
    }

    private static void checkGameFolder(){
        System.out.println("Appdata folder:" + clientFilePath);
        try{
            if(!Files.exists(clientFilePath)){
                Files.createDirectories(clientFilePath);
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
