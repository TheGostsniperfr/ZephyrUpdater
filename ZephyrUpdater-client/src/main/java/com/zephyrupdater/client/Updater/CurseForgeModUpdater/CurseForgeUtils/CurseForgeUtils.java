package com.zephyrupdater.client.Updater.CurseForgeModUpdater.CurseForgeUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.FileUtils.CURSE_KEY;
import com.zephyrupdater.common.ZUFile.FileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CurseForgeUtils {
    private static final String CURSE_FORGE_URL = "https://api.curse.tools";
    private static final String GET_MOD_FILE_URL = "/v1/cf/mods/{modId}/files/{fileId}";

    public static List<CurseForgeMod> getModFileList(JsonObject jsonModList, Path modsDirPath){
        List<CurseForgeMod> curseForgeMods = new ArrayList<>();

        for(String modName : jsonModList.keySet()){
            JsonElement jsonElement = jsonModList.get(modName);

            if(!jsonElement.isJsonObject()){
                System.out.println(jsonElement + " is not a JsonObject");
                continue;
            }

            JsonObject modJson = jsonElement.getAsJsonObject();
            String fileId = CommonUtil.getValueFromJson(CURSE_KEY.FILE_ID.getKey(), modJson, String.class);
            String projectId = CommonUtil.getValueFromJson(CURSE_KEY.PROJECT_ID.getKey(), modJson, String.class);

            String formattedUrl = CURSE_FORGE_URL + GET_MOD_FILE_URL
                            .replace("{modId}", projectId)
                            .replace("{fileId}", fileId);

            try {
                JsonObject response = getResponseFromRequest(new URL(formattedUrl));
                curseForgeMods.add(new CurseForgeMod(response, modsDirPath));

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return curseForgeMods;
    }

    private static JsonObject getResponseFromRequest(URL url){
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setInstanceFollowRedirects(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Accept", "application/json");
            int responseCode = conn.getResponseCode();

            // TODO

            try (Reader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                return JsonParser.parseReader(reader).getAsJsonObject();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createBlankJsonModList(int nbMods, Path filePath, String fileName){
        if(Files.exists(filePath.resolve(fileName))){
            System.out.println("A file with the same name already exist.");
            return;
        }
        JsonObject mainJson = new JsonObject();
        for(int i = 0; i < nbMods; i++){
            JsonObject modJson = new JsonObject();
            modJson.addProperty(CURSE_KEY.FILE_ID.getKey(),"");
            modJson.addProperty(CURSE_KEY.PROJECT_ID.getKey(),"");
            mainJson.add(new StringBuilder("CurseForgeUrl").append(i).toString(), modJson);
        }

        FileManager.saveJsonAt(mainJson, filePath, fileName);
    }

    public static void checkUpdateModList(List<CurseForgeMod> modList){
        modList.forEach(CurseForgeMod::checkUpdate);
    }
}
