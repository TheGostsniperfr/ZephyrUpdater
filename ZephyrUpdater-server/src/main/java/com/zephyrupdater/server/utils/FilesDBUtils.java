package com.zephyrupdater.server.utils;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.utils.FileUtils.ExternalFilesUtils.ExternalFileCore;
import com.zephyrupdater.common.utils.FileUtils.FileUtils;
import com.zephyrupdater.common.utils.FileUtils.HashUtils.HashAlgoType;
import com.zephyrupdater.server.database.FilesDB;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FilesDBUtils {
    public static void addFilesRequest(FilesDB filesDB, String requestAlias, String targetDir){
        if(!FileUtils.isValidTargetDirPath(targetDir)){
            System.err.println("Invalid target folder");
            return;
        }

        if (DBStructUtils.isRequestAlreadyExist(filesDB, requestAlias)){
            return;
        }

        System.out.print("Creating new request:\nAlias: " + requestAlias + "\nTarget dir: " + targetDir);

        JsonObject newRequest = new JsonObject();
        newRequest.addProperty("isShared", false);
        newRequest.addProperty("targetDir", targetDir);
        newRequest.add("files", getPublicFilesObjFromTargetDir(targetDir));

        filesDB.addObj(requestAlias, newRequest);

        System.out.println("Success to add request: " + requestAlias);
    }

    private static JsonObject getPublicFilesObjFromTargetDir(String targetDir){
        Path targetDirPath = Paths.get(targetDir);
        List<File> files = FileUtils.getRecursiveFilesFromDirPath(targetDirPath);
        JsonObject publicFilesObj = new JsonObject();

        for (File file : files){
            System.out.println("Adding: " + file.getAbsolutePath().replace(targetDir, ""));
            ExternalFileCore extFile = new ExternalFileCore(file.getAbsolutePath(), targetDir, HashAlgoType.SHA1);
            publicFilesObj.add(extFile.getFileName(), extFile.getJson());
        }

        return publicFilesObj;
    }
}
