package com.zephyrupdater.common.ZUCommand;

public enum ZUCKeys {
    ID("id"),
    PASSWORD("password"),
    CONTENT("content");


    private final String key;

    ZUCKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
