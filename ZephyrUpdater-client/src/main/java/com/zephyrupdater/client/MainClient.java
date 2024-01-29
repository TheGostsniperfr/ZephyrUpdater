package com.zephyrupdater.client;

import com.zephyrupdater.client.Updater.McUpdaterManager;
import com.zephyrupdater.common.OsSpec;

import java.nio.file.Path;

public class MainClient {
    public static Path gameDirPath;
    public static OsSpec osSpec;

    public static McUpdaterManager arfforniaV5Updater;
    public static void main(String[] args) {
        osSpec = new OsSpec();
        osSpec.printSpec();

        arfforniaV5Updater = new McUpdaterManager(".Arffornia_V.5", "1.20.1", osSpec);


        AppClient.launchClient();
    }
}
