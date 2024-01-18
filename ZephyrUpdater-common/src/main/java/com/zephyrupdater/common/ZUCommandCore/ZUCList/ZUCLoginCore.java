package com.zephyrupdater.common.ZUCommandCore.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommandCore.ZUCKeys;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;

import java.util.List;

public class ZUCLoginCore implements ZUCStructCore {
    public String id;
    public String password;

    public ZUCLoginCore(String id, String password){
        this.id = id;
        this.password = password;
    }

    public ZUCLoginCore(List<String> argv){

    }

    public ZUCLoginCore(JsonObject data){
        this.id = CommonUtil.getValueFromJson(ZUCKeys.ID.getKey(), data, String.class);
        this.password = CommonUtil.getValueFromJson(ZUCKeys.PASSWORD.getKey(), data, String.class);
    }
    @Override
    public ZUCTypes getStructType() {
        return ZUCTypes.LOGIN;
    }

    public static String getCmdName() {
        return "login";
    }

    @Override
    public String getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ZUCKeys.ID.getKey(), id);
        jsonObject.addProperty(ZUCKeys.PASSWORD.getKey(), password);

        return jsonObject.toString();
    }

}