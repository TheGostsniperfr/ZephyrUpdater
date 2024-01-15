package com.zephyrupdater.client.ZUCommand.ZUCList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.MainClient;
import com.zephyrupdater.client.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommandCore.ZUCList.ZUCUpdateCore;
import com.zephyrupdater.common.ZUProtocolCore.ZUPManager;
import com.zephyrupdater.common.ZUProtocolCore.ZUProtocolTypesCore.ZUPCommandCore;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.List;
import java.util.Objects;

public class ZUCUpdate extends ZUCUpdateCore implements ZUCStruct {
    public ZUCUpdate(JsonObject data) {
        super(data);
    }

    public ZUCUpdate(String folderPath, JsonObject filesJson) {
        super(folderPath, filesJson);
    }

    @Override
    public void executeServerCmd() {
        String clientMainPath = String.valueOf(MainClient.clientFilePath);

        for (String key : this.filesJson.keySet()) {
            JsonElement jsonElement = this.filesJson.get(key);

            if (!filesJson.isJsonObject()) {
                System.out.println(this.filesJson + " is not a JsonObject");
            }

            JsonObject midJson = jsonElement.getAsJsonObject();
            System.out.println("Update " + key);

            Path path = Paths.get(CommonUtil.getValueFromJson("path", midJson, String.class));
            String hash = CommonUtil.getValueFromJson("hash", midJson, String.class);

            System.out.println("Server path is : " + path);
            System.out.println("File hash is :" + hash);

            Path localPath = Paths.get(MainClient.clientFilePath.toString() + path);
            if(Files.exists(localPath) && Files.isDirectory(localPath)){
                System.out.println(key + "is downloaded");
                System.out.println("hash verification :");
                try {
                    byte[] fileContentBytes = Files.readAllBytes(path);
                    byte[] clienthash = MessageDigest.getInstance("SHA-256").digest(fileContentBytes);

                    if(!Objects.equals(clienthash, hash)){
                        System.out.println("not same hash, delete it");
                        /* TODO
                        delete file and doawnload a new one
                         */
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("No such file or directory");
                /* TODO
                doawnload it
                 */
            }





        }
    }
    /**
     * Checking files at a specific folder
     * path format: Arffornia_V.5/clientFiles/
     * or           Arffornia_V.5/launcherFiles/
     *
     * @param argv
     */
    public static void executeClientCmd(List<String> argv){
        if(argv.size() < 2){
            printHelp();
            return;
        }

        String folderPath = argv.get(1);
        if(folderPath.equals("*")){
            folderPath = "";
        }

        ZUPManager.sendData(
                AppClient.getServerSocket(),
                new ZUPCommandCore(new ZUCUpdate(folderPath, new JsonObject())));
    }

    public static void printHelp(){
        System.out.println("update [folder path (To check update)]");
    }
}
