package com.zephyrupdater.common.ZUProtocol;


public interface ZUPStructCore {
    ZUPTypes getStructType();
    long getDataSize();
    Boolean getIsMultiChunks();

    String getJson();
}