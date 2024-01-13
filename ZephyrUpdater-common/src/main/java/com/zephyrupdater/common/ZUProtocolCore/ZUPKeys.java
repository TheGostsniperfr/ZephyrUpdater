package com.zephyrupdater.common.ZUProtocolCore;

public enum ZUPKeys {
    STRUCT_TYPE("structType"),
    DATA_SIZE("dataSize"),
    CONTENT("content"),
    FILE_NAME("filename"),
    COMMAND("cmd"),
    FOLDER_PATH("folderPath"),
    FILES_JSON("filesJson");

    private final String key;

    ZUPKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}