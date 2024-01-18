package com.zephyrupdater.client.Updater.ExternalFilesUpdater.ExternalFilesUtils;

public enum EXTERNAL_FILE_KEY {
    FILE_NAME("filename"),
    HASH("hash"),
    HASH_ALGO("hashAlgo");

    private final String key;

    EXTERNAL_FILE_KEY(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
