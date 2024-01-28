package com.zephyrupdater.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OsSpec {
    
    private OSType osType;
    private ArchType archType;

    public OsSpec(){
        setOsType();
        setArchType();
    }

    private void setOsType(){
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            osType = OSType.WINDOWS;
        } else if (osName.contains("nux") || osName.contains("nix")) {
            osType = OSType.LINUX;
        } else if (osName.contains("mac")) {
            osType = OSType.MAC;
        } else {
            throw new RuntimeException("Unsupported os: " + osName);
        }
    }

    private void setArchType(){
        String arch = System.getProperty("os.arch").toLowerCase();
        if (arch.contains("amd64") || arch.contains("x86_64")) {
            archType = ArchType.AMD64;
        } else if (arch.contains("x86")) {
            archType = ArchType.X86;
        } else if (arch.contains("arm")) {
            if (arch.contains("64")) {
                archType = ArchType.ARM64;
            } else {
                archType = ArchType.ARM32;
            }
        } else {
            throw new RuntimeException("Unsupported CPU architecture: " + arch);
        }
    }

    public void printSpec(){
        System.out.println("OS: " + this.getOsType());
        System.out.println("Arch: " + this.getArchType());
    }

    public OSType getOsType() {
        return osType;
    }

    public ArchType getArchType() {
        return archType;
    }

    public enum OSType{
        WINDOWS,
        LINUX,
        MAC,
    }
    
    public enum ArchType {
        X86,
        AMD64,
        ARM32,
        ARM64,
    }


    public Path getAppdataPath() {
        Path appdataPath;
        if (isOnWindows()) {
            appdataPath = Paths.get(System.getenv("APPDATA"));
        } else if (isOnLinux() || isOnMAC()) {
            appdataPath = Paths.get(System.getProperty("user.home"));
        }else{
            throw new RuntimeException("Unsupported os: " + getOsType().name());
        }

        try {
            Files.createDirectories(appdataPath.getParent());
            return appdataPath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean isOnWindows(){
        return this.osType == OSType.WINDOWS;
    }
    public Boolean isOnMAC(){
        return this.osType == OSType.MAC;
    }
    public Boolean isOnLinux(){
        return this.osType == OSType.LINUX;
    }
    public Boolean isOnX86(){
        return this.archType == ArchType.X86;
    }
    public Boolean isOnAMD64(){
        return this.archType == ArchType.AMD64;
    }
    public Boolean isOnARM32(){
        return this.archType == ArchType.ARM32;
    }
    public Boolean isOnARM64(){
        return this.archType == ArchType.ARM64;
    }
}
