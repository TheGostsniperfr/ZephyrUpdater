package com.zephyrupdater.client.Updater.ExternalFilesUpdater.ExternalFilesUtils;

public enum ExtFilesKeys {
    FILE_NAME("filename"),
    HASH("hash"),
    HASH_ALGO("hashAlgo");

    private final String key;

    ExtFilesKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
