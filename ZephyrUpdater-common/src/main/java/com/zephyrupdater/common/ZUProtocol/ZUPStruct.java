package com.zephyrupdater.common.ZUProtocol;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ZUPStruct {
    public ZUPTypes structType;
    public long dataSize;
    public Boolean isMultiChunks = false;
    public String getJson(){
        return new Gson().toJson(this);
    }

    public static <T> T getValueFromJson(String key, JsonObject jsonElement, Class<T> valueType) {
        JsonElement valueElement = jsonElement.getAsJsonObject().get(key);

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