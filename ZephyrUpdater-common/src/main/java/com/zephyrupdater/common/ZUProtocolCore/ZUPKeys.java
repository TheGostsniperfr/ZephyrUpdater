package com.zephyrupdater.common.ZUProtocolCore;

public enum ZUPKeys {
    STRUCT_TYPE("structType"),
    DATA_SIZE("dataSize"),
    CONTENT("content"),
    COMMAND("cmd"),
    REQUEST("request"),
    FILE_PATH("filePath"),
    EXT_FILES_JSON("extFilesJson"),
    CURSE_MOD_JSON("curseModJson");

    private final String key;

    ZUPKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}