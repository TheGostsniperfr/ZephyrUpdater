package com.zephyrupdater.common.ZUCommandCore.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommandCore.ZUCKeys;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;

public class ZUCMessageCore implements ZUCStructCore {
    public String content;
    public ZUCMessageCore(String message){
        this.content = message;
    }

    public ZUCMessageCore(JsonObject data){
        this.content =  CommonUtil.getValueFromJson(ZUCKeys.CONTENT.getKey(), data, String.class);
    }

    @Override
    public ZUCTypes getStructType() {
        return ZUCTypes.MESSAGE;
    }

    public static String getCmdName() {
        return "msg";
    }

    @Override
    public String getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ZUCKeys.CONTENT.getKey(), content);

        return jsonObject.toString();
    }

    public static void printHelp(){
        System.out.println("msg [message]");
    }
}
