package com.zephyrupdater.common.FileUtils.HashUtils;

public enum HASH_ALGO_TYPE {
    SHA256("SHA-256"),
    MD5("MD5");

    private final String key;

    HASH_ALGO_TYPE(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
