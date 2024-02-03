package com.zephyrupdater.server.database;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class PublicFilesDB {
    private static final String DB_NAME = "publicFilesDB.json";
    private final Path dbFilePath;
    private JsonObject db;

    public PublicFilesDB(Path cacheDirPath){
        this.dbFilePath = cacheDirPath.resolve(DB_NAME);
        FileUtils.createDirIfNotExist(this.dbFilePath.getParent());
        this.loadDB();
    }

    public void loadDB(){
        if(!Files.exists(this.dbFilePath)){
            FileUtils.saveJsonAt(new JsonObject(), this.dbFilePath);
        }

        this.db = FileUtils.loadJsonFromFilePath(this.dbFilePath);
    }

    public void saveDB(){
        FileUtils.saveJsonAt(this.db.getAsJsonObject(), this.dbFilePath);
    }

    public Set<String> getAllRequestAlias(){
        return this.db.keySet();
    }

    public JsonObject getDB() {
        return db;
    }
}
