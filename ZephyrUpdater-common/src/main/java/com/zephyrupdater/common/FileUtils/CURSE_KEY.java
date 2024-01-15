package com.zephyrupdater.common.FileUtils;

public enum CURSE_KEY {
    FILE_ID("fileId"),
    PROJECT_ID("projectId"),
    DATA("data"),
    FILE_NAME("fileName"),
    HASH("hashes"),
    VALUE("value"),
    SIZE("fileSizeOnDisk"),
    URL("downloadUrl");

    private final String key;

    CURSE_KEY(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
