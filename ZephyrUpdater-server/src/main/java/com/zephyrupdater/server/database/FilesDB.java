package com.zephyrupdater.server.database;

import java.nio.file.Path;

public class FilesDB extends DBStruct {
    private static final String DB_NAME = "filesDB.json";
    public FilesDB(Path cacheDirPath){
        super(cacheDirPath, DB_NAME);
    }
}
