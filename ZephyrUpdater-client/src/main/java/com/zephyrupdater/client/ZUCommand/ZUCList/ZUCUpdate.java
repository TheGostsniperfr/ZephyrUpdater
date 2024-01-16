package com.zephyrupdater.client.ZUCommand.ZUCList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.client.AppClient;
import com.zephyrupdater.client.MainClient;
import com.zephyrupdater.client.Updater.UpdaterManager;
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

public class ZUCUpdate extends ZUCUpdateCore implements ZUCStruct {
    public ZUCUpdate(JsonObject data) {
        super(data);
    }

    public ZUCUpdate(String folderPath, JsonObject filesJson, JsonObject curseModJson) {
        super(folderPath, filesJson, curseModJson);
    }

    @Override
    public void executeServerCmd() {
        UpdaterManager.update(this.filesJson, this.curseModJson);

        for (String key : this.filesJson.keySet()) {
            System.out.println("####################");
            JsonElement jsonElement = this.filesJson.get(key);

            if (!filesJson.isJsonObject()) {
                System.out.println(this.filesJson + " is not a JsonObject");
            }

            JsonObject midJson = jsonElement.getAsJsonObject();
            System.out.println("Update " + key);

            Path path = Paths.get(CommonUtil.getValueFromJson("path", midJson, String.class));
            String hash = CommonUtil.getValueFromJson("hash", midJson, String.class);

            System.out.println("-Server path is : " + path);

            Path localPath = Paths.get(MainClient.clientFilePath.toString() + "\\" + path);
            System.out.println("-local path : " + localPath);
            if(Files.exists(localPath)){

                System.out.println("//  Path OK");
                System.out.println("hash verification :");

                try {
                    byte[] fileContentBytes = Files.readAllBytes(Paths.get("C:\\Users\\mathys\\AppData\\Roaming\\.Arffornia.V.5\\mods\\angelblockrenewed-forge-1.3-1.20.jar"));
                    byte[] clienthash = MessageDigest.getInstance("SHA-256").digest(fileContentBytes);
                    System.out.println("-server : " + hash);
                    System.out.println("-client :" + clienthash);

                    if(!clienthash.equals(hash)){
                        System.out.println("//  not same hash, delete it");
                        /* TODO
                        delete file and doawnload a new one
                         */
                    }

                    else{
                        System.out.println("//  Hash OK");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            else{
                System.out.println("//  No such file or directory");
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
                new ZUPCommandCore(new ZUCUpdate(folderPath, new JsonObject(), new JsonObject())));
    }

    public static void printHelp(){
        System.out.println("update [folder path (To check update)]");
    }
}
