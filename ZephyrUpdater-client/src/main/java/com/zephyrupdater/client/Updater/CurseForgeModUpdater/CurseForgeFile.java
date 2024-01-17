package com.zephyrupdater.client.Updater.CurseForgeModUpdater;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;

import java.net.MalformedURLException;
import java.net.URL;

public class CurseForgeFile {
    private final String fileId;
    private final String projectId;
    private static final String GET_MOD_FILE_URL = "/v1/cf/mods/{modId}/files/{fileId}";

    public CurseForgeFile(JsonObject curseModJson){
        this.fileId = CommonUtil.getValueFromJson(CURSE_KEY.FILE_ID.getKey(), curseModJson, String.class);
        this.projectId = CommonUtil.getValueFromJson(CURSE_KEY.PROJECT_ID.getKey(), curseModJson, String.class);
    }

    public URL getDownloadUrl(String baseUrl){
        try {
            return new URL(baseUrl + GET_MOD_FILE_URL
                    .replace("{modId}", this.projectId)
                    .replace("{fileId}", this.fileId));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFileId() {
        return fileId;
    }

    public String getProjectId() {
        return projectId;
    }
}
