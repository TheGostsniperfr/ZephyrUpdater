package com.zephyrupdater.common.ZUCommandCore.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommandCore.ZUCKeys;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;

public class ZUCDisconnectionCore implements ZUCStructCore {
    public String content;

    @Override
    public ZUCTypes getStructType() {
        return ZUCTypes.DISCONNECTION;
    }

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
        this.content = CommonUtil.getFormatCmd("Exit");
    }
    public static void printHelp(){
        System.out.println("exit");
    }
}
