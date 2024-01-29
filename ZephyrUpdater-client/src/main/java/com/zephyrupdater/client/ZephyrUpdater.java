package com.zephyrupdater.client;

import com.zephyrupdater.client.games.GameManagerCore;
import com.zephyrupdater.client.networkClient.ZephyrNetClient;
import com.zephyrupdater.client.utils.ZUCache;
import com.zephyrupdater.common.OsSpec;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ZephyrUpdater {
    private static final String ZEPHYR_DIR_NAME = "ZephyrUpdater";
    private final Path zephyrUpdaterDir;
    private final List<GameManagerCore> gameList;
    private int currentGameIndex;
    private final OsSpec osSpec;
    private ZUCache zuCache;

    public void init(){
        if(gameList == null || gameList.isEmpty()){
            throw new RuntimeException("Empty or null game list.");
        }

        this.zuCache = new ZUCache(this);
        this.setCurrentGameIndex(zuCache.getGameIndex());

        // init net:
        ZephyrNetClient.launchClient(this);
    }

    public ZephyrUpdater(){
        this.gameList = new ArrayList<>();
        this.osSpec = new OsSpec();

        this.zephyrUpdaterDir = this.osSpec.getAppdataPath().resolve(ZEPHYR_DIR_NAME);
        FileUtils.createDirIfNotExist(this.zephyrUpdaterDir);
    }

    public void updateCurrentGame(){
        this.getCurrentGame().update();
    }

    public void launchCurrentGame(){
        this.getCurrentGame().launchGame();
    }

    public void sendNetCmd(String cmd){
        try {
            System.out.write(cmd.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Path getZephyrUpdaterDir() {
        return zephyrUpdaterDir;
    }

    public GameManagerCore getCurrentGame(){
        return gameList.get(this.currentGameIndex);
    }

    public void setCurrentGameIndex(int newGameIndex) {
        zuCache.setGameIndex(newGameIndex);
        this.currentGameIndex = newGameIndex;
    }

    public int getCurrentGameIndex() {
        return currentGameIndex;
    }

    public OsSpec getOsSpec() {
        return osSpec;
    }

    public void addGame(GameManagerCore game){
        this.gameList.add(game);
    }
}
