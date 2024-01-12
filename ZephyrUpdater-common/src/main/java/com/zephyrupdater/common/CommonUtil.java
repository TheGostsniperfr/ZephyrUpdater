package com.zephyrupdater.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.Socket;

public class CommonUtil {
    public static final String HOST = "localhost";
    public static final int SERVER_PORT  = 2048;

    public static final long timeRetryInterval = 500;
    public static final int nbTry = 5;

    public static String getFormatCmd(String str){
        return "@!@__" + str + "__@!@";
    }
    public static <T> T getValueFromJson(String key, JsonObject jsonObject, Class<T> valueType) {
        JsonElement valueElement = jsonObject.getAsJsonObject().get(key);

        if (valueElement != null && !valueElement.isJsonNull()) {
            try {
                if (valueType.equals(String.class)) {
                    return valueType.cast(valueElement.getAsString());
                } else if (valueType.equals(Integer.class)) {
                    return valueType.cast(valueElement.getAsInt());
                } else if (valueType.equals(Long.class)) {
                    return valueType.cast(valueElement.getAsLong());
                } else if (valueType.equals(Double.class)) {
                    return valueType.cast(valueElement.getAsDouble());
                } else if (valueType.equals(Boolean.class)) {
                    return valueType.cast(valueElement.getAsBoolean());
                } else {
                    throw new IllegalArgumentException("Unknown type: " + valueType);
                }
            } catch (NumberFormatException e) {
                System.err.println("Error to cast key:" + key + " to the type: " + valueType);
            }
        } else {
            System.err.println("Key not found in Json header: " + key);
        }

        return null;
    }
}
