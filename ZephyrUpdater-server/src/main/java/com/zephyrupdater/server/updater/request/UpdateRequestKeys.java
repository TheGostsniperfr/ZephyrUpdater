package com.zephyrupdater.server.updater.request;

public enum UpdateRequestKeys {
    REQUEST_NAME("name"),
    TARGET_RELATIVE_PATH("targetPath"),
    HASH("hash"),
    HASH_ALGO("hashAlgo");
    private final String key;

    UpdateRequestKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
    
}
