package com.zephyrupdater.client.utils;

public enum ZUCacheKeys {
    GAME_INDEX("gameIndex");

    private final String key;

    ZUCacheKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

}
