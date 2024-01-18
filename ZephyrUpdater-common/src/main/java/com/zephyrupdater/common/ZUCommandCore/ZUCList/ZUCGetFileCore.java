package com.zephyrupdater.common.ZUCommandCore.ZUCList;

import com.google.gson.JsonObject;
import com.zephyrupdater.common.CommonUtil;
import com.zephyrupdater.common.ZUCommandCore.ZUCKeys;
import com.zephyrupdater.common.ZUCommandCore.ZUCStructCore;
import com.zephyrupdater.common.ZUCommandCore.ZUCTypes;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZUCGetFileCore implements ZUCStructCore {

    public final String fileName;
    public final Path relativeFilePath;
    public Path absFilePath;

    public ZUCGetFileCore(String fileName, Path relativeFilePath) {
        this.fileName = fileName;
        this.relativeFilePath = relativeFilePath;
    }

    public ZUCGetFileCore(JsonObject data){
        this.fileName = CommonUtil.getValueFromJson(ZUCKeys.FILE_NANE.getKey(), data, String.class);
        this.relativeFilePath =
                Paths.get(CommonUtil.getValueFromJson(ZUCKeys.RELATIVE_FILE_PATH.getKey(), data, String.class));
    }

    @Override
    public ZUCTypes getStructType() {
        return ZUCTypes.GET_FILE;
    }

    public static String getCmdName() {
        return "getFile";
    }

    @Override
    public String getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ZUCKeys.FILE_NANE.getKey(), this.fileName);
        jsonObject.addProperty(ZUCKeys.RELATIVE_FILE_PATH.getKey(), this.relativeFilePath.toString());

        return jsonObject.toString();
    }
}
