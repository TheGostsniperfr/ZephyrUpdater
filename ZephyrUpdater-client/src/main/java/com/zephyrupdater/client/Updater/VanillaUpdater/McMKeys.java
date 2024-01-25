package com.zephyrupdater.client.Updater.VanillaUpdater;

public enum McMKeys {
    /*  VERSIONS MANIFEST  */
    VERSION_ID("id"),
    VERSION_MANIFEST_URL("url"),
    VERSIONS("versions"),

    /*  VERSION MANIFEST  */

    LIBS("libraries"),

    /*  LIBS OBJ */

    LIB_DOWNLOADS("downloads"),
    LIB_ARTIFACT("artifact"),
    LIB_PATH("path"),
    LIB_URL("url"),
    LIB_HASH("sha1"),
    LIB_SIZE("size");



    private final String key;

    McMKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
