package com.zephyrupdater.client;

import com.zephyrupdater.common.OsSpec;

import java.nio.file.Path;

public class MainClient {
    public static final String GAME_DIR_NAME = ".Arffornia.V.5";

    public static final String GAME_VERSION = "1.20.1";
    public static Path gameDirPath;
    public static OsSpec osSpec;
    public static void main(String[] args) {
        osSpec = new OsSpec();
        osSpec.printSpec();
        gameDirPath = osSpec.getAppdataPath().resolve(GAME_DIR_NAME);
        AppClient.launchClient();
    }
}
