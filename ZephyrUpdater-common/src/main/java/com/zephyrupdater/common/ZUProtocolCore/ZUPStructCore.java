package com.zephyrupdater.common.ZUProtocolCore;


public interface ZUPStructCore {
    ZUPTypes getStructType();
    long getDataSize();
    Boolean getIsMultiChunks();

    String getJson();
}