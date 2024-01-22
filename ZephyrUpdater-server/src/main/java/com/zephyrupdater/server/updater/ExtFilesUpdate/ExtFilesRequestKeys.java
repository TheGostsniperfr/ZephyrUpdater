package com.zephyrupdater.server.updater.ExtFilesUpdate;

public enum ExtFilesRequestKeys {
    FILES("files");
    private final String key;

    ExtFilesRequestKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
    
}
