package com.zephyrupdater.common.FileUtils.HashUtils;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

public class HashAlgo {
    public static String getHashFromFilePath(Path filePath, HashAlgoType algoType){
        try {
            byte[] data = Files.readAllBytes(filePath);
            byte[] hash = MessageDigest.getInstance(algoType.getKey()).digest(data);
            return new BigInteger(1, hash).toString(16);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static HashAlgoType getAlgoTypeByName(String algoName){
        for(HashAlgoType algoType : HashAlgoType.values()){
            if(algoType.getKey().equals(algoName)){
                return algoType;
            }
        }

        throw new IllegalArgumentException(algoName);
    }
}
