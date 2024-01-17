package com.zephyrupdater.client.Updater.CurseForgeModUpdater;

public enum CURSE_KEY {
    FILE_ID("fileId"),
    PROJECT_ID("projectId"),
    DATA("data"),
    FILE_NAME("fileName"),
    HASH("hashes"),
    VALUE("value"),
    SIZE("fileLength"),
    URL("downloadUrl");

    private final String key;

    CURSE_KEY(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
