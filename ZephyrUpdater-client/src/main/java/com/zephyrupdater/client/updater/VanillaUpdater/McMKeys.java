package com.zephyrupdater.client.updater.VanillaUpdater;

public enum McMKeys {
    /*  VERSIONS MANIFEST  */
    VERSION_ID("id"),
    VERSION_MANIFEST_URL("url"),
    VERSIONS("versions"),

    /*  VERSION MANIFEST  */
    DOWNLOADS("downloads"),
    ASSET_INDEX("assetIndex"),
    LIBS("libraries"),

    /*  DOWNLOADS OBJ  */

    CLIENT("client"),


    /*  LIBS OBJ  */
    LIB_DOWNLOADS("downloads"),
    LIB_ARTIFACT("artifact"),
    LIB_PATH("path"),
    LIB_URL("url"),
    LIB_HASH("sha1"),
    LIB_SIZE("size"),
    LIB_CLASSIFIERS("classifiers"),

    /*  ASSET INDEX OBJ  */
    A_I_URL("url"),
    A_I_HASH("sha1"),
    A_I_SIZE("size"),

    /* CLIENT OBJ  */

    CLIENT_URL("url"),
    CLIENT_HASH("sha1"),
    CLIENT_SIZE("size"),

    /*  NATIVES OBJ  */
    NATIVES_LINUX("natives-linux"),
    NATIVES_WIN("natives-windows"),
    NATIVES_MAC("natives-macos"),
    NATIVES_URL("url"),
    NATIVES_HASH("sha1"),
    NATIVES_SIZE("size"),
    NATIVES_PATH("path");

    private final String key;

    McMKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
