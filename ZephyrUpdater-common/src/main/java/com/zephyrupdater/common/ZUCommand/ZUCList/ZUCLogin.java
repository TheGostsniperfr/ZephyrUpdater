package com.zephyrupdater.common.ZUCommand.ZUCList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCKeys;
import com.zephyrupdater.common.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;

public class ZUCLogin extends ZUCStruct {
    public String id;
    public String password;
    public ZUCLogin(String id, String password){
        this.structType = ZUCTypes.LOGIN;
        this.id = id;
        this.password = password;
    }

    public ZUCLogin(JsonObject data){
        this.structType = ZUCTypes.LOGIN;
        this.id = CommonUtil.getValueFromJson(ZUCKeys.ID.getKey(), data, String.class);
        this.password = CommonUtil.getValueFromJson(ZUCKeys.PASSWORD.getKey(), data, String.class);
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

    public static void printHelp(){
        System.out.println("login [id] [password]");
    }
}