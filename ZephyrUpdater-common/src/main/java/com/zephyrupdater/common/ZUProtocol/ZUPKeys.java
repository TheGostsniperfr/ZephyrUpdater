package com.zephyrupdater.common.ZUProtocol;

public enum ZUPKeys {
    STRUCT_TYPE("structType"),
    DATA_SIZE("dataSize"),
    CONTENT("content"),
    COMMAND("cmd");

    private final String key;

    ZUPKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}