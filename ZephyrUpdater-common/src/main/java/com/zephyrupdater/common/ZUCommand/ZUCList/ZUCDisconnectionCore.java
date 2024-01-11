package com.zephyrupdater.common.ZUCommand.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommand.ZUCKeys;
import com.zephyrupdater.common.ZUCommand.ZUCStructCore;
import com.zephyrupdater.common.ZUCommand.ZUCTypes;

public class ZUCDisconnectionCore extends ZUCStructCore {
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

    public ZUCDisconnectionCore(){
        this.structType = ZUCTypes.DISCONNECTION;
        this.content = CommonUtil.getFormatCmd("Exit");
    }
    public static void printHelp(){
        System.out.println("exit");
    }
}
