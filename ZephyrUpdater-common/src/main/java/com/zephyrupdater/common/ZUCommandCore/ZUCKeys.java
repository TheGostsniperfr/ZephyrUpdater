package com.zephyrupdater.common.ZUCommandCore;

public enum ZUCKeys {
    ID("id"),
    PASSWORD("password"),
    CONTENT("content"),
    FILE_NANE("fileName"),
    RELATIVE_FILE_PATH("relativeFilePath");

    private final String key;

    ZUCKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
