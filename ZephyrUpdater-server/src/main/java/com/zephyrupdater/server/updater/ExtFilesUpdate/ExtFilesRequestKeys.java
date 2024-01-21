package com.zephyrupdater.server.updater.ExtFilesUpdate;

public enum ExtFilesRequestKeys {
    REQUEST_alias("alias"),
    FILES("files"),
    DB_NAME("request");
    private final String key;

    ExtFilesRequestKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
    
}
