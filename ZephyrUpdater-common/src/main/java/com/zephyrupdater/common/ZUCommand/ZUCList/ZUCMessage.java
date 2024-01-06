package com.zephyrupdater.common.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCKeys;
import com.zephyrupdater.common.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;
import com.zephyrupdater.common.ZUProtocol.ZUPKeys;

public class ZUCMessage extends ZUCStruct {
    public String content;
    public ZUCMessage(String message){
        this.structType = ZUCTypes.MESSAGE;
        this.content = message;
    }

    public ZUCMessage(JsonObject data){
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
