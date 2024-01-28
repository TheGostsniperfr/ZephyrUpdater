package com.zephyrupdater.common.utils.FileUtils;

import com.zephyrupdater.common.utils.FileUtils.HashUtils.HashAlgoType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class DownloadableFile {
    private final String hash;
    private final HashAlgoType hashAlgoType;
    private final long size;
    private final URL url;
    private final Path filePath;

    public DownloadableFile(Path filePath, URL url, long size, String hash, HashAlgoType hashAlgoType){
        this.hash = hash;
        this.hashAlgoType = hashAlgoType;
        this.size = size;
        this.url = url;
        this.filePath = filePath;
    }

    private void downloadMod(){
        InputStream inputStream;
        try {
            File parentDir = this.filePath.getParent().toFile();
            if(!parentDir.exists()){
                Files.createDirectories(parentDir.toPath());
            }

            inputStream = this.url.openStream();
            Files.copy(inputStream, this.filePath, StandardCopyOption.REPLACE_EXISTING);

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean needToBeUpdate(){
        return !FileUtils.isSameFile(this.filePath, this.hash, this.hashAlgoType);
    }

    public Boolean checkUpdate(){
        if(needToBeUpdate()){
            System.out.println("Downloading: " + this.getFileName() + " at: " + this.getFilePath());
            downloadMod();
            return true;
        }

        return false;
    }

    public URL getUrl() {
        return url;
    }
    public long getSize() {
        return size;
    }
    public String getHash() {
        return hash;
    }

    public String getFileName(){
        return this.filePath.getFileName().toString();
    }

    public Path getFilePath(){
        return this.filePath;
    }

    public HashAlgoType getHashAlgoType() {
        return hashAlgoType;
    }
}
