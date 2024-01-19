package com.zephyrupdater.client.Updater.CurseForgeModUpdater;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.common.ZUFile.ZUFileManager;

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
    private static final String CF_BASE_URL = "https://api.curse.tools";

    public static void updateCurseForgeMod(JsonObject curseModJson, Path modDirPath){
        List<CurseForgeMod> modList = CurseForgeUtils.getModFileList(curseModJson, modDirPath);
        CurseForgeUtils.checkUpdateModList(modList);
    }


    public static List<CurseForgeMod> getModFileList(JsonObject jsonModList, Path modsDirPath){
        List<CurseForgeMod> curseForgeMods = new ArrayList<>();

        for(String modName : jsonModList.keySet()){
            JsonElement jsonElement = jsonModList.get(modName);

            if(!jsonElement.isJsonObject()){
                System.out.println(jsonElement + " is not a JsonObject");
                continue;
            }

            CurseForgeFile modFile = new CurseForgeFile(jsonElement.getAsJsonObject());
            JsonObject response = getResponseFromRequest(modFile.getDownloadUrl(CF_BASE_URL));
            curseForgeMods.add(new CurseForgeMod(response, modsDirPath));
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

            if(responseCode != 200){
                throw new RuntimeException("Bad server response code: " + responseCode);
            }

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

        ZUFileManager.saveJsonAt(mainJson, filePath, fileName);
    }

    public static void checkUpdateModList(List<CurseForgeMod> modList){
        modList.forEach(CurseForgeMod::checkUpdate);
    }
}
