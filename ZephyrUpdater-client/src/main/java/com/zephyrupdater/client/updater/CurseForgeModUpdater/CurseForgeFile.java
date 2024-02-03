package com.zephyrupdater.client.updater.CurseForgeModUpdater;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.utils.FileUtils.CurseKeys;

import java.net.MalformedURLException;
import java.net.URL;

public class CurseForgeFile {
    private final String fileId;
    private final String projectId;
    private static final String GET_MOD_FILE_URL = "/v1/cf/mods/{modId}/files/{fileId}";

    public CurseForgeFile(JsonObject curseModJson){
        this.fileId = CommonUtil.getValueFromJson(CurseKeys.FILE_ID.getKey(), curseModJson, String.class);
        this.projectId = CommonUtil.getValueFromJson(CurseKeys.PROJECT_ID.getKey(), curseModJson, String.class);
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
