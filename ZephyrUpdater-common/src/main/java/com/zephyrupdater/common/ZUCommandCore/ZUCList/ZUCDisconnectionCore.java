package com.zephyrupdater.common.ZUCommandCore.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommandCore.ZUCKeys;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;

public class ZUCDisconnectionCore implements ZUCStructCore {
    public String content;

    public static ZUCTypes getStructType() {
        return ZUCTypes.DISCONNECTION;
    }

    public ZUCDisconnectionCore(JsonObject data){ }

    @Override
    public JsonObject getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ZUCKeys.CONTENT.getKey(), content);

        return jsonObject;
    }

    public ZUCDisconnectionCore(){
        this.content = CommonUtil.getFormatCmd("Exit");
    }
}
