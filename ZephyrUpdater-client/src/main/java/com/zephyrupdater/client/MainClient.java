package com.zephyrupdater.client;

import com.zephyrupdater.client.games.gameList.McGameManager;
import com.zephyrupdater.client.networkClient.ZephyrNetClient;

public class MainClient {
    public static void main(String[] args) {
        ZephyrUpdater zephyrUpdater = new ZephyrUpdater();
        zephyrUpdater.addGame(new McGameManager(".Arffornia_V.5", "1.20.1", "17.0.9", zephyrUpdater));

        zephyrUpdater.init();

        zephyrUpdater.sendNetCmd("connect 2 2");

        while(ZephyrNetClient.getIsConnect() != true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        zephyrUpdater.updateCurrentGame();
    }
}
