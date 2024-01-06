package com.zephyrupdater.common.ZUCommand.ZUCList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCKeys;
import com.zephyrupdater.common.ZUCommand.ZUCStruct;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;

public class ZUCDisconnection extends ZUCStruct {
    public String content;

    public static String getCmdName(){
        return "exit";
    }

    @Override
    public String getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ZUCKeys.CONTENT.getKey(), content);

        return jsonObject.toString();
    }

    public ZUCDisconnection(){
        this.structType = ZUCTypes.DISCONNECTION;
        this.content = CommonUtil.getFormatCmd("Exit");
    }
    public static void printHelp(){
        System.out.println("exit");
    }
}
