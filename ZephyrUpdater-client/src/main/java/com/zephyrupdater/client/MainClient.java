package com.zephyrupdater.client;

import com.zephyrupdater.client.games.gameList.McGameManager;

public class MainClient {
    public static void main(String[] args) {
        ZephyrUpdater zephyrUpdater = new ZephyrUpdater();
        zephyrUpdater.addGame(new McGameManager(".Arffornia_V.5", "1.20.1", "17.0.9", zephyrUpdater));

        zephyrUpdater.init();

        zephyrUpdater.sendNetCmd("connect 2 2");



        zephyrUpdater.updateCurrentGame();
    }
}
