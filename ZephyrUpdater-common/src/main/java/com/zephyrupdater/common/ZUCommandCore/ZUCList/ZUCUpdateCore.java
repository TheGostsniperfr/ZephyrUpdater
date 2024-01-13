package com.zephyrupdater.common.ZUCommandCore.ZUCList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;
import com.zephyrupdater.common.ZUProtocolCore.ZUPKeys;

public class ZUCUpdateCore implements ZUCStructCore {

    public String folderPath;
    public JsonObject filesJson;

    public ZUCUpdateCore(JsonObject data){
        this.folderPath = CommonUtil.getValueFromJson(ZUPKeys.FOLDER_PATH.getKey(), data, String.class);
        this.filesJson = JsonParser.parseString(
                CommonUtil.getValueFromJson(ZUPKeys.FILES_JSON.getKey(), data, String.class))
                .getAsJsonObject();
    }

    public ZUCUpdateCore(String folderPath, JsonObject filesJson){
        this.folderPath = folderPath;
        this.filesJson = filesJson;
    }
    @Override
    public ZUCTypes getStructType() {
        return ZUCTypes.UPDATE;
    }

    @Override
    public String getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ZUPKeys.FOLDER_PATH.getKey(), folderPath);
        jsonObject.addProperty(ZUPKeys.FILES_JSON.getKey(), filesJson.toString());

        return jsonObject.toString();
    }
}
