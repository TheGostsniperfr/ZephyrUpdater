package com.zephyrupdater.client.Updater.VanillaUpdater;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.FileUtils.ExternalFilesUtils.ExternalFileCore;
import com.zephyrupdater.common.FileUtils.FileUtils;

import java.util.List;

public class MinecraftManifest {
    private static final String VERSIONS_MANIFEST_URL = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
    private final JsonObject versionsManifest;
    private final JsonArray versionsArray;
    private final JsonObject versionManifest;
    private final String mcVersion;

    public MinecraftManifest(String mcVersion){
        versionsManifest = FileUtils.loadJsonFromUrl(VERSIONS_MANIFEST_URL);
        this.mcVersion = mcVersion;

        this.versionsArray = this.versionsManifest.get(McMKeys.VERSIONS.getKey()).getAsJsonArray();
        this.versionManifest = getVersionManifest();
        if(this.versionManifest == null){
            System.err.println("Unknown minecraft version: " + this.mcVersion);
        }
    }

    private JsonObject getVersionManifest() {
        for (JsonElement version : this.versionsArray) {
            JsonObject versionObj = version.getAsJsonObject();
            String versionId = CommonUtil.getValueFromJson(McMKeys.VERSION_ID.getKey(), versionObj, String.class);

            if (versionId.equals(this.mcVersion)) {
                String manifestUrl = CommonUtil.getValueFromJson(
                        McMKeys.VERSION_MANIFEST_URL.getKey(), versionObj, String.class);

                return FileUtils.loadJsonFromUrl(manifestUrl);
            }
        }

        return null;
    }

    public List<ExternalFileCore> getLibsAsFiles(){
        JsonArray libArray = this.versionManifest.get()
    }
}
