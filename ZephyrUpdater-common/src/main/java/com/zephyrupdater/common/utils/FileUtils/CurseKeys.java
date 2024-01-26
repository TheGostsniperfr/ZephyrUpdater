package com.zephyrupdater.common.utils.FileUtils;

public enum CurseKeys {
    FILE_ID("fileId"),
    PROJECT_ID("projectId"),
    DATA("data"),
    FILE_NAME("fileName"),
    HASH("hashes"),
    VALUE("value"),
    SIZE("fileLength"),
    URL("downloadUrl");

    private final String key;

    CurseKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
