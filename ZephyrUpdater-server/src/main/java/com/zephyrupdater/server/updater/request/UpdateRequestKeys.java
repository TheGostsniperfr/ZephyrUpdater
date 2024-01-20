package com.zephyrupdater.server.updater.request;

public enum UpdateRequestKeys {
    REQUEST_alias("alias"),
    FILES("files"),
    DB_NAME("request");
    private final String key;

    UpdateRequestKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
    
}
