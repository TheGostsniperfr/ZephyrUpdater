package com.zephyrupdater.server.commands.cmdlist;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.commands.ICmd;
import com.zephyrupdater.server.database.DBStruct;
import com.zephyrupdater.server.utils.DBStructUtils;

import java.util.List;
import java.util.Set;

public class CmdListRequest implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        if(argv.isEmpty()){
            System.out.println(getHelp());
            return;
        }

        DBStruct db = DBStructUtils.getDBByName(server, argv.getFirst());
        if(db == null) { return; }

        Set<String> requestAliasSet = db.getAllRequestAlias();
        System.out.println("Found " + requestAliasSet.size() + " request(s).");

        int i = 1;
        for(String requestName : requestAliasSet){
            JsonObject requestObj = DBStructUtils.getRequestObj(db, requestName);
            JsonElement isSharedObj = requestObj.get("isShared");
            JsonElement targetDirObj = requestObj.get("targetDir");
            JsonElement fileIdObj = requestObj.get("fileId");
            JsonElement projectIdObj = requestObj.get("projectId");

            System.out.println((i++)
                    + "> name: " + requestName
                    + ", isShared: " + isSharedObj.getAsBoolean()
                    + (targetDirObj == null ? "" : (", targetDir: " + targetDirObj.getAsString()))
                    + (fileIdObj == null ? "" : (", fileId: " + fileIdObj.getAsString()))
                    + (projectIdObj == null ? "" : (", projectId: " + projectIdObj.getAsString()))
            );
        }
    }

    @Override
    public String getCmdName() {
        return "requestList";
    }

    @Override
    public String getHelp() {
        return getCmdName() + " [files|modList]: list all the request in the database.";
    }
}
