package com.zephyrupdater.common.ZUCommandCore.ZUCList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommandCore.ZUCKeys;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;

public class ZUCGetFilesCore implements ZUCStructCore {
    public final String request; // ex: "v5_minecraft_files
    public final JsonObject extFilesJson;

    public ZUCGetFilesCore(JsonObject data){
        this.request = CommonUtil.getValueFromJson(ZUCKeys.REQUEST.getKey(), data, String.class);
        this.extFilesJson = JsonParser.parseString(CommonUtil.getValueFromJson(ZUCKeys.CONTENT.getKey(), data, String.class)).getAsJsonObject();
    }

    public ZUCGetFilesCore(String request){
        this.request = request;
        this.extFilesJson = new JsonObject();
    }

    public ZUCGetFilesCore(JsonElement extFilesJson){
        this.request = "";
        this.extFilesJson = extFilesJson.getAsJsonObject();
    }

    @Override
    public ZUCTypes getStructType() {
        return ZUCTypes.GET_FILES;
    }
    public static String getCmdName() {
        return "getFiles";
    }

    @Override
    public String getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ZUCKeys.REQUEST.getKey(), this.request);
        jsonObject.addProperty(ZUCKeys.CONTENT.getKey(), extFilesJson.toString());

        return jsonObject.toString();
    }
}
