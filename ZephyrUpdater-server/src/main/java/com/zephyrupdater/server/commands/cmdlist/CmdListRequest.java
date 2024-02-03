package com.zephyrupdater.server.commands.cmdlist;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.server.ZephyrServerManager;
import com.zephyrupdater.server.commands.ICmd;

import java.util.List;
import java.util.Set;

public class CmdListRequest implements ICmd {
    @Override
    public void execute(ZephyrServerManager server, List<String> argv) {
        Set<String> requestAliasSet = server.getPublicFilesDB().getAllRequestAlias();
        System.out.println("Found " + requestAliasSet.size() + " request(s).");

        int i = 1;
        for(String requestName : requestAliasSet){
            JsonObject requestObj = server.getPublicFilesDB().getDB().getAsJsonObject(requestName);

            JsonElement targetDirObj = requestObj.get("targetDir");
            JsonElement isPublicObj = requestObj.get("isPublic");

            if(targetDirObj == null || isPublicObj == null){
                System.err.println("Invalid request struct: " + requestName);
                return;
            }

            System.out.println((i++)
                    + "> name: " + requestName
                    + ", isPublic: " + isPublicObj.getAsBoolean()
                    + ", targetDir: " + targetDirObj.getAsString()
            );
        }
    }

    @Override
    public String getCmdName() {
        return "requestList";
    }

    @Override
    public String getHelp() {
        return getCmdName() + ": list all the request in the database.";
    }
}
