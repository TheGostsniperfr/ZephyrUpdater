package com.zephyrupdater.common.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCKeys;
import com.zephyrupdater.common.ZUCommand.ZUCStructCore;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;

public class ZUCMessageCore extends ZUCStructCore {
    public String content;
    public ZUCMessageCore(String message){
        this.structType = ZUCTypes.MESSAGE;
        this.content = message;
    }

    public ZUCMessageCore(JsonObject data){
        this.structType = ZUCTypes.MESSAGE;
        this.content =  CommonUtil.getValueFromJson(ZUCKeys.CONTENT.getKey(), data, String.class);
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
