package com.zephyrupdater.client.Updater.JavaUpdater;

import com.zephyrupdater.client.MainClient;
import com.zephyrupdater.common.OSType;
import com.zephyrupdater.common.ZUFile.ArchiveExtractor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class JavaUpdater {
    private static final Path JAVA_DIR = MainClient.clientFilePath.resolve("java");

    public static void javaUpdate(){
        if (!Files.exists(JAVA_DIR)) {
            try {
                Files.createDirectories(JAVA_DIR);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("test");
        if(!JavaUpdater.isJavaInstalled()){
            System.out.println("test2");
            JavaUpdater.downloadJava();
            System.out.println("test3");
            if(!JavaUpdater.isJavaInstalled()){
                throw new RuntimeException("Failed to install java.");
            }
        }else{
            JavaUpdater.setCurrentJava();
        }

    }
    private static void downloadJava() {
        try {
            URL javaUrl = new URL(getJavaUrlByOS());
            System.out.println("URL:" + javaUrl);
            System.out.println("Java dir: " + JAVA_DIR);
            InputStream inputStream = javaUrl.openStream();
            Path destinationFile = JAVA_DIR.resolve(Paths.get(javaUrl.getPath()).getFileName().toString());
            System.out.println("dest File: " + destinationFile);
            if(!Files.exists(destinationFile)) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }


            if (MainClient.CLIENT_OS == OSType.WINDOWS) {
                ArchiveExtractor.extractZipArchive(destinationFile, JAVA_DIR);
            } else {
                ArchiveExtractor.extractTarGzArchive(destinationFile, JAVA_DIR);
            }

            //Files.deleteIfExists(destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setCurrentJava();
    }

    private static String getJavaUrlByOS(){
        switch (MainClient.CLIENT_OS){
            case WINDOWS: //Windows x64 Compressed Archive
                return "https://download.oracle.com/java/17/archive/jdk-17.0.9_windows-x64_bin.zip";
            case LINUX: //Linux x64 Compressed Archive
                return "https://download.oracle.com/java/17/archive/jdk-17.0.9_linux-x64_bin.tar.gz";
            case MAC: //macOS Arm 64 Compressed Archive
                return "https://download.oracle.com/java/17/archive/jdk-17.0.9_macos-aarch64_bin.tar.gz";
            default:
                throw new IllegalArgumentException();
        }
    }

    private static void setCurrentJava() {
        System.setProperty("java.home", JAVA_DIR.resolve("bin").toString());
    }

    private static Boolean isJavaInstalled(){
        Path javaBinDir = JAVA_DIR.resolve("jdk-17.0.9").resolve("bin");
        if(!Files.exists(javaBinDir)){
            return false;
        }
        return System.getProperty("java.version") != null && System.getProperty("java.home") != null;
    }


}
