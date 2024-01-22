package com.zephyrupdater.common.ZUCommandCore.ZUCList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;
import com.zephyrupdater.common.ZUProtocolCore.ZUPKeys;

public class ZUCUpdateCore implements ZUCStructCore {

    public String request;
    public JsonArray extFilesJson;
    public JsonObject curseModJson;

    public ZUCUpdateCore(JsonObject data){
        this.request = CommonUtil.getValueFromJson(ZUPKeys.REQUEST.getKey(), data, String.class);
        this.extFilesJson = JsonParser.parseString(
                CommonUtil.getValueFromJson(ZUPKeys.EXT_FILES_JSON.getKey(), data, String.class))
                .getAsJsonArray();
        this.curseModJson = JsonParser.parseString(
                CommonUtil.getValueFromJson(ZUPKeys.CURSE_MOD_JSON.getKey(), data, String.class))
                .getAsJsonObject();
    }

    public ZUCUpdateCore(JsonArray extFilesJson, JsonObject curseModJson){
        this.request = "";
        this.extFilesJson = extFilesJson;
        this.curseModJson = curseModJson;
    }

    public ZUCUpdateCore(String request){
        this.request = request;
        this.curseModJson = new JsonObject();
        this.extFilesJson = new JsonArray();
    }

    @Override
    public ZUCTypes getStructType() {
        return ZUCTypes.UPDATE;
    }

    @Override
    public String getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ZUPKeys.REQUEST.getKey(), request);
        jsonObject.addProperty(ZUPKeys.EXT_FILES_JSON.getKey(), extFilesJson.toString());
        jsonObject.addProperty(ZUPKeys.CURSE_MOD_JSON.getKey(), curseModJson.toString());

        return jsonObject.toString();
    }
}
