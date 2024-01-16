package com.zephyrupdater.common.FileUtils.HashUtils;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

public class HashAlgo {
    public static String getHashFromFilePath(Path filePath, HASH_ALGO_TYPE algoType){
        try {
            byte[] data = Files.readAllBytes(filePath);
            byte[] hash = MessageDigest.getInstance(algoType.getKey()).digest(data);
            return new BigInteger(1, hash).toString(16);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
