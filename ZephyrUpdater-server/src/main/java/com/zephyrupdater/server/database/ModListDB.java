package com.zephyrupdater.server.database;

import java.nio.file.Path;

public class ModListDB extends DBStruct {
    private static final String DB_NAME = "modList.json";
    public ModListDB(Path cacheDirPath){
        super(cacheDirPath, DB_NAME);
    }
}
